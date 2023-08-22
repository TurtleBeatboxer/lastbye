package com.origami.service;

import com.origami.domain.Files;
import com.origami.domain.Profile;
import com.origami.repository.FilesRepository;
import com.origami.repository.ProfileRepository;
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

    @Autowired
    private ProfileRepository profileRepository;

    public String uploadImage(MultipartFile file, String type, String userId) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        Files fileData = new Files();
        fileData.setFilePath(filePath);
        fileData.setName(file.getOriginalFilename());
        fileData.setFormat(file.getContentType());
        fileData.setType(type);
        Profile profile = profileRepository.findOneByUserId((long) Integer.parseInt(userId)).get();
        fileData.setProfile(profile);
        filesRepository.save(fileData);
        file.transferTo(new File(filePath));
        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }
}
