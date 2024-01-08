package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.TypeOccasion;

@Repository
public interface TypeOccasionRepository extends JpaRepository<TypeOccasion, Integer> {
    
}
