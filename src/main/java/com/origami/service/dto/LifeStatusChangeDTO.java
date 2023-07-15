package com.origami.service.dto;

import com.origami.domain.LifeStatus;

public class LifeStatusChangeDTO {

    private String identifier;
    private String codeQR;
    private LifeStatus lifeStatus;
    private String emailAddress;
    private String lifeLink;

    public String getLifeLink() {
        return lifeLink;
    }

    public void setLifeLink(String lifeLink) {
        this.lifeLink = lifeLink;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public LifeStatus getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(LifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }
}
