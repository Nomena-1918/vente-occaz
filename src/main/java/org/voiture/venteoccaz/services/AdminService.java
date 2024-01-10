package org.voiture.venteoccaz.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.AnnonceRepository;
import org.voiture.venteoccaz.Repositories.CategorieRepository;
import org.voiture.venteoccaz.Repositories.CouleurRepository;
import org.voiture.venteoccaz.Repositories.EtatAnnonceRepository;
import org.voiture.venteoccaz.Repositories.FavorisRepository;
import org.voiture.venteoccaz.Repositories.MarqueRepository;
import org.voiture.venteoccaz.Repositories.ModeleRepository;
import org.voiture.venteoccaz.Repositories.TypeOccasionRepository;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class AdminService {
    private final AnnonceRepository annonceRepository;
    private final EtatAnnonceRepository etatAnnonceRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FavorisRepository favorisRepository;
    private final CategorieRepository categorieRepository;
    private final CouleurRepository couleurRepository;
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;
    private final TypeOccasionRepository typeOccasionRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AdminService(AnnonceRepository annonceRepository, EtatAnnonceRepository etatAnnonceRepository, UtilisateurRepository utilisateurRepository, FavorisRepository favorisRepository, CategorieRepository categorieRepository, CouleurRepository couleurRepository,MarqueRepository marqueRepository,ModeleRepository modeleRepository,TypeOccasionRepository typeOccasionRepository) {
        this.annonceRepository = annonceRepository;
        this.etatAnnonceRepository = etatAnnonceRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.favorisRepository = favorisRepository;
        this.categorieRepository = categorieRepository;
        this.couleurRepository = couleurRepository;
        this.marqueRepository = marqueRepository;
        this.modeleRepository = modeleRepository;
        this.typeOccasionRepository = typeOccasionRepository;
    }

    public double getChiffreAffaire(String dateDebutStr, String dateFinStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        try {
            dateDebut = LocalDate.parse(dateDebutStr, formatter);
            dateFin = LocalDate.parse(dateFinStr, formatter);           
        } catch (Exception e) {
            formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
            dateDebut = LocalDate.parse(dateDebutStr, formatter);
            dateFin = LocalDate.parse(dateFinStr, formatter);  
        }
        finally {
            if(dateDebut == null || dateFin == null) return getChiffreAffaireDefaut();
        }
        return getChiffreAffaireSql(dateDebut, dateFin);
    }

    public double getChiffreAffaireSql(LocalDate dateDebut, LocalDate dateFin) {
        String sqlQuery = "SELECT SUM(commission) as total_commission\r\n" + //
                        "FROM v_annonce_commission \r\n" + //
                        "WHERE typeEtat = 100 AND dateHeureEtat >= :dateDebut AND dateHeureEtat <= :dateFin";
        Query nativeQuery = entityManager.createNativeQuery(sqlQuery);

        // Remplace les paramètres dans la requête
        nativeQuery.setParameter("dateDebut", dateDebut);
        nativeQuery.setParameter("dateFin", dateFin);
    
        // Assurez-vous que le résultat est bien un double
        Object result = nativeQuery.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            // Gérez le cas où le résultat n'est pas un nombre selon vos besoins
            throw new IllegalStateException("La requête n'a pas renvoyé un résultat numérique.");
        }
    }
    
    public double getChiffreAffaireDefaut() {
        String sqlQuery = "SELECT total_commission FROM v_chiffre_affaire_defaut";
        Query nativeQuery = entityManager.createNativeQuery(sqlQuery);
    
        // Assurez-vous que le résultat est bien un double
        Object result = nativeQuery.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            // Gérez le cas où le résultat n'est pas un nombre selon vos besoins
            throw new IllegalStateException("La requête n'a pas renvoyé un résultat numérique.");
        }
    }

    public Long getCountUtilisateursNonAdmin() {
        return utilisateurRepository.countNonAdminUsers();
    }
}
