package org.voiture.venteoccaz.Repositories;

import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    
}
