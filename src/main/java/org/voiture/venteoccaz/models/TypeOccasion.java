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
@Table(name = "typeoccasions")
public class TypeOccasion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtypeoccasion")
    Integer idTypeOccasion;
    @Column(name = "nomtypeoccasion")
    String nomTypeOccasion;
    
}
