package org.voiture.venteoccaz.models.mongodb;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
@Document(collection="messages")
public class Message {
    @Id
    ObjectId idMessage;
    @DBRef
    Messagerie messagerie;
    @DBRef
    MongoUtilisateur envoyeur;
    @DBRef
    MongoUtilisateur recepteur;
    String texte;
    LocalDateTime dateHeureEnvoi;

    public Message(MongoUtilisateur envoyeur, MongoUtilisateur recepteur, String texte, LocalDateTime dateHeureEnvoi) {
        this.envoyeur = envoyeur;
        this.recepteur = recepteur;
        this.texte = texte;
        this.dateHeureEnvoi = dateHeureEnvoi;
    }

}
