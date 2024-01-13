package org.voiture.venteoccaz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.exception.AccessDeniedException;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;
import org.voiture.venteoccaz.services.authentification.AuthService;
import org.voiture.venteoccaz.services.messagerie.MessagerieService;
import org.voiture.venteoccaz.services.messagerie.MongoUtilisateurService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class MessagerieController {
    private final MessagerieService messagerieService;
    private final AuthService authService;
    private final MongoUtilisateurService mongoUtilisateurService;

    public MessagerieController(MessagerieService messagerieService, AuthService authService, MongoUtilisateurService mongoUtilisateurService, UtilisateurRepository utilisateurRepository) {
        this.messagerieService = messagerieService;
        this.authService = authService;
        this.mongoUtilisateurService = mongoUtilisateurService;
    }

    // Créer contact si non-existant et le retourner sino retourner l'existant
    @PostMapping("/contacts")
    public ResponseEntity<Reponse> ajouterContact(@RequestHeader Map<String,String> headers, @RequestParam Integer idEnvoyeur, @RequestParam Integer idReceveur) {
        try {
            authService.validateAuthorizationVoid(headers);

            MongoUtilisateur mongoUtilisateurEnvoyeur = mongoUtilisateurService.getUtilisateurById(idEnvoyeur);
            MongoUtilisateur mongoUtilisateurReceveur = mongoUtilisateurService.getUtilisateurById(idReceveur);

            Messagerie messagerie = messagerieService.ajouterContact(mongoUtilisateurEnvoyeur, mongoUtilisateurReceveur);
            return new ResponseEntity<>(new Reponse("200", "Contact correspondant", messagerie), HttpStatus.OK);
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(new Reponse("403", e.getMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new Reponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // Contacts d'un utilisateur
    @GetMapping("/contacts/{idUtilisateur}")
    public ResponseEntity<Reponse> getAllContact(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            authService.validateAuthorizationVoid(headers);

            MongoUtilisateur mongoUtilisateur = mongoUtilisateurService.getUtilisateurById(idUtilisateur);
            var contacts = messagerieService.getContacts(mongoUtilisateur.getId());

            return new ResponseEntity<>(new Reponse("200", "Contacts correspondants", contacts), HttpStatus.OK);
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(new Reponse("403", e.getMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new Reponse("500", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // Mesagerie entre les 2 users
    @GetMapping("/messageries/{idEnvoyeur}/{idReceveur}")
    public ResponseEntity<Reponse> getEchangeMessage(@RequestHeader Map<String,String> headers, @RequestParam Integer idEnvoyeur, @RequestParam Integer idReceveur) {
        try {
            authService.validateAuthorizationVoid(headers);

            MongoUtilisateur mongoUtilisateurEnvoyeur = mongoUtilisateurService.getUtilisateurById(idEnvoyeur);
            MongoUtilisateur mongoUtilisateurReceveur = mongoUtilisateurService.getUtilisateurById(idReceveur);
            Optional<Messagerie> messagerie = messagerieService.getEchanges(mongoUtilisateurEnvoyeur, mongoUtilisateurReceveur);

            if (messagerie.isPresent())
                return new ResponseEntity<>(new Reponse("200", "Contact correspondant", messagerie), HttpStatus.OK);
            else
                return new ResponseEntity<>(new Reponse("403", "Messagerie non-existante entre "+mongoUtilisateurEnvoyeur.getEmail()+" et "+mongoUtilisateurReceveur.getEmail(), messagerie), HttpStatus.OK);

        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(new Reponse("403", e.getMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new Reponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    // Envoi message
    @PutMapping("/messagerie/{idEnvoyeur}/{idReceveur}")
    public ResponseEntity<Reponse> envoyerMessage(@RequestHeader Map<String,String> headers, @RequestParam Integer idEnvoyeur, @RequestParam Integer idReceveur, @RequestBody String message) {
        try {
            authService.validateAuthorizationVoid(headers);

            MongoUtilisateur mongoUtilisateurEnvoyeur = mongoUtilisateurService.getUtilisateurById(idEnvoyeur);
            MongoUtilisateur mongoUtilisateurReceveur = mongoUtilisateurService.getUtilisateurById(idReceveur);

            Optional<Messagerie> messagerie = messagerieService.getEchanges(mongoUtilisateurEnvoyeur, mongoUtilisateurReceveur);
            Messagerie messagerieUpdate;

            if (messagerie.isPresent()) {
                Message mess = new Message(mongoUtilisateurEnvoyeur, mongoUtilisateurReceveur, message, LocalDateTime.now());
                messagerieUpdate = messagerieService.envoyerMessage(mess, messagerie.get().getId());
                return new ResponseEntity<>(new Reponse("200", "Message envoyé", messagerieUpdate), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new Reponse("403", "Messagerie non-existante entre "+mongoUtilisateurEnvoyeur.getEmail()+" et "+mongoUtilisateurReceveur.getEmail(), messagerie), HttpStatus.OK);
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(new Reponse("403", e.getMessage()), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new Reponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
