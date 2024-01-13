package org.voiture.venteoccaz.services.messagerie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.Repositories.mongodb.MongoUtilisateurRepository;
import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.util.Optional;

@Service
public class MongoUtilisateurService {
    private final MongoUtilisateurRepository mongoUtilisateurRepository;

    @Autowired
    public MongoUtilisateurService(MongoUtilisateurRepository mongoUtilisateurRepository) {
        this.mongoUtilisateurRepository = mongoUtilisateurRepository;
    }


    // Check utilisateur
    public MongoUtilisateur getUtilisateur(Utilisateur utilisateur) {
        Optional<MongoUtilisateur> mongoUtilisateur = mongoUtilisateurRepository.findMongoUtilisateurByIdUtilisateur(utilisateur.getIdUtilisateur());
        return mongoUtilisateur.orElseGet(() -> mongoUtilisateurRepository.save(new MongoUtilisateur(utilisateur)));
    }

}
