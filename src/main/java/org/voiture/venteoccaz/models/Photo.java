package org.voiture.venteoccaz.models;

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
@Table(name = "photos")
public class Photo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphoto")
    Integer idPhoto;

    @ManyToOne
    @JoinColumn(name = "idannonce", nullable = false)
    @JsonIgnore
    Annonce annonce;

    String repertoire;
    
}
