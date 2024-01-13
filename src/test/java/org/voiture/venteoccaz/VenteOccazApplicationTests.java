package org.voiture.venteoccaz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.Repositories.mongodb.MessageRepository;
import org.voiture.venteoccaz.Repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.Repositories.mongodb.MongoUtilisateurRepository;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;
import org.voiture.venteoccaz.services.messagerie.MessagerieService;
import org.voiture.venteoccaz.services.messagerie.MongoUtilisateurService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class VenteOccazApplicationTests {
    private final UtilisateurRepository utilisateurRepository;
    private final MessagerieRepository messagerieRepository;
    private final MessagerieService messagerieService;
    private final MongoUtilisateurService mongoUtilisateurService;
    private final MessageRepository messageRepository;
    private final MongoUtilisateurRepository mongoUtilisateurRepository;

    @Autowired
    public VenteOccazApplicationTests(UtilisateurRepository utilisateurRepository, MessagerieRepository messagerieRepository, MessagerieService messagerieService, MongoUtilisateurService mongoUtilisateurService, MessageRepository messageRepository, MongoUtilisateurRepository mongoUtilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.messagerieRepository = messagerieRepository;
        this.messagerieService = messagerieService;
        this.mongoUtilisateurService = mongoUtilisateurService;
        this.messageRepository = messageRepository;
        this.mongoUtilisateurRepository = mongoUtilisateurRepository;
    }

    @Test
    void contextLoads() {
        System.out.println("Hello World !");
    }

    // Contact :
    // u1 avec u2
    @Test
    void ajouterContact() {
        // Créer des utilisateurs de test
        int id = 2;
        MongoUtilisateur u1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u1  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        id = 3;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        Messagerie messagerie = messagerieService.ajouterContact(u1, u2);

        // Enregistrer la messagerie
        System.out.println("=========\n"+messagerie+"\n=========");
    }

    // Contact :
    // u2 avec u3
    @Test
    void ajouterContactBis() {
        int id = 3;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        id = 4;
        MongoUtilisateur u3 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u3  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        var mess = messagerieService.ajouterContact(u2, u3);
        System.out.println("=========\n"+mess+"\n=========");

    }


    // Contacts :
    // u1 avec u2
    // u3 avec u2
    // u2 avec u1 et u3
    @Test
    void getContacts() {
        int id = 3;
        MongoUtilisateur utilisateur = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        assert utilisateur != null;
        var list = messagerieService.getContacts(utilisateur.getId());
        System.out.println("=========\n"+list+"\n=========");

    }

    // Messagerie (et vice-versa) :
    // u1 <-> u2
    // u2 <-> u3
    @Test
    void getEchanges() {
        int id = 4;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        assert u2 != null;
        var contacts = messagerieService.getContacts(u2.getId());
        MongoUtilisateur u3 = contacts.get(0);

        assert u3 != null;
        var messagerie = messagerieService.getEchanges(u2, u3);

        System.out.println("=============\n"+messagerie+"\n=============");
    }

    // Message :
    // u2 -> u3
    @Test
    void envoyerMessage() throws Exception {
        int id = 4;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        assert u2 != null;
        var contacts = messagerieService.getContacts(u2.getId());
        MongoUtilisateur u3 = contacts.get(0);

        assert u3 != null;
        var messagerie = messagerieService.getEchanges(u2, u3);

        Message message = new Message(u2, u3, "Dernier message de test, je veux m'assurer que ca marche 🔥", LocalDateTime.now());

        Messagerie m = null;
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());

        System.out.println("=============\n"+m+"\n=============");
    }

    @Test
    void selectMessagerie() {
        List<Messagerie> messagerieList = messagerieRepository.findAll();
        System.out.println("=============\n"+messagerieList+"\n=============");
    }

    @Test
    void deleteMessagerie() {
        mongoUtilisateurRepository.deleteAll();
        messagerieRepository.deleteAll();
        messageRepository.deleteAll();
        System.out.println("=============\n DELETED ! \n=============");
    }


}
