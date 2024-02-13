package org.voiture.venteoccaz.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.repositories.SessionRepository;

import java.util.List;
import java.util.Optional;

import static org.voiture.venteoccaz.utils.StringUtils.*;

@Service
public class FirebaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;
    private final SessionRepository sessionRepository;

    @Value("${gcp.notifications.taille-max}")
    private final int tailleMaxNotif = 50;
    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging, SessionRepository sessionRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.sessionRepository = sessionRepository;
    }

    public Optional<BatchResponse> sendNotifications(Message message) throws FirebaseMessagingException {
        Optional<List<String>> listToken = sessionRepository.findTokenFcmByUtilisateur(message.getRecepteur().getIdUtilisateur());
        MulticastMessage msg;

        if (listToken.isPresent()) {
            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build();

            Notification notification = Notification.builder()
                    .setTitle(message.getEnvoyeur().getEmail())
                    .setBody(tronquer(message.getTexte(), tailleMaxNotif)+" à "+formaterDateTime(message.getDateHeureEnvoi()))
                    .build();

            msg = MulticastMessage.builder()
                    .addAllTokens(listToken.get())
                    .setNotification(notification)
                    .setAndroidConfig(androidConfig)
                    .build();

            return Optional.ofNullable(firebaseMessaging.sendMulticast(msg));
        }
        else
            return Optional.empty();
    }

}
