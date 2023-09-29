package com.origami.service;

import com.origami.domain.Files;
import com.origami.domain.Profile;
import com.origami.domain.enumeration.FileType;
import com.origami.repository.FilesRepository;
import com.origami.repository.ProfileRepository;
import com.origami.service.dto.FileDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class FileService {

    @Autowired
    private FilesRepository filesRepository;

    @Value("${files.path}")
    private String FOLDER_PATH;

    @Autowired
    private ProfileRepository profileRepository;

    private final String SEPARATOR = FileSystems.getDefault().getSeparator();

    public String uploadImagePDFFirstTime(FileDTO fileDTO) throws IOException {
        Optional<Profile> profileOptional = profileRepository.findOneByUserId(fileDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            if (!profile.isFinishedEditing()) {
                Files fileData = new Files();
                fileData.setName(fileDTO.getFile().getOriginalFilename());
                fileData.setFormat(getFileExtension(fileData.getFormat()));
                fileData.setFileType(stringToFileType(fileDTO.getType()));
                fileData.setProfile(profile);
                new File(FOLDER_PATH + profile.getUserId()).mkdirs();
                String filePath = makePath(fileData);
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

    public ResponseEntity<?> getAllFilesFromUser(Long userId) throws IOException {
        Optional<Profile> profileOptional = profileRepository.findOneByUserId(userId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            List<Files> filesRepositoryList = filesRepository.findAllByProfileId(profile.getId());
            List<byte[]> listOfImagesAsByteStream = new ArrayList<>();
            for (Files files : filesRepositoryList) {
                File file = ResourceUtils.getFile(makePath(files));
                InputStream in = new FileInputStream(file);
                if (in != null) {
                    listOfImagesAsByteStream.add(IOUtils.toByteArray(in));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(listOfImagesAsByteStream);
        }
        return null;
    }

    public byte[] downloadPublicProfileImage(String publicProfile) throws IOException {
        Optional<Profile> optionalProfile = profileRepository.findProfileByPublicProfileLink(publicProfile);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            Optional<Files> files = filesRepository.findOneByProfileId(profile.getId());
            if (files.isPresent()) {
                File file = ResourceUtils.getFile(makePath(files.get()));
                InputStream in = new FileInputStream(file);
                if (in != null) {
                    return IOUtils.toByteArray(in);
                }
            }
        }
        return null;
    }

    private String getFileExtension(String nameOfFile) {
        if (nameOfFile.contains("/")) {
            int index = nameOfFile.lastIndexOf('/') + 1;
            return nameOfFile.substring(index);
        }
        return nameOfFile;
    }

    private String makePath(Files file) {
        return (
            FOLDER_PATH +
            file.getProfile().getUserId() +
            SEPARATOR +
            fileTypeToFileName(file.getFileType()) +
            "." +
            getFileExtension(file.getFormat())
        );
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
