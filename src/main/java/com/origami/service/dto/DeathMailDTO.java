package com.origami.service.dto;

import lombok.Data;

@Data
public class DeathMailDTO {

    private String userEmail;
    private String friendEmail;
    private String tempPassword;
}
