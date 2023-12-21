package com.origami.service.dto;

import lombok.Data;

@Data
public class QRStartProcessDTO {

    private String emailAddress;
    private String answer;
    private String codeQR;
}
