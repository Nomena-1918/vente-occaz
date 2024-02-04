package org.voiture.venteoccaz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.voiture.venteoccaz.models.Session;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.Utilisateur;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query("SELECT s FROM Session s WHERE s.token = :token and s.isConnected = 1")
    Optional<Session> findByToken(String token);

    @Query("SELECT s FROM Session s WHERE s.utilisateur.idUtilisateur = :idUtilisateur")
    Optional<Session> findAllByUtilisateur(Integer idUtilisateur);

    @Query("SELECT s FROM Session s WHERE s.code = :code and s.isConnected = 1")
    Optional<Session> findAllByCodeConnected(String code);

    @Query("SELECT s FROM Session s WHERE s.utilisateur.idUtilisateur = :idUtilisateur and s.tokenFcm = :tokenFcm")
    Optional<Session> findAllByUtilisateurTokenFcm(Integer idUtilisateur, String tokenFcm);

    @Query("SELECT s.tokenFcm FROM Session s WHERE s.utilisateur.idUtilisateur = :idUtilisateur and s.isConnected = 1")
    Optional<List<String>> findTokenFcmByUtilisateur(Integer idUtilisateur);
}
