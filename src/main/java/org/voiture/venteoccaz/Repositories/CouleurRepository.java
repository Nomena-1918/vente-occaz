package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Couleur;

@Repository
public interface CouleurRepository  extends JpaRepository<Couleur, Integer> {
    
}
