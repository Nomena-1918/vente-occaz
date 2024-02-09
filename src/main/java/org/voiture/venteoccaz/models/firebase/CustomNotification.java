package org.voiture.venteoccaz.models.firebase;

import lombok.Getter;

@Getter
public class CustomNotification {
    String nomUtilisateurEnvoyeur;
    String messageContent;
    String dateHeureEnvoi;

    public CustomNotification() {
    }

    public CustomNotification setNomUtilisateurEnvoyeur(String nomUtilisateurEnvoyeur) {
        this.nomUtilisateurEnvoyeur = nomUtilisateurEnvoyeur;
        return this;
    }

    public CustomNotification setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public CustomNotification setDateHeureEnvoi(String dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
        return this;
    }
}
