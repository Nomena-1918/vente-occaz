package org.voiture.venteoccaz.Repositories;

import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.isAdmin = 0")
    Long countNonAdminUsers();
    
}
