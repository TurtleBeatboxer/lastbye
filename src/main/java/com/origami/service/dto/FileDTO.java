package com.origami.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

    private MultipartFile file;
    private String type;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileDTO() {}

    public FileDTO(MultipartFile multipartFile, String type, String userId) {
        this.file = multipartFile;
        this.type = type;
        this.userId = userId;
    }
}
