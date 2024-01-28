package org.voiture.venteoccaz.services.messagerie;

import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.mongodb.*;
import org.voiture.venteoccaz.models.Utilisateur;
import org.voiture.venteoccaz.models.mongodb.Message;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import org.voiture.venteoccaz.models.mongodb.MongoUtilisateur;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MessagerieService {

    private final MessagerieRepository messagerieRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessagerieService(MessagerieRepository messagerieRepository, MessageRepository messageRepository) {
        this.messagerieRepository = messagerieRepository;
        this.messageRepository = messageRepository;
    }


    // Ajouter contact
    public Messagerie ajouterContact(MongoUtilisateur mongoUtilisateurEnvoyeur, MongoUtilisateur mongoUtilisateurRecepteur) {
        Optional<Messagerie> contact = getEchanges(mongoUtilisateurEnvoyeur, mongoUtilisateurRecepteur);
        return contact.orElseGet(() -> messagerieRepository.save(
                new Messagerie(
                        mongoUtilisateurEnvoyeur,
                        mongoUtilisateurRecepteur,
                        new ArrayList<>(),
                        LocalDateTime.now())
        ));
    }


    // Prendre tous les contacts d'un utilisateur
    public List<MongoUtilisateur> getContacts(ObjectId id) {
        List<MongoUtilisateur> listUser = new ArrayList<>();
        Optional<List<RecepteurOnly>> listS =  messagerieRepository.findAllContactsByMongoUtilisateurAsSender(id);
        Optional<List<EnvoyeurOnly>> listR =  messagerieRepository.findAllContactsByMongoUtilisateurAsReceiver(id);

        if (listS.isPresent())
            for (RecepteurOnly l : listS.get())
                listUser.add(l.getRecepteur());
        if (listR.isPresent())
            for (EnvoyeurOnly l : listR.get())
                listUser.add(l.getEnvoyeur());

        return listUser;
    }


    // Prendre tous les échanges d'un utilisateur avec un autre utilisateur
    public Optional<Messagerie> getEchanges(MongoUtilisateur mongoUtilisateurEnvoyeur, MongoUtilisateur mongoUtilisateurReceveur) {
        Optional<List<Messagerie>> mess = messagerieRepository.findAllByMongoUtilisateurEchange(mongoUtilisateurEnvoyeur.getId(), mongoUtilisateurReceveur.getId());
        if (mess.isPresent() && !mess.get().isEmpty())
                return mess.map(messageries -> messageries.get(0));
        else return Optional.empty();
    }


    // Envoyer message
    @Transactional
    public Messagerie envoyerMessage(Message message, ObjectId messagerieId) throws Exception {

        // Retrieve the messagerie
        Optional<Messagerie> optionalMessagerie = messagerieRepository.findById(messagerieId);
        if (optionalMessagerie.isPresent()) {
            Messagerie messagerie = optionalMessagerie.get();

            List<ObjectId> acteurs = Stream.of(messagerie.getRecepteur(), messagerie.getEnvoyeur()).map(MongoUtilisateur::getId).toList();
            if (! (acteurs.contains(message.getRecepteur().getId()) && acteurs.contains(message.getEnvoyeur().getId())))
                throw new Exception("Acteurs de la messagerie et du message différents");

            message.setMessagerie(messagerie);
            // Save the message to generate an ID
            Message savedMessage = messageRepository.save(message);

            // Add the saved message to the messagerie
            messagerie.getEchanges().add(savedMessage);

            // Save the updated messagerie
            return messagerieRepository.save(messagerie);
        } else {
            throw new IllegalArgumentException("Pas de messagerie avec l'ID " + messagerieId);
        }
    }

}
