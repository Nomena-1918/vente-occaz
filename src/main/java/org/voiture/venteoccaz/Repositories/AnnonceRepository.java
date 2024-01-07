package org.voiture.venteoccaz.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    @Query("SELECT a FROM Annonce a LEFT JOIN a.etats e WHERE e.annonce IS NULL")
    List<Annonce> getAnnoncesNonValides();
    
}
