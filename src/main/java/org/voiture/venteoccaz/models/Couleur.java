package org.voiture.venteoccaz.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "couleurs")
public class Couleur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idCouleur;
    String nomCouleur;
    
}
