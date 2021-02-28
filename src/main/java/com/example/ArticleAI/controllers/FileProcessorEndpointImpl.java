package com.example.ArticleAI.controllers;

import com.example.ArticleAI.DAO.interfaces.YakeRepository;
import com.example.ArticleAI.configurations.AllowedContentTypes;
import com.example.ArticleAI.controllers.REST.FileProcessorEndpoint;
import com.example.ArticleAI.dto.YakeDTO;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.FullArticle;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.modules.nlpFilter.service.NlpFilterService;
import com.example.ArticleAI.modules.recomendationsResolver.service.implementations.NlpRequestService;
import com.example.ArticleAI.modules.recomendationsResolver.service.implementations.RecomendationsService;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileProcessorEndpointImpl implements FileProcessorEndpoint {

    private final IPOIService poiService;
    private final IRequestService requestService;
    private final IITextService iiTextService;
    private final IYakeService yakeService;
    private final YakeRepository yakeRepository;
    private final NlpFilterService nlpFilterService;

    private final FileProcessor fileProcessor;

    private final Map<LoadedFile, Boolean> mappedSupportedFiles = new HashMap<>();
    private final Map<String, String> errorsToClient = new HashMap<>();


    @Override
    public ResponseEntity<Object> processFiles(List<MultipartFile> files, ArticleYake articleYake) throws IOException, ParseException {

//        return ResponseEntity.status(HttpStatus.OK).body(nlpFilterService.doFilter(yakeRepository.getAllNgram().stream()
//                .map(ngram -> YakeResponse.builder()
//                        .ngram(ngram)
//                        .score(0)
//                        .build())
//                .collect(Collectors.toList())));

//
        Optional<List<LoadedFile>> savedFiles;
        Map<LoadedFile, Boolean> allowedFiles;

        if (!files.isEmpty()) {
            log.info("file saved");
            setSupportFiles(files);
            allowedFiles = getAllowedFiles();
            if (!allowedFiles.isEmpty()) {
                log.info("allowed");
                try {
                    savedFiles = fileProcessor.saveFilesToFilesystem(allowedFiles.keySet());
                    if (isTrainSet(files)) {
                        doTrain(savedFiles, articleYake);
                    } else {
                        Integer generatedArticleId;
                        ArticleYake parsedArticle = parseText(savedFiles, articleYake).get(0);
                        Optional<Integer> generatedArticleIdOptional = yakeRepository.saveArticle(parsedArticle);
                        if (generatedArticleIdOptional.isPresent()) {
                            generatedArticleId = generatedArticleIdOptional.get();

                            return ResponseEntity.status(HttpStatus.OK).body(YakeDTO.builder()
                                    .generatedArticleId(generatedArticleId)
                                    .yakeResponse(yakeService.parseYakeResponseJSON(requestService.sendRequest(parsedArticle)))
                                    .build());
                        }
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                } catch (FileAlreadyExistsException e) {
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                }
            }
        }

        log.info("file failed");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }


    @SneakyThrows
    private void doTrain(Optional<List<LoadedFile>> savedFiles, ArticleYake articleYake) {
//        List<ArticleYake> parsedArticles;
//        List<YakeResponse> yakeResponses = new ArrayList<>();
//        if (savedFiles.isPresent()) {
//            parsedArticles = parseText(savedFiles, articleYake);
//            parsedArticles.forEach(articleYake1 ->
//                    yakeResponses.addAll(yakeService.parseYakeResponseJSON(requestService.sendRequest(articleYake1))));
//            yakeRepository.saveYakeScores(yakeResponses, -1);
//        }

//        String everything;
//        BufferedReader br = new BufferedReader(new FileReader("keys.txt"));
//        try {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//            }
//            everything = sb.toString();
//        } finally {
//            br.close();
//        }
//        everything = everything.replace("\\s\\s", "\\s");
//        everything = everything.replace("\\)", "");
//        everything = everything.replace("\\(", "");
//        everything = everything.replace("\r\n", "");
//        everything = everything.replace(";", ",");
//        everything = everything.replace("?", "");
//        everything = everything.toLowerCase();
//
//
//        Set<String> keyWords = new HashSet<>();
//        List<String> keyPhrases = Arrays.asList(everything.split(","));
//        keyPhrases = keyPhrases.stream()
//                .map(String::trim)
//                .collect(Collectors.toList());
//        keyPhrases.forEach(phrase -> keyWords.addAll(Arrays.asList(phrase.split(" "))));
//        File qwe = new File("book_new.csv");
//
//        Writer fstream = new OutputStreamWriter(new FileOutputStream(qwe), "windows-1251");
//        try (CSVPrinter printer = new CSVPrinter(fstream, CSVFormat.DEFAULT.withHeader("key"))) {
//            keyPhrases.forEach(phrase -> {
//                try {
//                    printer.printRecord(phrase);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }


    private boolean isTrainSet(List<MultipartFile> files) {
        return files.size() > 1;
    }

    private Optional<Map<FullArticle, LoadedFile>> sendPoolRequests(Map<LoadedFile, ArticleYake> articlesYake) {
        Map<FullArticle, LoadedFile> result = new HashMap<>();
        articlesYake.forEach((loadedFile, articleYake) -> result.put(FullArticle.builder()
                .savedArticleYake(articleYake)
                .savedYakeResponse(yakeService.parseYakeResponseJSON(requestService.sendRequest(articleYake)))
                .build(), loadedFile));
        return Optional.of(result);
    }

    private void setSupportFiles(List<MultipartFile> files) {
        Optional<AllowedContentTypes> allowedContentType;
        this.mappedSupportedFiles.clear();

        for (MultipartFile file : files) {
            allowedContentType = Optional.ofNullable(AllowedContentTypes.fromDisplayString(file.getContentType()));
            log.info(file.getContentType());
            if (allowedContentType.isPresent()) {
                switch (allowedContentType.get()) {
                    case PDF:
                    case DOCX:
                    case DOC:
                    case OCTET:
                        this.mappedSupportedFiles.put(LoadedFile.builder()
                                .loadedFile(file)
                                .type(allowedContentType.get())
                                .build(), Boolean.TRUE);
                        break;
                }
            } else {
                this.mappedSupportedFiles.put(LoadedFile.builder()
                        .loadedFile(file)
                        .build(), Boolean.FALSE);
                this.errorsToClient.put(file.getOriginalFilename(), "Не поддерживаемый формат файла");
            }
        }
    }

    private Map<LoadedFile, Boolean> getAllowedFiles() {
        return mappedSupportedFiles.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .collect(Collectors.toMap(Map.Entry::getKey, multipartFileBooleanEntry -> true));
    }

    private Map<LoadedFile, ArticleYake> mapText(Optional<List<LoadedFile>> savedFiles, ArticleYake
            articleYake) {
        Map<LoadedFile, ArticleYake> result = new HashMap<>();
        if (savedFiles.isPresent()) {
            for (LoadedFile loadedFile : savedFiles.get()) {
                switch (loadedFile.getType()) {
                    case PDF:
                        result.put(loadedFile, iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake));
                        break;
                    case DOC:
                    case DOCX:
                    case OCTET:
                        result.put(loadedFile, poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake));
                        break;
                }
            }
        }

        return result;
    }

    private List<ArticleYake> parseText(Optional<List<LoadedFile>> savedFiles, ArticleYake articleYake) {
        List<ArticleYake> result = new ArrayList<>();
        if (savedFiles.isPresent()) {
            for (LoadedFile loadedFile : savedFiles.get()) {
                switch (loadedFile.getType()) {
                    case PDF:
                        result.add(iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake));
                        break;
                    case DOC:
                    case DOCX:
                    case OCTET:
                        result.add(poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake));
                        break;
                }
            }
        }

        return result;
    }
}