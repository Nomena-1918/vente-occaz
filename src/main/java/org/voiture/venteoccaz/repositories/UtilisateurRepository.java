package org.voiture.venteoccaz.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.voiture.venteoccaz.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.isAdmin = 0")
    Long countNonAdminUsers();

    @Query("SELECT u FROM Utilisateur u WHERE u.email = :email and u.motDePasse = :motDePasse")
    Optional<Utilisateur> findAllByEmailAndMotDePasse(@PathVariable String email, @PathVariable String motDePasse);
    
}
