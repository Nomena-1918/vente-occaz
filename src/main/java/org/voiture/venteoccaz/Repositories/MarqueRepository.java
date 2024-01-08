package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Marque;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Integer> {
    
}
