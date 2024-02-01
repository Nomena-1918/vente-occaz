package org.voiture.venteoccaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.repositories.*;
import org.voiture.venteoccaz.models.*;

import java.util.List;

@Service
public class GestionCritereService {
    private final CategorieRepository categorieRepository;
    private final ModeleRepository modeleRepository;
    private final TypeOccasionRepository typeOccasionRepository;
    private final CouleurRepository couleurRepository;
    private final MarqueRepository marqueRepository;

    @Autowired
    public GestionCritereService(CategorieRepository categorieRepository, ModeleRepository modeleRepository, TypeOccasionRepository typeOccasionRepository, CouleurRepository couleurRepository, MarqueRepository marqueRepository) {
        this.categorieRepository = categorieRepository;
        this.modeleRepository = modeleRepository;
        this.typeOccasionRepository = typeOccasionRepository;
        this.couleurRepository = couleurRepository;
        this.marqueRepository = marqueRepository;
    }

    // CREATE
    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }
    public Modele createModele(Modele modele) {
        return modeleRepository.save(modele);
    }
    public TypeOccasion createTypeOccasion(TypeOccasion typeOccasion) {
        return typeOccasionRepository.save(typeOccasion);
    }
    public Couleur createCouleur(Couleur couleur) {
        return couleurRepository.save(couleur);
    }
    public Marque createMarque(Marque Marque) {
        return marqueRepository.save(Marque);
    }

    // READ
    public List<Categorie> readCategorie() {
        return categorieRepository.findAll();
    }
    public List<Modele> readModele() {
        return modeleRepository.findAll();
    }
    public List<TypeOccasion> readTypeOccasion() {
        return typeOccasionRepository.findAll();
    }
    public List<Couleur> readCouleur() {
        return couleurRepository.findAll();
    }
    public List<Marque> readMarque() {
        return marqueRepository.findAll();
    }


}
