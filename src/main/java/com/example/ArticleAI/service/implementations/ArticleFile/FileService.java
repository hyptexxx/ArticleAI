package com.example.ArticleAI.service.implementations.ArticleFile;

import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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

    @Override
    public boolean saveFileToFilesystem(MultipartFile file) {
        final File dir = new File(rootPath);
        BufferedOutputStream stream = null;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        try {
            stream = new BufferedOutputStream(new FileOutputStream(serverFile));
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
        return serverFile.exists();
    }
}
