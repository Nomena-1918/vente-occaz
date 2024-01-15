package org.voiture.venteoccaz.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

        @Query("SELECT a, true FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE f.idFavoris IS NOT NULL AND f.utilisateur.idUtilisateur = :idUtilisateur ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAllAnnonceFavoris(@Param("idUtilisateur") Integer idUtilisateur);

        @Query("SELECT a, CASE WHEN f.idFavoris IS NOT NULL AND f.utilisateur.idUtilisateur = :idUtilisateur THEN true ELSE false END FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE a.proprietaire.idUtilisateur = :idUtilisateur ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAllAnnonceByIdUtilisateur(@Param("idUtilisateur") Integer idUtilisateur);

        @Query("SELECT a, CASE WHEN f.idFavoris IS NOT NULL AND f.utilisateur.idUtilisateur = :idUtilisateur THEN true ELSE false END FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE e.typeEtat = 10 AND NOT EXISTS (SELECT 1 FROM a.etats e2 WHERE e2.typeEtat = 100) AND a.categorie.idCategorie = :idCategorie AND a.marque.idMarque = :idMarque AND a.modele.idModele = :idModele AND a.prix >= :prixMin AND a.prix <= :prixMax ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAnnoncesValideNonVenduWithFavorisStatus(@Param("idUtilisateur") Integer idUtilisateur,
                @Param("idCategorie") Integer idCategorie, @Param("idMarque") Integer idMarque,
                @Param("idModele") Integer idModele, @Param("prixMin") Double prixMin, @Param("prixMax") Double prixMax);

        @Query("SELECT a, CASE WHEN f.idFavoris IS NOT NULL THEN true ELSE false END FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE e.typeEtat = 10 AND NOT EXISTS (SELECT 1 FROM a.etats e2 WHERE e2.typeEtat = 100) AND a.categorie.idCategorie = :idCategorie AND a.marque.idMarque = :idMarque AND a.modele.idModele = :idModele AND a.prix >= :prixMin AND a.prix <= :prixMax ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAnnoncesValideNonVenduWithFavorisStatus(
                @Param("idCategorie") Integer idCategorie, @Param("idMarque") Integer idMarque,
                @Param("idModele") Integer idModele, @Param("prixMin") Double prixMin, @Param("prixMax") Double prixMax);

        @Query("SELECT a, CASE WHEN f.idFavoris IS NOT NULL AND f.utilisateur.idUtilisateur = :idUtilisateur THEN true ELSE false END FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE e.typeEtat = 10 AND NOT EXISTS (SELECT 1 FROM a.etats e2 WHERE e2.typeEtat = 100) ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAnnoncesValideNonVenduWithFavorisStatus(@Param("idUtilisateur") Integer idUtilisateur);

        @Query("SELECT a, CASE WHEN f.idFavoris IS NOT NULL THEN true ELSE false END FROM Annonce a LEFT JOIN Favoris f ON a.idAnnonce = f.idAnnonce LEFT JOIN a.etats e WHERE e.typeEtat = 10 AND NOT EXISTS (SELECT 1 FROM a.etats e2 WHERE e2.typeEtat = 100) ORDER BY e.dateHeureEtat DESC")
        List<Object[]> getAnnoncesValideNonVenduWithFavorisStatus();

        @Query("SELECT a FROM Annonce a LEFT JOIN a.etats e WHERE e.annonce IS NULL ORDER BY e.dateHeureEtat DESC")
        List<Annonce> getAnnoncesNonValides();

        Annonce findByIdAnnonce(int idAnnonce);

        @Modifying
        @Query("UPDATE Annonce a SET a.pourcentageCommission = :pourcentageCommission WHERE a.idAnnonce = :idAnnonce")
        void updatePourcentageCommission(@Param("idAnnonce") Integer idAnnonce,
            @Param("pourcentageCommission") Double pourcentageCommission);

}
