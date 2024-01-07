package org.voiture.venteoccaz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.services.AnnonceService;

@RestController
@RequestMapping("api/v1/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @PostMapping("/validation")
    public ResponseEntity<Reponse> validerAnnonce(@RequestParam Integer idAnnonce,@RequestParam double pourcentageCommission,@RequestParam Integer idAdmin) {
        try {
            annonceService.validerAnnonce(idAnnonce, pourcentageCommission, idAdmin, null);
            return ResponseEntity.ok(new Reponse(""));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/{idAnnonce}")
    public ResponseEntity<Reponse> getAnnonceById(@PathVariable int idAnnonce) {
        try {
            Annonce annonce = annonceService.findByIdAnnonce(idAnnonce);
            return ResponseEntity.ok(new Reponse(annonce));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-validees")
    public ResponseEntity<Reponse> getNonValidatedAnnonces() {
        try {
            List<Annonce> nonValidatedAnnonces = annonceService.getUnvalidatedAnnonces();
            return ResponseEntity.ok(new Reponse(nonValidatedAnnonces));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }
}

