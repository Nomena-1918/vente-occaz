package org.voiture.venteoccaz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.Repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.Repositories.mongodb.RecepteurOnly;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;
import org.voiture.venteoccaz.services.messagerie.MessagerieService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class VenteOccazApplicationTests {
    private final UtilisateurRepository utilisateurRepository;
    private final MessagerieRepository messagerieRepository;
    private final MessagerieService messagerieService;
    @Autowired
    public VenteOccazApplicationTests(UtilisateurRepository utilisateurRepository, MessagerieRepository messagerieRepository, MessagerieService messagerieService) {
        this.utilisateurRepository = utilisateurRepository;
        this.messagerieRepository = messagerieRepository;
        this.messagerieService = messagerieService;
    }

    @Test
    void contextLoads() {
        System.out.println("Hello World !");
    }

    @Test
    void insertMessagerie() {
        // Créer des utilisateurs de test
        int id = 2;
        MongoUtilisateur utilisateur1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur1  = new MongoUtilisateur(utilisateurRepository.findById(id).get());

        id = 3;
        MongoUtilisateur utilisateur2 = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur2  = new MongoUtilisateur(utilisateurRepository.findById(id).get());


        // Créer une messagerie avec des échanges de messages
        Messagerie messagerie = new Messagerie(
                utilisateur1,
                utilisateur2,
                Arrays.asList(
                        new Message(utilisateur1, utilisateur2, "Salut u1@gmail.com, comment ça va?", LocalDateTime.now()),
                        new Message(utilisateur2, utilisateur1, "Ça va bien, merci u2@gmail.com!", LocalDateTime.now())
                ),
                LocalDateTime.now()
        );

        // Enregistrer la messagerie
        System.out.println("=========\n"+messagerieRepository.save(messagerie)+"\n=========");
    }

    @Test
    void getContacts() {
        int id = 2;
        MongoUtilisateur utilisateur1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur1  = new MongoUtilisateur(utilisateurRepository.findById(id).get());

        var list = messagerieService.getContacts(utilisateur1);
        System.out.println("=========\n"+list+"\n=========");

    }


    @Test
    void getEchanges() {
        int id = 2;
        MongoUtilisateur utilisateur1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur1  = new MongoUtilisateur(utilisateurRepository.findById(id).get());

        var list = messagerieService.getContacts(utilisateur1);
        var messagerie = messagerieService.getEchanges(utilisateur1, list.get(0));

        System.out.println("=============\n"+messagerie+"\n=============");
    }

    @Test
    void envoyerMessage() throws Exception {
        int id = 2;
        MongoUtilisateur utilisateur1 = null;
        if (utilisateurRepository.findById(id).isPresent())
            utilisateur1  = new MongoUtilisateur(utilisateurRepository.findById(id).get());

        List<MongoUtilisateur> contacts = messagerieService.getContacts(utilisateur1);
        Optional<List<Messagerie>> messagerie = messagerieService.getEchanges(utilisateur1, contacts.get(0));

        System.out.println("=============\n"+messagerie+"\n=============");

        Message message = new Message(utilisateur1, contacts.get(0), "Dernier message de test, je veux m'assurer que ca marche 🔥", LocalDateTime.now());
        if(messagerie.isPresent())


        System.out.println("=============\n"+messagerie+"\n=============");
    }




    @Test
    void selectMessagerie() {
        List<Messagerie> messagerieList = messagerieRepository.findAll();
        System.out.println("=============\n"+messagerieList+"\n=============");
    }

    @Test
    void deleteMessagerie() {
        messagerieRepository.deleteAll();
        System.out.println("=============\n DELETED ! \n=============");
    }




}
