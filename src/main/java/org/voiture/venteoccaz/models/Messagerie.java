package org.voiture.venteoccaz.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="messageries")
public class Messagerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idMessagerie;
    Utilisateur envoyeur;
    Utilisateur recepteur;
    List<Message> echanges;
    LocalDateTime dateHeureCreation;

}
