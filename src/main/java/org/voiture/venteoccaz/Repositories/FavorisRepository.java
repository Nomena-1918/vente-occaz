package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Favoris;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris, Integer>  {
    
}
