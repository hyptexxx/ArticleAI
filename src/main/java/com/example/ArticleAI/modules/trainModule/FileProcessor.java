package com.example.ArticleAI.modules.trainModule;

import com.example.ArticleAI.models.LoadedFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;

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


    /**
     * @param file loaded file
     * @return true | false, if file was successfully saved.
     */
    public Optional<LoadedFile> saveFilesToFilesystem(LoadedFile file) throws FileAlreadyExistsException {
        final File dir = new File(rootPath);
        File savedFile;
        BufferedOutputStream stream;
        if (!dir.exists()) {
            dir.mkdirs();
        }
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
            return Optional.ofNullable(LoadedFile.builder()
                    .savedFile(savedFile)
                    .type(file.getType())
                    .build());
        } else {
            throw new FileAlreadyExistsException("File already exists");
            //todo сохранять стейт статьи при нужных результатов
        }
    }

    private static boolean isFileExists(File file) {
        return file.exists();
    }
}
