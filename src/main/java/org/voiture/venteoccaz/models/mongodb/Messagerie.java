package org.voiture.venteoccaz.models.mongodb;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
@Document(collection="messageries")
public class Messagerie {

    @Id
    ObjectId idMessagerie;
    MongoUtilisateur envoyeur;
    MongoUtilisateur recepteur;
    List<Message> echanges;
    LocalDateTime dateHeureCreation;

    public Messagerie(MongoUtilisateur envoyeur, MongoUtilisateur recepteur, List<Message> echanges, LocalDateTime dateHeureCreation) {
        this.envoyeur = envoyeur;
        this.recepteur = recepteur;
        this.echanges = echanges;
        this.dateHeureCreation = dateHeureCreation;
    }

    public Messagerie() {
    }
}
