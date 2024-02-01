package org.voiture.venteoccaz.repositories.mongodb;

import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

public interface EnvoyeurOnly {
    MongoUtilisateur getEnvoyeur();
}

