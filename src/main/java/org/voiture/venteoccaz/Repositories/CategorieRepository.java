package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    
}
