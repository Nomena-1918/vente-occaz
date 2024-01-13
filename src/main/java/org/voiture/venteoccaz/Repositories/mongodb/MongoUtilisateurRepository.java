package org.voiture.venteoccaz.Repositories.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.util.Optional;

@Repository
public interface MongoUtilisateurRepository extends MongoRepository<MongoUtilisateur, ObjectId> {
    Optional<MongoUtilisateur> findMongoUtilisateurByIdUtilisateur(Integer idUtilisateur);
}
