package org.voiture.venteoccaz.models;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idMessage;
    Utilisateur envoyeur;
    Utilisateur recepteur;
    String texte;
    LocalDateTime dateHeureEnvoi;

}
