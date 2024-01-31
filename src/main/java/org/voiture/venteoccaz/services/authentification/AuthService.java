package org.voiture.venteoccaz.services.authentification;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.Repositories.SessionRepository;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.Repositories.notification.UtilisateurFCMRepository;
import org.voiture.venteoccaz.exception.AccessDeniedException;
import org.voiture.venteoccaz.models.Session;
import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.models.notification.UtilisateurFCM;
import org.voiture.venteoccaz.util.CodeGenerator;
import org.voiture.venteoccaz.util.TokenGenerator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;
    private final UtilisateurFCMRepository utilisateurFCMRepository;

    @Autowired
    public AuthService(UtilisateurRepository utilisateurRepository, SessionRepository sessionRepository, UtilisateurFCMRepository utilisateurFCMRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.sessionRepository = sessionRepository;
        this.utilisateurFCMRepository = utilisateurFCMRepository;
    }

    public Optional<Utilisateur> isRegisteredAdmin(String email, String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findAllByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent() && utilisateur.get().getIsAdmin() == 1) {
            return utilisateur;
        }
        return Optional.empty();
    }

    public Optional<Utilisateur> isRegistered(String email, String motDePasse) {
        return utilisateurRepository.findAllByEmailAndMotDePasse(email, motDePasse);
    }

    public Optional<Session> isRegisteredCode(String code) {
        return sessionRepository.findAllByCodeConnected(code);
    }

    public Optional<Session> getSessionWithToken(String token) {
        return sessionRepository.findByToken(token.replace("Bearer ", ""));
    }


    public Boolean validateAuthorization(Map<String,String> headers) {
        String token = headers.get("authorization");
        return token != null && getSessionWithToken(token).isPresent();
    }

    public void validateAuthorizationVoid(Map<String,String> headers) throws Exception {
        String token = headers.get("authorization");
        if (!(token != null && getSessionWithToken(token).isPresent()))
            throw new AccessDeniedException("Non authorisé");
    }

    public void expireToken(String token) {
        Optional<Session> session = getSessionWithToken(token.replace("Bearer ", ""));
        if (session.isPresent()) {
            Session s = session.get();
            s.setIsConnected(0);
            s.setDateHeureLogin(LocalDateTime.now());
            sessionRepository.save(s);
        }
        else
            throw new RuntimeException("- Token not found -");
    }

    public Optional<Session> getSessionFromUtilisateur(Utilisateur utilisateur) {
        return sessionRepository.findAllByUtilisateur(utilisateur.getIdUtilisateur());
    }


    @Transactional
    public Reponse authenticateUser(Optional<Utilisateur> utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        if (utilisateur.isEmpty()) {
            return new Reponse("403", "Utilisateur absent de la base de donnée");
        }
        // Gestion session
        Optional<Session> session = getSessionFromUtilisateur(utilisateur.get());

        Session s = session.orElseGet(Session::new);
        var sessionFinal  = sessionRepository.save(setSessionActif(s, utilisateur.get()));

        return new Reponse("200", "Session de l'utilisateur", sessionFinal);
    }

    @Transactional
    public Reponse authenticate(Optional<Utilisateur> utilisateur, String tokenFcm) throws NoSuchAlgorithmException, InvalidKeyException {
        if (utilisateur.isEmpty()) {
            return new Reponse("403", "Utilisateur absent de la base de donnée");
        }
        // Gestion session
        Optional<Session> session = getSessionFromUtilisateur(utilisateur.get());

        Session s = session.orElseGet(Session::new);
        var sessionFinal  = sessionRepository.save(setSessionActif(s, utilisateur.get()));

        // Gestion token FCM
        Optional<UtilisateurFCM> utilisateurFCM = utilisateurFCMRepository.findUtilisateurFCMByUtilisateurAndTokenFcm(utilisateur.get().getIdUtilisateur(), tokenFcm);
        if (utilisateurFCM.isEmpty()) {
            UtilisateurFCM newUtilisateurTcmToken = new UtilisateurFCM();
            newUtilisateurTcmToken.setUtilisateur(utilisateur.get());
            newUtilisateurTcmToken.setTokenFcm(tokenFcm);
            utilisateurFCMRepository.save(newUtilisateurTcmToken);
        }

        // Envoi notifications à tous les appareils de l'utilisateur (batch sendMessages)
        // entre dernière date connexion et maintenant : s et sessionFinal
        // ...

        return new Reponse("200", "Session de l'utilisateur", sessionFinal);
    }

    private Session setSessionActif(Session s, Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        s.setUtilisateur(utilisateur);
        s.setIsConnected(1);
        s.setDateHeureLogin(LocalDateTime.now());
        s.setToken(TokenGenerator.getToken(utilisateur.getEmail()));

        String code = CodeGenerator.getCode();
        while (sessionRepository.findAllByCodeConnected(code).isPresent()) {
            code = CodeGenerator.getCode();
        }
        s.setCode(code);
        
        return s;
    }

    public ResponseEntity<Reponse> secure(Map<String,String> headers, Object o) {
        try {
            if (validateAuthorization(headers)) {
                return new ResponseEntity<>(new Reponse("200", o.getClass().getSimpleName()+" reçu", o), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Reponse("403", "Permission refusée", null), HttpStatus.UNAUTHORIZED);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(new Reponse("500", "Erreur serveur", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
