package org.voiture.venteoccaz.models.firebase;

import lombok.Data;
import lombok.Getter;

@Getter
public class Notification {
    String titre;
    String nomUtilisateurEnvoyeur;
    String messageContent;
    String dateHeureEnvoi;

    public Notification() {
    }

    public Notification setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public Notification setNomUtilisateurEnvoyeur(String nomUtilisateurEnvoyeur) {
        this.nomUtilisateurEnvoyeur = nomUtilisateurEnvoyeur;
        return this;
    }

    public Notification setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public Notification setDateHeureEnvoi(String dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
        return this;
    }
}
