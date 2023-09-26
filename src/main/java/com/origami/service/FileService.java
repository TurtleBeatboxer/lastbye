package com.origami.service;

import com.origami.domain.Files;
import com.origami.domain.Profile;
import com.origami.domain.dtos.FileDTO;
import com.origami.domain.enumeration.FileType;
import com.origami.repository.FilesRepository;
import com.origami.repository.ProfileRepository;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    public FileService() {}

    @Autowired
    private FilesRepository filesRepository;

    @Value("${files.path}")
    private String FOLDER_PATH;

    @Autowired
    private ProfileRepository profileRepository;

    public String uploadImage(FileDTO fileDTO) throws IOException {
        Files fileData = new Files();
        fileData.setName(fileDTO.getFile().getOriginalFilename());
        fileData.setFormat(fileDTO.getFile().getContentType());
        fileData.setFileType(stringToFileType(fileDTO.getType()));
        Profile profile = profileRepository.findOneByUserId(fileDTO.getUserId()).get();
        fileData.setProfile(profile);
        String filePath = FOLDER_PATH + profile.getUserId() + "/" + fileDTO.getFile().getOriginalFilename();
        filesRepository.save(fileData);
        fileData.setFilePath(filePath);
        fileDTO.getFile().transferTo(new File(filePath));
        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public FileType stringToFileType(String type) {
        switch (type) {
            case ("burialPicture"):
                return FileType.BURIAL_PICTURE;
            case ("farewellLetter"):
                return FileType.FAREWELL_LETTER;
            case ("testament"):
                return FileType.TESTAMENT;
            case ("publicPicture"):
                return FileType.PUBLIC_PICTURE;
            case ("videoSpeech"):
                return FileType.VIDEO_SPEECH;
        }
        return null;
    }
}
