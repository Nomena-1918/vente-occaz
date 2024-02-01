package org.voiture.venteoccaz.repositories.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.mongodb.Messagerie;

import java.util.List;
import java.util.Optional;


@Repository
public interface MessagerieRepository extends MongoRepository<Messagerie, ObjectId> {
    @Query(value = "{ 'envoyeur.$id' : ?0 }", fields = "{ 'recepteur': 1 }")
    Optional<List<RecepteurOnly>> findAllContactsByMongoUtilisateurAsSender(ObjectId id);

    @Query(value = "{ 'recepteur.$id' : ?0 }", fields = "{ 'envoyeur': 1 }")
    Optional<List<EnvoyeurOnly>> findAllContactsByMongoUtilisateurAsReceiver(ObjectId id);

    @Query("{ '$or': [{'envoyeur': ?0, 'recepteur': ?1}, {'envoyeur': ?1, 'recepteur': ?0}]}")
    Optional<List<Messagerie>> findAllByMongoUtilisateurEchange(ObjectId IdMongoUtilisateurEnvoyeur, ObjectId IdMongoUtilisateurReceveur);

}