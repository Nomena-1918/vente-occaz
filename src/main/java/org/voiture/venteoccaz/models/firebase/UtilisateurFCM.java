package org.voiture.venteoccaz.models.firebase;

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
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "token_fcm")
    private String tokenFcm;
}
