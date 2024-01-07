package org.voiture.venteoccaz.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "favoris")
public class Favoris {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfavoris")
    Integer idFavoris;

    @ManyToOne
    @JoinColumn(name = "idutilisateur", nullable = false)
    Utilisateur utilisateur;

    @ManyToMany
    @JoinTable(
        name = "favoris_annonces",
        joinColumns = @JoinColumn(name = "idfavoris"),
        inverseJoinColumns = @JoinColumn(name = "idannonce")
    )
    List<Annonce> annonceFavorites;

}
