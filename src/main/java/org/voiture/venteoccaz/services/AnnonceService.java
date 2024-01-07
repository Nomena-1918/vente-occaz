package org.voiture.venteoccaz.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.voiture.venteoccaz.Repositories.AnnonceRepository;
import org.voiture.venteoccaz.Repositories.EtatAnnonceRepository;
import org.voiture.venteoccaz.Repositories.UtilisateurRepository;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.models.EtatAnnonce;

@Service
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final EtatAnnonceRepository etatAnnonceRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public AnnonceService(AnnonceRepository annonceRepository, EtatAnnonceRepository etatAnnonceRepository, UtilisateurRepository utilisateurRepository) {
        this.annonceRepository = annonceRepository;
        this.etatAnnonceRepository = etatAnnonceRepository;
        this.utilisateurRepository = utilisateurRepository;
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

