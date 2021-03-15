package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.mappers.YakeMapper;
import com.example.ArticleAI.models.KeywordClass;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.models.Recomendation;
import com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests.SearchAPIService;
import com.example.ArticleAI.modules.distanceService.DistanceService;
import com.example.ArticleAI.modules.nlpFilter.service.NlpFilterService;
import com.example.ArticleAI.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SendTo("/topic/analyseSteps")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NlpController {

    private final NlpFilterService nlpFilterService;
    private final ClassesRepository classesRepository;
    private final DistanceService distanceService;
    private final SearchAPIService searchAPIService;
    private final SimpMessageSendingOperations messagingTemplate;

    @PostMapping(value = "/api/nlp/analyse")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("yakeData") String yakeData) throws ParseException {
        List<NlpResponse> filteredYake = nlpFilterService.doFilter(YakeMapper
                .parse(yakeData)
                .orElseThrow(() -> new ParseException("unparsable yake data", 0)));

        List<KeywordClass> classes = classesRepository.getAllClassesEmbeddings();
        messagingTemplate.convertAndSend("/topic/analyseSteps", "5");
        classes = classes.stream()
                .map(clazz -> KeywordClass.builder()
                        .classActuality(searchAPIService.getSearchCount(clazz.getName()))
                        .name(clazz.getName())
                        .embedding(clazz.getEmbedding())
                        .build())
                .collect(Collectors.toList());
        messagingTemplate.convertAndSend("/topic/analyseSteps", "6");
        Recomendation distance = distanceService.getDistance(filteredYake.stream()
                .filter(keyWord -> keyWord.getIsGood() == 1)
                .map(NlpResponse::getNgram)
                .collect(Collectors.toList()), classes);

        if (filteredYake.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(distance);
    }
}
