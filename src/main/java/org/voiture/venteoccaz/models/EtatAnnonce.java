package org.voiture.venteoccaz.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "etatannonces")
public class EtatAnnonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idEtatAnnonce;

    // @ManyToOne
    // @JoinColumn(name = "idannonce", nullable = false)
    Annonce annonce;

    int typeEtat;

    LocalDateTime dateHeureEtat;
}
