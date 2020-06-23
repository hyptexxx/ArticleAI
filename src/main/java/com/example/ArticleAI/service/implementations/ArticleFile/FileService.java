package com.example.ArticleAI.service.implementations.ArticleFile;

import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

@Service
public class FileService implements IFileService {

    /**
     * Path to resources/uploadFiles.
     */
    private final String rootPath = System.getProperty("catalina.home") + File.separator +
            "webapps" + File.separator +
            "ROOT" + File.separator +
            "resources" + File.separator +
            "uploadedFiles";

    private static File importedFile;


    /**
     * @param file loaded file
     * @return true | false, if file was successfully saved.
     */
    @Override
    public boolean saveFileToFilesystem(MultipartFile file) throws FileAlreadyExistsException {
        final File dir = new File(rootPath);
        BufferedOutputStream stream;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        importedFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        if (!isFileExists()) {
            try {
                stream = new BufferedOutputStream(new FileOutputStream(importedFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            try {
                stream.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new FileAlreadyExistsException("File already exists");
        }
        return importedFile.exists();
    }


    /**
     * @return previously saved file
     */
    @Override
    public File getFile() {
        return importedFile;
    }

    private static boolean isFileExists() {
        return importedFile.exists();
    }
}
