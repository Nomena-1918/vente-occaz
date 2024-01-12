package org.voiture.venteoccaz.Repositories.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.util.List;


@Repository
public interface MessagerieRepository extends MongoRepository<Messagerie, Long> {
    @Query(value = "{ '$or': [{'envoyeur': ?0}, {'recepteur': ?0}]}",
            fields = "{ 'recepteur': { '$cond': { 'if': { '$eq': ['$envoyeur', ?0] }, 'then': '$recepteur', 'else': '$envoyeur' } } }")
    List<RecepteurOnly> findAllByMongoUtilisateur(MongoUtilisateur mongoUtilisateur);
}