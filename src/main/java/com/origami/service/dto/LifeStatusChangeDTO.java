package com.origami.service.dto;

import com.origami.domain.LifeStatus;
import lombok.Data;

@Data
public class LifeStatusChangeDTO {

    private String codeQR;
    private String lifeLink;
    private String friendAddress;
}
