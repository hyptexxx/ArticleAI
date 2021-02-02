package com.example.ArticleAI.modules.trainModule;

import com.example.ArticleAI.models.LoadedFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class FileProcessor {
    /**
     * Path to resources/uploadFiles.
     */
    private final String rootPath = System.getProperty("catalina.home") + File.separator +
            "webapps" + File.separator +
            "ROOT" + File.separator +
            "resources" + File.separator +
            "uploadedFiles";

    private final List<LoadedFile> importedFiles = new ArrayList<>();


    /**
     * @param files loaded file
     * @return true | false, if file was successfully saved.
     */
    public Optional<List<LoadedFile>> saveFilesToFilesystem(Set<LoadedFile> files) throws FileAlreadyExistsException {
        importedFiles.clear();
        final File dir = new File(rootPath);
        File savedFile;
        BufferedOutputStream stream;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (LoadedFile file : files) {
            savedFile = new File(dir.getAbsolutePath() + File.separator
                    + file.getLoadedFile().getOriginalFilename());
            if (savedFile.delete()) {
                log.info("file deleted {}", savedFile.getPath());
            }

            log.info("saving file {}", savedFile.getPath());
            if (!isFileExists(savedFile)) {
                try {
                    stream = new BufferedOutputStream(new FileOutputStream(savedFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
                try {
                    stream.write(file.getLoadedFile().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
                importedFiles.add(LoadedFile.builder()
                        .savedFile(savedFile)
                        .type(file.getType())
                        .build());
            } else {
                throw new FileAlreadyExistsException("File already exists");
                //todo сохранять стейт статьи при нужных результатов
            }
        }

        return Optional.of(importedFiles);
    }

    private static boolean isFileExists(File file) {
        return file.exists();
    }
}
