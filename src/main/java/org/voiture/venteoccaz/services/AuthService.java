package org.voiture.venteoccaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.SessionRepository;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Session;
import org.voiture.venteoccaz.models.Utilisateur;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Value("${custom.token.duration}")
    private int tokenDuration;
    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public AuthService(UtilisateurRepository utilisateurRepository, SessionRepository sessionRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.sessionRepository = sessionRepository;
    }

    public Optional<Utilisateur> isRegistered(String email, String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findAllByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent() && utilisateur.get().getIsAdmin() == 1) {
            return utilisateur;
        }
        return Optional.empty();
    }

    public Optional<Session> getSessionWithToken(String token) {
        return sessionRepository.findByToken(token.replace("Bearer ", ""));
    }



    public Boolean validateAuthorization(Map<String,String> headers) {
        String token = headers.get("authorization");
        return token != null && getSessionWithToken(token).isPresent();
    }


}
