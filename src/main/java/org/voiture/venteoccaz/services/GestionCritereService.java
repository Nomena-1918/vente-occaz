package org.voiture.venteoccaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.CategorieRepository;
import org.voiture.venteoccaz.models.Categorie;

@Service
public class GestionCritereService {
    private final CategorieRepository categorieRepository;

    @Autowired
    public GestionCritereService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }
}
