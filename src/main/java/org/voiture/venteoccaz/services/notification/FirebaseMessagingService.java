package org.voiture.venteoccaz.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.models.firebase.Notification;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.repositories.notification.UtilisateurFCMRepository;

import java.util.List;
import java.util.Optional;

import static org.voiture.venteoccaz.utils.StringUtils.*;

@Service
public class FirebaseMessagingService {
    private final UtilisateurFCMRepository utilisateurFCMRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final ObjectMapper objectMapper;

    @Value("${gcp.notifications.taille-max}")
    private final int tailleMaxNotif = 50;
    @Autowired
    public FirebaseMessagingService(UtilisateurFCMRepository utilisateurFCMRepository, FirebaseMessaging firebaseMessaging, ObjectMapper objectMapper) {
        this.utilisateurFCMRepository = utilisateurFCMRepository;
        this.firebaseMessaging = firebaseMessaging;
        this.objectMapper = objectMapper;
    }

    public Optional<BatchResponse> sendNotifications(Message message) throws FirebaseMessagingException, JsonProcessingException {
        Optional<List<String>> listToken = utilisateurFCMRepository.findTokenFcmByUtilisateur(message.getRecepteur().getIdUtilisateur());
        MulticastMessage msg;

        if (listToken.isPresent()) {

            Notification notification =
                new Notification()
                    .setTitre("Notification")
                    .setNomUtilisateurEnvoyeur(message.getEnvoyeur().getEmail())
                    .setMessageContent(tronquer(message.getTexte(), tailleMaxNotif))
                    .setDateHeureEnvoi(formaterDateTime(message.getDateHeureEnvoi()));

            msg = MulticastMessage.builder()
                    .addAllTokens(listToken.get())
                    .putData("body", objectMapper.writeValueAsString(notification))
                    .build();

            return Optional.ofNullable(firebaseMessaging.sendMulticast(msg));
        }
        else
            return Optional.empty();
    }

}
