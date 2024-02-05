package org.voiture.venteoccaz;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.SendResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.voiture.venteoccaz.repositories.UtilisateurRepository;
import org.voiture.venteoccaz.repositories.mongodb.MessageRepository;
import org.voiture.venteoccaz.repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.repositories.mongodb.MongoUtilisateurRepository;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;
import org.voiture.venteoccaz.services.messagerie.MessagerieService;
import org.voiture.venteoccaz.services.messagerie.MongoUtilisateurService;
import org.voiture.venteoccaz.services.notification.FirebaseMessagingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class VenteOccazApplicationTests {
    private final UtilisateurRepository utilisateurRepository;
    private final MessagerieRepository messagerieRepository;
    private final MessagerieService messagerieService;
    private final MongoUtilisateurService mongoUtilisateurService;
    private final MessageRepository messageRepository;
    private final MongoUtilisateurRepository mongoUtilisateurRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public VenteOccazApplicationTests(UtilisateurRepository utilisateurRepository, MessagerieRepository messagerieRepository, MessagerieService messagerieService, MongoUtilisateurService mongoUtilisateurService, MessageRepository messageRepository, MongoUtilisateurRepository mongoUtilisateurRepository, FirebaseMessagingService firebaseMessagingService) {
        this.utilisateurRepository = utilisateurRepository;
        this.messagerieRepository = messagerieRepository;
        this.messagerieService = messagerieService;
        this.mongoUtilisateurService = mongoUtilisateurService;
        this.messageRepository = messageRepository;
        this.mongoUtilisateurRepository = mongoUtilisateurRepository;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @Test
    void contextLoads() {
        System.out.println("Hello World !");
    }


    // Contact :
    // u1 avec u2
    @Test
    void ajouterContact() throws Exception {
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
    void ajouterContactBis() throws Exception {
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
    // u2 <-> u3
    @Test
    void envoyerMessage() throws Exception {
        int id = 3;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        id = 4;
        MongoUtilisateur u3 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u3  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        var mess = messagerieService.ajouterContact(u2, u3);

        assert u3 != null;
        assert u2 != null;
        var messagerie = messagerieService.getEchanges(u2, u3);
        Message message = new Message(u3, u2, "Bonjour u3 c'est u2", LocalDateTime.now());
        Messagerie m = null;
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());

         messagerie = messagerieService.getEchanges(u2, u3);
         message = new Message(u3, u2, "Salut u2, comment ca va ?", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());


        messagerie = messagerieService.getEchanges(u2, u3);
        message = new Message(u2, u3, "Je me porte à merveille, pour la Ford Fiesta, y a moyen d'avoir une remise de 5% ?", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());


        messagerie = messagerieService.getEchanges(u2, u3);
        message = new Message(u3, u2, "Malheureusement non, c'est un prix fixe ce ne sera pas possible Monsieur", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());


        messagerie = messagerieService.getEchanges(u2, u3);
        message = new Message(u2, u3, "D'accord, bonne journée à vous ! 🙂", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());

    }


    // Message :
    // u1 <-> u2
    @Test
    void envoyerMessageTotal() throws Exception {
        int id = 2;
        MongoUtilisateur u1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u1  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        id = 3;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        var mess = messagerieService.ajouterContact(u1, u2);

        assert u2 != null;
        assert u1 != null;
        var messagerie = messagerieService.getEchanges(u1, u2);
        Message message = new Message(u1, u2, "Bien le bonjour u2 c'est u1", LocalDateTime.now());
        Messagerie m = null;
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());

        messagerie = messagerieService.getEchanges(u1, u2);
        message = new Message(u2, u1, "Bonjour bonjour, comment allez-vous ?", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());


        messagerie = messagerieService.getEchanges(u1, u2);
        message = new Message(u1, u2, "Je me porte comme un charme, c'est OK pour le rendez-vous de Vendredi prochain ?", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());



        messagerie = messagerieService.getEchanges(u1, u2);
        message = new Message(u2, u1, "Oui bien sûr ce sera l'occasion de tester votre Chevrolet", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());


        messagerie = messagerieService.getEchanges(u2, u1);
        message = new Message(u1, u2, "Parfait 🙂, au plaisir de vous rencontrer", LocalDateTime.now());
        if (messagerie.isPresent())
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());

    }


    // Message :
    // u2 <-> u3
    @Test
    void envoyerMessageTestNotifs() throws Exception {
        int id = 2;
        MongoUtilisateur u2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u2  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        id = 3;
        MongoUtilisateur u3 = null;
        if (utilisateurRepository.findById(id).isPresent())
            u3  = mongoUtilisateurService.getUtilisateur(utilisateurRepository.findById(id).get());

        //var mess = messagerieService.ajouterContact(u2, u3);

        assert u3 != null;
        assert u2 != null;
        //var messagerie = messagerieService.getEchanges(u2, u3);
        Message message = new Message(u3, u2, "Test hoe raha tena mandeha", LocalDateTime.now());
        Messagerie m = null;
        //if (messagerie.isPresent()) {
            //m = messagerieService.envoyerMessage(message, messagerie.get().getId());
            Optional<BatchResponse> b = firebaseMessagingService.sendNotifications(message);

            if (b.isPresent()) {
                var l = b.get().getResponses();

                for (SendResponse s : l) {
                    System.out.println("\n\n=====================\n");
                    System.out.println("Message ID : "+s.getMessageId());
                    if (s.getException()!=null)
                        System.out.println("Exception : "+s.getException().getMessage());
                    System.out.println("isSuccessful : "+ s.isSuccessful());
                }
            }
            else
                System.out.println("b empty");
       // }

/*
        messagerie = messagerieService.getEchanges(u2, u3);
        message = new Message(u3, u2, "Ouais bien reçu t'inquietes. Salut u2, comment ca va ?", LocalDateTime.now());
        if (messagerie.isPresent()) {
            m = messagerieService.envoyerMessage(message, messagerie.get().getId());
            Optional<BatchResponse> b = firebaseMessagingService.sendNotifications(message);
        }
 */

    }

    @Test
    void selectMessagerie() {
        List<Messagerie> messagerieList = messagerieRepository.findAll();
    }

    @Test
    void deleteMessagerie() {
        mongoUtilisateurRepository.deleteAll();
        messagerieRepository.deleteAll();
        messageRepository.deleteAll();
        System.out.println("=============\n DELETED ! \n=============");
    }

}
