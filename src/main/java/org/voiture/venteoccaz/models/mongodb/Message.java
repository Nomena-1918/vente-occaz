package org.voiture.venteoccaz.models.mongodb;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
@Document(collection="messages")
public class Message {

    @Id
    Integer idMessage;
    MongoUtilisateur envoyeur;
    MongoUtilisateur recepteur;
    String texte;
    LocalDateTime dateHeureEnvoi;

}
