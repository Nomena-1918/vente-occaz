package org.voiture.venteoccaz.services.messagerie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.Repositories.mongodb.RecepteurOnly;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<RecepteurOnly> list =  messagerieRepository.findAllByMongoUtilisateur(mongoUtilisateur);

        for (RecepteurOnly l : list)
            listUser.add(l.getRecepteur());

        return listUser;
    }

    // Prendre tous les échanges d'un utilisateur avec un autre utilisateur

    // Envoyer un message


}
