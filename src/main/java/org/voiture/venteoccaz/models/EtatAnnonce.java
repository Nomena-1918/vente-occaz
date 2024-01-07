package org.voiture.venteoccaz.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "etatannonces")
public class EtatAnnonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idetatannonce")
    Integer idEtatAnnonce;

    @ManyToOne
    @JoinColumn(name = "idannonce", nullable = false)
    @JsonIgnore
    Annonce annonce;

    @Column(name = "typeetat")
    int typeEtat;

    @Column(name = "dateheureetat")
    LocalDateTime dateHeureEtat;
}
