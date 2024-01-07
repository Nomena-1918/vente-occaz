package org.voiture.venteoccaz.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "annonces")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idannonce")
    Integer idAnnonce;

    @ManyToOne
    @JoinColumn(name = "idutilisateur", nullable = false)
    Utilisateur proprietaire;

    @ManyToOne
    @JoinColumn(name = "idcategorie", nullable = false)
    Categorie categorie;
    
    @ManyToOne
    @JoinColumn(name = "idmodele", nullable = false)
    Modele modele;

    @ManyToOne
    @JoinColumn(name = "idtypeoccasion", nullable = false)
    TypeOccasion typeOccasion;

    @ManyToOne
    @JoinColumn(name = "idcouleur", nullable = false)
    Couleur couleur;

    @ManyToOne
    @JoinColumn(name = "idmarque", nullable = false)
    Marque marque;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    List<Photo> listePhotos;
    
    double prix;
    @Column(name = "pourcentagecommission")
    double pourcentageCommission;
    String description;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    List<EtatAnnonce> etats;

    @Column(name = "etatannonce")
    int etatAnnonce;
    
}
