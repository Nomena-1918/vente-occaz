package org.voiture.venteoccaz.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.models.firebase.CustomNotification;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.repositories.SessionRepository;

import java.util.List;
import java.util.Optional;

import static org.voiture.venteoccaz.utils.StringUtils.*;

@Service
public class FirebaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;
    private final ObjectMapper objectMapper;
    private final SessionRepository sessionRepository;

    @Value("${gcp.notifications.taille-max}")
    private final int tailleMaxNotif = 50;
    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging, ObjectMapper objectMapper, SessionRepository sessionRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.objectMapper = objectMapper;
        this.sessionRepository = sessionRepository;
    }

    public Optional<BatchResponse> sendNotifications(Message message) throws FirebaseMessagingException, JsonProcessingException {
        Optional<List<String>> listToken = sessionRepository.findTokenFcmByUtilisateur(message.getRecepteur().getIdUtilisateur());
        MulticastMessage msg;

        if (listToken.isPresent()) {
            CustomNotification customNotification =
                new CustomNotification()
                    .setNomUtilisateurEnvoyeur(message.getEnvoyeur().getEmail())
                    .setMessageContent(tronquer(message.getTexte(), tailleMaxNotif))
                    .setDateHeureEnvoi(formaterDateTime(message.getDateHeureEnvoi()));

            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build();

            msg = MulticastMessage.builder()
                    .addAllTokens(listToken.get())
                    .setNotification(Notification.builder() // Use setNotification for notification payload
                            .setTitle("Nouveau message") // Add title
                            .setBody(customNotification.getMessageContent())
                            .build())
                    .putData("data", objectMapper.writeValueAsString(customNotification)) // Keep detailed data for later use
                    .setAndroidConfig(androidConfig)
                    .build();
            return Optional.ofNullable(firebaseMessaging.sendMulticast(msg));
        }
        else
            return Optional.empty();
    }

}
