package com.capsule.global.fcm;

import com.capsule.global.config.ValueConfig;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FCMInitializer {

    @PostConstruct
    public void initalize(){
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FCMPath.path.getFcmPath()).getInputStream());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.info("FCM error");
            log.error("FCM error message : " + e.getMessage());
        }
    }

}
