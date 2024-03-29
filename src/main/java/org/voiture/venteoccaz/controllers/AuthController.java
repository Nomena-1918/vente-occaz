package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.Reponse.Reponse;

import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Session;
import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.services.authentification.AuthService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final AuthService authService;
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    public AuthController(AuthService authService, UtilisateurRepository utilisateurRepository) {
        this.authService = authService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/login-admin")
    public ResponseEntity<Reponse> LoginAdmin(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegisteredAdmin(utilisateur.getEmail(), utilisateur.getMotDePasse());
        return getReponseResponseEntity(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Reponse> Login(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("utilisateur.getEmail() "+utilisateur.getEmail());
        System.out.println("utilisateur.getMotDePasse() "+utilisateur.getMotDePasse());
        Optional<Utilisateur> user = authService.isRegistered(utilisateur.getEmail(), utilisateur.getMotDePasse());
        return getReponseResponseEntity(user);
    }

    @PostMapping("/login-by-code")
    public ResponseEntity<Reponse> LoginByCode(@RequestParam String code) {
        Optional<Session> session = authService.isRegisteredCode(code);
        return session.map(
                    value -> new ResponseEntity<>(new Reponse("200", "Connecté !", value), HttpStatus.OK)
                )
                .orElseGet(
                    () -> new ResponseEntity<>(new Reponse("403", "Non authentifié !", null), HttpStatus.FORBIDDEN)
                );
    }

    @PostMapping("/logout")
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

    @PostMapping("/inscription")
    public ResponseEntity<Reponse> Inscription(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegistered(utilisateur.getEmail(), utilisateur.getMotDePasse());

        if(user.isPresent())
            return new ResponseEntity<>(new Reponse("500", "Utilisateur déjà présent dans la base de donnée", null), HttpStatus.CONFLICT);

        Utilisateur newUser = utilisateurRepository.save(utilisateur);
        return getReponseResponseEntity(Optional.of(newUser));
    }

    private ResponseEntity<Reponse> getReponseResponseEntity(Optional<Utilisateur> utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Reponse reponse = authService.authenticate(utilisateur);
        HttpStatus httpStatus = switch (reponse.getCode()) {
            case "200" -> HttpStatus.OK;
            case "403" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.UNAUTHORIZED;
        };
        return new ResponseEntity<>(reponse, httpStatus);
    }


}
