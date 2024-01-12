package org.voiture.venteoccaz.Repositories.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.mongodb.Messagerie;

@Repository
public interface MessagerieRepository extends MongoRepository<Messagerie, Long> {



}
