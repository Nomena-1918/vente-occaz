package org.voiture.venteoccaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voiture.venteoccaz.Repositories.AnnonceRepository;
import org.voiture.venteoccaz.models.Annonce;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    public List<Annonce> getUnvalidatedAnnonces() {
        return annonceRepository.getAnnoncesNonValides();
    }
}

