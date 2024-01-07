package org.voiture.venteoccaz.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "typeoccasions")
public class TypeOccasion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idTypeOccasion;
    String nomTypeOccasion;
    
}
