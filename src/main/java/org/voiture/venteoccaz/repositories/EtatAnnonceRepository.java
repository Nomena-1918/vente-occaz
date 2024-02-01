package org.voiture.venteoccaz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.EtatAnnonce;

@Repository
public interface EtatAnnonceRepository extends JpaRepository<EtatAnnonce, Integer> {
    
}

