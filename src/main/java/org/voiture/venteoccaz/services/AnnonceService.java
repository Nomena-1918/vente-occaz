package org.voiture.venteoccaz.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.voiture.venteoccaz.Repositories.AnnonceRepository;
import org.voiture.venteoccaz.Repositories.CategorieRepository;
import org.voiture.venteoccaz.Repositories.CouleurRepository;
import org.voiture.venteoccaz.Repositories.EtatAnnonceRepository;
import org.voiture.venteoccaz.Repositories.FavorisRepository;
import org.voiture.venteoccaz.Repositories.MarqueRepository;
import org.voiture.venteoccaz.Repositories.ModeleRepository;
import org.voiture.venteoccaz.Repositories.TypeOccasionRepository;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.models.EtatAnnonce;
import org.voiture.venteoccaz.models.Favoris;
import org.voiture.venteoccaz.models.Photo;

@Service
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final EtatAnnonceRepository etatAnnonceRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FavorisRepository favorisRepository;
    private final CategorieRepository categorieRepository;
    private final CouleurRepository couleurRepository;
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;
    private final TypeOccasionRepository typeOccasionRepository;

    @Autowired
    public AnnonceService(AnnonceRepository annonceRepository, EtatAnnonceRepository etatAnnonceRepository, UtilisateurRepository utilisateurRepository, FavorisRepository favorisRepository, CategorieRepository categorieRepository, CouleurRepository couleurRepository,MarqueRepository marqueRepository,ModeleRepository modeleRepository,TypeOccasionRepository typeOccasionRepository) {
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

    // favoris
    public List<Annonce> getAllAnnonceFavoris(Integer idUtilisateur) {
        return getAnnonceWithFavoris(annonceRepository.getAllAnnonceFavoris(idUtilisateur));
    }

    // vente
    public void setAnnonceVendue(Integer idUtilisateur, Integer idAnnonce, LocalDateTime dateHeureVente) {
        if(dateHeureVente == null) dateHeureVente = LocalDateTime.now();

        // Insertion dans EtatAnnonce
        EtatAnnonce etatAnnonce = new EtatAnnonce();
        etatAnnonce.setAnnonce(annonceRepository.findById(idAnnonce).orElse(null));
        etatAnnonce.setTypeEtat(100);
        etatAnnonce.setUtilisateur(utilisateurRepository.findById(idUtilisateur).orElse(null));
        etatAnnonce.setDateHeureEtat(dateHeureVente);

        etatAnnonceRepository.save(etatAnnonce);
    }

    // historique
    public List<Annonce> getAllAnnonceByIdUtilisateur(Integer idUtilisateur) {
        return getAnnonceWithFavoris(annonceRepository.getAllAnnonceByIdUtilisateur(idUtilisateur));
    }

    public void creerAnnonce(Integer idUtilisateur, Integer idMarque,Integer idModele, Integer idCategorie,Integer idTypeOccasion,Integer idCouleur,double prix, String [] listePhotos, String description) {
        Annonce annonce = new Annonce();
        annonce.setProprietaire(utilisateurRepository.findById(idUtilisateur).orElse(null));
        annonce.setCategorie(categorieRepository.findById(idCategorie).orElse(null));
        annonce.setCouleur(couleurRepository.findById(idCouleur).orElse(null));
        annonce.setMarque(marqueRepository.findById(idMarque).orElse(null));
        annonce.setModele(modeleRepository.findById(idModele).orElse(null));
        annonce.setTypeOccasion(typeOccasionRepository.findById(idTypeOccasion).orElse(null));

        List<Photo> photos = new ArrayList<>();
        Photo photo = null;
        for (String repertoire : listePhotos) {
            photo = new Photo();
            photo.setRepertoire(repertoire);
            photo.setAnnonce(annonce);
            photos.add(photo);
        }
        annonce.setListePhotos(photos);

        annonce.setPrix(prix);
        annonce.setDescription(description);
        annonce.setEtatAnnonce(1);
        annonce.setDateHeureCreation(LocalDateTime.now());

        annonceRepository.save(annonce);
    }
    
    public void enleverFavoris(Integer idUtilisateur, Integer idAnnonce) {
        favorisRepository.deleteFavoris(idUtilisateur, idAnnonce);
    }

    public void setFavoris(Integer idUtilisateur,Integer idAnnonce) {
        Favoris favoris = new Favoris();
        favoris.setUtilisateur(utilisateurRepository.findById(idUtilisateur).orElse(null));
        favoris.setIdAnnonce(idAnnonce);
        favorisRepository.save(favoris);
    }

    public List<Annonce> getAllAnnoncesValidesNonVendues(Integer idUtilisateur ,Integer idCategorie,Integer idMarque,Integer idModele,Double prixMin, Double prixMax) {
        if(idCategorie == null || idMarque == null || idModele == null || prixMin == null || prixMax == null) {
            return getAllAnnoncesValidesNonVendues(idUtilisateur);
        }
        
        List<Object []> annoncesWithFavorisStatus = annonceRepository.getAnnoncesValideNonVenduWithFavorisStatus(idUtilisateur, idCategorie, idMarque, idModele, prixMin, prixMax);
        return getAnnonceWithFavoris(annoncesWithFavorisStatus);
    }

    public List<Annonce> getAllAnnoncesValidesNonVendues(Integer idUtilisateur) {
        List<Object[]> annoncesWithFavorisStatus = annonceRepository.getAnnoncesValideNonVenduWithFavorisStatus(idUtilisateur);
        return getAnnonceWithFavoris(annoncesWithFavorisStatus);
    }

    public List<Annonce> getAllAnnoncesValidesNonVendues(Integer idCategorie,Integer idMarque,Integer idModele,Double prixMin, Double prixMax) {
        if(idCategorie == null || idMarque == null || idModele == null || prixMin == null || prixMax == null) {
            return getAllAnnoncesValidesNonVendues();
        }
        
        List<Object []> annoncesWithFavorisStatus = annonceRepository.getAnnoncesValideNonVenduWithFavorisStatus(idCategorie, idMarque, idModele, prixMin, prixMax);
        return getAnnonceWithFavoris(annoncesWithFavorisStatus);
    }

    public List<Annonce> getAllAnnoncesValidesNonVendues() {
        return annonceRepository.getAnnoncesValideNonVenduWithFavorisStatus();
    }

    public List<Annonce> getAnnonceEtatFavoriValidesNonVendues(Integer idUtilisateur) {
        return  getAnnonceWithFavoris(annonceRepository.getAnnonceEtatFavoriValidesNonVendues(idUtilisateur));
    }

    public List<Annonce> getAnnonceWithFavoris(List<Object []> annoncesWithFavorisStatus) {
        List<Annonce> allAnnoncesValidesNonVendues =  new ArrayList<>();
        Annonce annonce = null;
        for (Object [] annonceWithFavoris : annoncesWithFavorisStatus) {
            annonce = (Annonce) annonceWithFavoris[0];
            annonce.setFavoris((boolean) annonceWithFavoris[1]);
            allAnnoncesValidesNonVendues.add(annonce);
        }
        return allAnnoncesValidesNonVendues;
    }

    public List<Annonce> getUnvalidatedAnnonces() {
        return annonceRepository.getAnnoncesNonValides();
    }

    public Annonce findByIdAnnonce(int id) {
        return annonceRepository.findByIdAnnonce(id);
    }

    @Transactional
    public void validerAnnonce(Integer idAnnonce, Double pourcentageCommission, Integer idAdmin, LocalDateTime dateHeureValidation) throws Exception {
        if (dateHeureValidation == null) dateHeureValidation = LocalDateTime.now();
        // Mise à jour de pourcentageCommission
        annonceRepository.updatePourcentageCommission(idAnnonce, pourcentageCommission);

        // Insertion dans EtatAnnonce
        EtatAnnonce etatAnnonce = new EtatAnnonce();
        etatAnnonce.setAnnonce(annonceRepository.findById(idAnnonce).orElse(null));
        etatAnnonce.setTypeEtat(10);
        etatAnnonce.setUtilisateur(utilisateurRepository.findById(idAdmin).orElse(null));
        etatAnnonce.setDateHeureEtat(dateHeureValidation);

        etatAnnonceRepository.save(etatAnnonce);
    }
}

