package com.origami.service.dto;

public class QRStartProcessDTO {

    private String emailAddress;
    private String answer;
    private String codeQR;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String password) {
        this.answer = password;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }
}
