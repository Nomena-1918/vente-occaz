package org.voiture.venteoccaz.Repositories.mongodb;

import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

public interface EnvoyeurOnly {
    MongoUtilisateur getEnvoyeur();
}

