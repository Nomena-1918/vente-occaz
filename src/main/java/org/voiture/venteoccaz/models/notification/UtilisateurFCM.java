package org.voiture.venteoccaz.models.notification;

import jakarta.persistence.*;
import lombok.Data;
import org.voiture.venteoccaz.models.Utilisateur;

@Entity
@Table(name = "utilisateur_fcm")
@Data
public class UtilisateurFCM {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @Column(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "token_fcm")
    private String tokenFcm;
}
