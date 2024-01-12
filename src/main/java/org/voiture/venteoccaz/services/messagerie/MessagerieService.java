package org.voiture.venteoccaz.services.messagerie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.Repositories.mongodb.RecepteurOnly;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MessagerieService {

    private final MessagerieRepository messagerieRepository;

    @Autowired
    public MessagerieService(MessagerieRepository messagerieRepository) {
        this.messagerieRepository = messagerieRepository;
    }

    // Prendre tous les contacts d'un utilisateur
    // select recepteur case when m.recepteur = utilisateur then m.recepteur = utilisateur default m.envoyeur from from Messagerie m where m.recepteur = utilisateur or m.envoyeur = utilisateur;
    public List<MongoUtilisateur> getContacts(MongoUtilisateur mongoUtilisateur) {
        List<MongoUtilisateur> listUser = new ArrayList<>();
        Optional<List<RecepteurOnly>> list =  messagerieRepository.findAllByMongoUtilisateur(mongoUtilisateur);

        if (list.isPresent())
            for (RecepteurOnly l : list.get())
                listUser.add(l.getRecepteur());

        return listUser;
    }


    // Prendre tous les échanges d'un utilisateur avec un autre utilisateur
    public Optional<Messagerie> getEchanges(MongoUtilisateur mongoUtilisateurEnvoyeur, MongoUtilisateur mongoUtilisateurReceveur) {
        return messagerieRepository.findAllByMongoUtilisateurEchange(mongoUtilisateurEnvoyeur, mongoUtilisateurReceveur);
    }

    // Envoyer un message
    public Messagerie envoyerMessage(Messagerie messagerie, Message message) throws Exception {
        List<MongoUtilisateur> acteurs = Arrays.asList(messagerie.getEnvoyeur(), messagerie.getRecepteur());

        if (! (acteurs.contains(message.getRecepteur()) && acteurs.contains(message.getEnvoyeur())))
            throw new Exception("Utilisateurs différents entre la Messagerie et le Message");

        messagerie.getEchanges().add(message);
        return messagerieRepository.save(messagerie);
    }

}
