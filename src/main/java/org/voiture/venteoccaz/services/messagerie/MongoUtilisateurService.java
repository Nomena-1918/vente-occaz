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
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public MongoUtilisateurService(MongoUtilisateurRepository mongoUtilisateurRepository, UtilisateurRepository utilisateurRepository) {
        this.mongoUtilisateurRepository = mongoUtilisateurRepository;
        this.utilisateurRepository = utilisateurRepository;
    }


    // Check utilisateur
    public MongoUtilisateur getUtilisateur(Utilisateur utilisateur) {
        Optional<MongoUtilisateur> mongoUtilisateur = mongoUtilisateurRepository.findMongoUtilisateurByIdUtilisateur(utilisateur.getIdUtilisateur());
        return mongoUtilisateur.orElseGet(() -> mongoUtilisateurRepository.save(new MongoUtilisateur(utilisateur)));
    }

    // Check utilisateur
    public MongoUtilisateur getUtilisateurById(Integer idUtilisateur) throws Exception {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(idUtilisateur);
        if (utilisateur.isEmpty())
            throw new Exception("Utilisateur inexistant");
        Optional<MongoUtilisateur> mongoUtilisateur = mongoUtilisateurRepository.findMongoUtilisateurByIdUtilisateur(idUtilisateur);
        return mongoUtilisateur.orElseGet(() -> mongoUtilisateurRepository.save(new MongoUtilisateur(utilisateur.get())));
    }

}
