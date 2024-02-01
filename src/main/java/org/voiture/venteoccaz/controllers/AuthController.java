package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.reponse.Reponse;

import org.voiture.venteoccaz.repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Session;
import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.models.firebase.UtilisateurFCM;
import org.voiture.venteoccaz.services.authentification.AuthService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api")
public class AuthController {
    private final AuthService authService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public AuthController(AuthService authService, UtilisateurRepository utilisateurRepository) {
        this.authService = authService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/v2/login-admin")
    public ResponseEntity<Reponse> LoginAdmin(@RequestBody UtilisateurFCM utilisateurFCM) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegisteredAdmin(utilisateurFCM.getUtilisateur().getEmail(), utilisateurFCM.getUtilisateur().getMotDePasse());
        return getReponseResponseEntity(user, utilisateurFCM.getTokenFcm());
    }

    @PostMapping("/v1/login-admin")
    public ResponseEntity<Reponse> LoginAdminUser(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegisteredAdmin(utilisateur.getEmail(), utilisateur.getMotDePasse());
        return getReponseResponseEntityUser(user);
    }


    @PostMapping("/v2/login")
    public ResponseEntity<Reponse> Login(@RequestBody UtilisateurFCM utilisateurFCM) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("utilisateurFCM.getUtilisateur().getEmail()  "+utilisateurFCM.getUtilisateur().getEmail());
        System.out.println("utilisateurFCM.getUtilisateur().getMotDePasse() "+utilisateurFCM.getUtilisateur().getMotDePasse());
        Optional<Utilisateur> user = authService.isRegistered(utilisateurFCM.getUtilisateur().getEmail(), utilisateurFCM.getUtilisateur().getMotDePasse());

        return getReponseResponseEntity(user, utilisateurFCM.getTokenFcm());
    }


    @PostMapping("/v1/login")
    public ResponseEntity<Reponse> LoginUser(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("utilisateur.getEmail()  "+utilisateur.getEmail());
        System.out.println("utilisateur.getMotDePasse() "+utilisateur.getMotDePasse());
        Optional<Utilisateur> user = authService.isRegistered(utilisateur.getEmail(), utilisateur.getMotDePasse());

        return getReponseResponseEntityUser(user);
    }

    @PostMapping("/v1/login-by-code")
    public ResponseEntity<Reponse> LoginByCode(@RequestParam String code) {
        Optional<Session> session = authService.isRegisteredCode(code);
        return session.map(
                    value -> new ResponseEntity<>(new Reponse("200", "Connecté !", value), HttpStatus.OK)
                )
                .orElseGet(
                    () -> new ResponseEntity<>(new Reponse("403", "Non authentifié !", null), HttpStatus.FORBIDDEN)
                );
    }

    @PostMapping("/v1/logout")
    public ResponseEntity<Reponse> Logout(@RequestHeader Map<String,String> headers) {
        try {
            if (authService.validateAuthorization(headers)) {
                String token = headers.get("authorization");
                token = token.replace("Bearer ", "");
                authService.expireToken(token);
                return new ResponseEntity<>(new Reponse("200", "Logged out !", null), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Reponse("403", "Non aunthentifié !", null), HttpStatus.FORBIDDEN);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(new Reponse("500", "Erreur serveur", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/v1/inscription")
    public ResponseEntity<Reponse> inscriptionUser(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegistered(utilisateur.getEmail(), utilisateur.getMotDePasse());

        if(user.isPresent())
            return new ResponseEntity<>(new Reponse("500", "Utilisateur déjà présent dans la base de donnée", null), HttpStatus.CONFLICT);

        Utilisateur newUser = utilisateurRepository.save(utilisateur);
        return getReponseResponseEntityUser(Optional.of(newUser));
    }

    @PostMapping("/v2/inscription")
    public ResponseEntity<Reponse> inscription(@RequestBody UtilisateurFCM utilisateurFCM) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegistered(utilisateurFCM.getUtilisateur().getEmail(), utilisateurFCM.getUtilisateur().getMotDePasse());

        if(user.isPresent())
            return new ResponseEntity<>(new Reponse("500", "Utilisateur déjà présent dans la base de donnée", null), HttpStatus.CONFLICT);

        Utilisateur newUser = utilisateurRepository.save(utilisateurFCM.getUtilisateur());
        return getReponseResponseEntity(Optional.of(newUser), utilisateurFCM.getTokenFcm());
    }

    // Pour v2
    private ResponseEntity<Reponse> getReponseResponseEntity(Optional<Utilisateur> utilisateur, String tokenFcm) throws NoSuchAlgorithmException, InvalidKeyException {
        Reponse reponse = authService.authenticate(utilisateur, tokenFcm);
        HttpStatus httpStatus = switch (reponse.getCode()) {
            case "200" -> HttpStatus.OK;
            case "403" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.UNAUTHORIZED;
        };
        return new ResponseEntity<>(reponse, httpStatus);
    }

    // Pour v1
    private ResponseEntity<Reponse> getReponseResponseEntityUser(Optional<Utilisateur> utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Reponse reponse = authService.authenticateUser(utilisateur);
        HttpStatus httpStatus = switch (reponse.getCode()) {
            case "200" -> HttpStatus.OK;
            case "403" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.UNAUTHORIZED;
        };
        return new ResponseEntity<>(reponse, httpStatus);
    }



}
