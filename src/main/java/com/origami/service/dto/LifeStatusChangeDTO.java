package com.origami.service.dto;

import com.origami.domain.LifeStatus;

public class LifeStatusChangeDTO {

    private String codeQR;
    private String lifeLink;
    private String friendAddress;

    public String getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(String friendAddress) {
        this.friendAddress = friendAddress;
    }

    public String getLifeLink() {
        return lifeLink;
    }

    public void setLifeLink(String lifeLink) {
        this.lifeLink = lifeLink;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public LifeStatusChangeDTO() {}
}
