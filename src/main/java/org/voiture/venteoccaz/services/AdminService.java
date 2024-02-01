package org.voiture.venteoccaz.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.repositories.AnnonceRepository;
import org.voiture.venteoccaz.repositories.CategorieRepository;
import org.voiture.venteoccaz.repositories.CouleurRepository;
import org.voiture.venteoccaz.repositories.EtatAnnonceRepository;
import org.voiture.venteoccaz.repositories.FavorisRepository;
import org.voiture.venteoccaz.repositories.MarqueRepository;
import org.voiture.venteoccaz.repositories.ModeleRepository;
import org.voiture.venteoccaz.repositories.TypeOccasionRepository;
import org.voiture.venteoccaz.repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Statistique;

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

    public Statistique getStatistique(String dateDebutStr, String dateFinStr) {
        Statistique statistique = new Statistique();
        statistique.setNombreUtilisateur(getCountUtilisateursNonAdmin());
        statistique.setChiffreAffaire(getChiffreAffaire(dateDebutStr, dateFinStr));
        statistique.setMaxCommission(getMaxCommission(dateDebutStr, dateFinStr));
        statistique.setMoyenneCommission(getMoyenneCommission(dateDebutStr, dateFinStr));
        statistique.setMinCommission(getMinCommission(dateDebutStr, dateFinStr));
        return statistique;
    }

    public Statistique getStatistiqueDefaut() {
        Statistique statistique = new Statistique();
        statistique.setNombreUtilisateur(getCountUtilisateursNonAdmin());
        statistique.setChiffreAffaire(getChiffreAffaireDefaut());
        statistique.setMaxCommission(getMaxCommissionDefaut());
        statistique.setMoyenneCommission(getMoyenneCommissionDefaut());
        statistique.setMinCommission(getMinCommissionDefaut());
        return statistique;
    }

    public double getMinCommission(String dateDebutStr, String dateFinStr) {
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
            if(dateDebut == null || dateFin == null) return getMinCommissionDefaut();
        }
        return getMinCommissionSql(dateDebut, dateFin);
    }

    public double getMinCommissionDefaut() {
        String sqlQuery = "SELECT min_commission FROM v_commission_defaut";
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

    public double getMinCommissionSql(LocalDate dateDebut, LocalDate dateFin) {
        String sqlQuery = "SELECT MIN(commission) as total_commission\r\n" + //
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

    public double getMoyenneCommission(String dateDebutStr, String dateFinStr) {
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
            if(dateDebut == null || dateFin == null) return getMoyenneCommissionDefaut();
        }
        return getMoyenneCommissionSql(dateDebut, dateFin);
    }

    public double getMoyenneCommissionDefaut() {
        String sqlQuery = "SELECT moyenne_commission FROM v_commission_defaut";
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

    public double getMoyenneCommissionSql(LocalDate dateDebut, LocalDate dateFin) {
        String sqlQuery = "SELECT AVG(commission) as total_commission\r\n" + //
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

    public double getMaxCommission(String dateDebutStr, String dateFinStr) {
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
            if(dateDebut == null || dateFin == null) return getMaxCommissionDefaut();
        }
        return getMaxCommissionSql(dateDebut, dateFin);
    }

    public double getMaxCommissionDefaut() {
        String sqlQuery = "SELECT max_commission FROM v_commission_defaut";
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

    public double getMaxCommissionSql(LocalDate dateDebut, LocalDate dateFin) {
        String sqlQuery = "SELECT MAX(commission) as total_commission\r\n" + //
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
