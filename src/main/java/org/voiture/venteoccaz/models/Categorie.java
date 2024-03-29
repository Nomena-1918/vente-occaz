package org.voiture.venteoccaz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategorie")
    Integer idCategorie;

    @Column(name = "nomcategorie", nullable = false)
    String nomCategorie;
    
}
