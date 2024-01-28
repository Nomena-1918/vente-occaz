package org.voiture.venteoccaz.models;

import java.util.List;
import java.time.LocalDateTime;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JsonIgnore
    List<Photo> listePhotos;
    
    double prix;
    @Column(name = "pourcentagecommission")
    double pourcentageCommission;
    String description;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<EtatAnnonce> etats;

    @Column(name = "etatannonce")
    int etatAnnonce;

    @Column(name = "dateheurecreation")
    LocalDateTime dateHeureCreation;

    @Transient
    boolean favoris = false;
    
}
