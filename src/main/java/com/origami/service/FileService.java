package com.origami.service;

import com.origami.domain.Files;
import com.origami.domain.Profile;
import com.origami.domain.dtos.FileDTO;
import com.origami.domain.enumeration.FileType;
import com.origami.repository.FilesRepository;
import com.origami.repository.ProfileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FilesRepository filesRepository;

    @Value("${files.path}")
    private String FOLDER_PATH;

    @Autowired
    private ProfileRepository profileRepository;

    private final String SEPARATOR = FileSystems.getDefault().getSeparator();

    public String uploadImage(FileDTO fileDTO) throws IOException {
        Optional<Profile> profileOptional = profileRepository.findOneByUserId(Long.parseLong(fileDTO.getUserId()));
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            if (!profile.isFinishedEditing()) {
                Files fileData = new Files();
                fileData.setName(fileDTO.getFile().getOriginalFilename());
                fileData.setFormat(fileDTO.getFile().getContentType());
                fileData.setFileType(stringToFileType(fileDTO.getType()));
                fileData.setProfile(profile);
                String filePath = FOLDER_PATH + profile.getUserId() + SEPARATOR + fileDTO.getType();
                filesRepository.save(fileData);
                fileData.setFilePath(filePath);
                fileDTO.getFile().transferTo(new File(filePath));
                if (fileData != null) {
                    return "file uploaded successfully : " + filePath;
                }
            }
        }
        return null;
    }

    public File downloadPublicProfileImage(String publicProfile) {
        Optional<Profile> optionalProfile = profileRepository.findProfileByPublicProfileLink(publicProfile);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            return new File(FOLDER_PATH + profile.getUserId() + SEPARATOR + "publicPicture");
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

    public String fileTypeToFileName(FileType fileType) {
        switch (fileType) {
            case BURIAL_PICTURE:
                return "burialPicture";
            case FAREWELL_LETTER:
                return "farewellLetter";
            case TESTAMENT:
                return "testament";
            case PUBLIC_PICTURE:
                return "publicPicture";
            case VIDEO_SPEECH:
                return "videoSpeech";
        }
        return null;
    }
}
