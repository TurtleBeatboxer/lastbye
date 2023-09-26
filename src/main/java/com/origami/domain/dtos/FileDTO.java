package com.origami.domain.dtos;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

    private MultipartFile file;
    private String type;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public FileDTO() {}

    public FileDTO(MultipartFile multipartFile, String type, Long userId) {
        this.file = multipartFile;
        this.type = type;
        this.userId = userId;
    }
}
