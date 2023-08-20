package com.origami.service;

import com.origami.domain.Files;
import com.origami.repository.FilesRepository;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public FileService() {}

    @Autowired
    private FilesRepository filesRepository;

    private final String FOLDER_PATH = "/Users/pkury/Desktop/lastgoodbuyImage/";

    public String uploadImage(MultipartFile file, String type) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        Files fileData = new Files();
        fileData.setFilePath(filePath);
        fileData.setName(file.getOriginalFilename());
        fileData.setFormat(file.getContentType());
        fileData.setType(type);
        filesRepository.save(fileData);
        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }
}
