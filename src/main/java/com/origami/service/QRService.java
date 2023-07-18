package com.origami.service;

import com.origami.domain.LifeStatus;
import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
import com.origami.service.dto.LifeStatusChangeDTO;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Service;

@Service
public class QRService {

    public QRService() {}

    public String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz" + "~$–_.+!*‘()";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
