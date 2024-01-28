package org.voiture.venteoccaz.Repositories.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.voiture.venteoccaz.models.notification.UtilisateurFCM;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurFCMRepository extends JpaRepository<UtilisateurFCM, Integer> {
    @Query("select u from UtilisateurFCM u where u.utilisateur.idUtilisateur = :idUtilisateur and u.tokenFcm = :tokenFcm ")
    Optional<UtilisateurFCM> findUtilisateurFCMByUtilisateurAndTokenFcm(@Param("idUtilisateur") Integer idUtilisateur, @Param("tokenFcm") String tokenFcm);

    @Query("select u.tokenFcm from UtilisateurFCM u where u.utilisateur.idUtilisateur = :idUtilisateur")
    Optional<List<String>> findTokenFcmByUtilisateur(@Param("idUtilisateur") Integer idUtilisateur);
}
