package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Modele;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Integer> {
    
}
