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

    private final MailService mailService;
    private final ProfileRepository profileRepository;

    public QRService(MailService mailService, ProfileRepository profileRepository) {
        this.mailService = mailService;
        this.profileRepository = profileRepository;
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

    /*@PostConstruct*/
    public void qRCountdown(LifeStatusChangeDTO lifeStatusChangeDTO) {
        final Integer[] i = { 11 };
        Timer timer = new Timer();
        int initialDelay = 0; //delay startu
        int cooldown = 1000/*7200000*/; //2h w ms - period miedzy wywolaniami "run"

        /*ManagedUserVM userVM = new ManagedUserVM();*/

        timer.scheduleAtFixedRate(
            new TimerTask() {
                public void run() {
                    Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(lifeStatusChangeDTO.getCodeQR());
                    if (profileOptional.isPresent() && profileOptional.get().getLifeStatus().equals(LifeStatus.UNKNOWN)) {
                        if (i[0] == 0) {
                            //prawdziwy mail
                            System.out.println("final mail");
                            profileOptional.get().setLifeStatus(LifeStatus.DEAD);
                            profileRepository.save(profileOptional.get());
                            timer.cancel();
                            return;
                        }

                        //prawdziwy mail
                        System.out.println(i[0]);
                        System.out.println("michal chyba ma w planach jutrto zwymiotowac hm?");
                        i[0]--;
                    } else {
                        timer.cancel();
                        return;
                    }
                }
            },
            initialDelay,
            cooldown
        );
    }
}
