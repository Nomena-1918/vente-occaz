package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.Reponse.Reponse;

import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.services.AuthService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/annonces")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login-admin")
    public ResponseEntity<Reponse> LoginAdmin(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegisteredAdmin(utilisateur.getEmail(), utilisateur.getMotDePasse());
        return getReponseResponseEntity(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Reponse> Login(@RequestBody Utilisateur utilisateur) throws NoSuchAlgorithmException, InvalidKeyException {
        Optional<Utilisateur> user = authService.isRegistered(utilisateur.getEmail(), utilisateur.getMotDePasse());
        return getReponseResponseEntity(user);
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

    @PostMapping("/logout")
    public ResponseEntity<String> Logout(@RequestHeader Map<String,String> headers) {
        try {
            if (authService.validateAuthorization(headers)) {
                String token = headers.get("authorization");
                token = token.replace("Bearer ", "");
                authService.expireToken(token);
                return new ResponseEntity<>("Logged out !", HttpStatus.OK);
            } else
                return new ResponseEntity<>("Token expiré", HttpStatus.UNAUTHORIZED);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
