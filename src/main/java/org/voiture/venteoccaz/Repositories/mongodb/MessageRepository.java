package org.voiture.venteoccaz.Repositories.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.mongodb.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
}
