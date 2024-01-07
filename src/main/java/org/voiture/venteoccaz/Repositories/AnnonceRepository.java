package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    
}
