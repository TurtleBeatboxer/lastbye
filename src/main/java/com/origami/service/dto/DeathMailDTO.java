package com.origami.service.dto;

public class DeathMailDTO {

    private String userEmail;
    private String friendEmail;
    private String tempPassword;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
}
