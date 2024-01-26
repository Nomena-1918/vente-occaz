package org.voiture.venteoccaz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idutilisateur")
    Integer idUtilisateur;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "motdepasse", nullable = false)
    String motDePasse;
    @Column(name = "isadmin")
    int isAdmin;
    
}
