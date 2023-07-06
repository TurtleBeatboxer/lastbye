package com.origami.service;

import com.origami.domain.LifeStatus;
import com.origami.domain.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class QRService {

    private final MailService mailService;
    private final ProfileService profileService;

    public QRService(MailService mailService, ProfileService profileService) {
        this.mailService = mailService;
        this.profileService = profileService;
    }

    public String getAlphaNumericString(int n) {
        // -_.~
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz" + "~$–_.+!*‘()";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public void startQRCountdown(Profile profile) {
        profileService.updateLifeStatus(LifeStatus.UNKNOWN, profile);
        qRCountdown(profile);
    }

    @Async
    @Scheduled(initialDelay = 86400000)
    public void qRCountdown(Profile profile) {
        profileService.updateLifeStatus(LifeStatus.DEAD, profile);
        //calosc funkcjonalnosci - blokowanie editow + do ustalenia
    }
}
