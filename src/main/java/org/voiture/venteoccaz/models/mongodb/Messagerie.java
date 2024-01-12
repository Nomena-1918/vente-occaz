package org.voiture.venteoccaz.models.mongodb;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
@Document(collection="messageries")
public class Messagerie {

    @Id
    Integer idMessagerie;
    MongoUtilisateur envoyeur;
    MongoUtilisateur recepteur;
    List<Message> echanges;
    LocalDateTime dateHeureCreation;

}
