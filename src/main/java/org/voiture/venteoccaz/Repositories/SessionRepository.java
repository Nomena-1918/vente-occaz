package org.voiture.venteoccaz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.voiture.venteoccaz.models.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @Query("SELECT s FROM Session s WHERE s.token = :token")
    Optional<Session> findByToken(String token);

    @Query("SELECT s FROM Session s WHERE s.utilisateur.idUtilisateur = :idUtilisateur")
    Optional<Session> findAllByUtilisateur(Integer idUtilisateur);
}
