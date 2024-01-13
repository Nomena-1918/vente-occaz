package org.voiture.venteoccaz.models.mongodb;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
@Document(collection="mongo_utilisateurs")
public class MongoUtilisateur {
    @Id
    ObjectId id;
    Integer idUtilisateur;
    String email;

    public MongoUtilisateur(Utilisateur utilisateur) {
        this.idUtilisateur = utilisateur.getIdUtilisateur();
        this.email = utilisateur.getEmail();
    }

    public MongoUtilisateur() {
    }
}
