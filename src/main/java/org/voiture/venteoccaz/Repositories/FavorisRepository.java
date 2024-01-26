package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Favoris;

import jakarta.transaction.Transactional;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris, Integer>  {

    @Transactional
    @Modifying
    @Query("DELETE from Favoris f WHERE f.utilisateur.idUtilisateur = :idUtilisateur AND f.idAnnonce = :idAnnonce")
    void deleteFavoris(@Param("idUtilisateur") Integer idUtilisateur, @Param("idAnnonce") Integer idAnnonce);
}
