package org.voiture.venteoccaz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.services.AnnonceService;

@RestController
@RequestMapping("api/v1/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/non-validees")
    public ResponseEntity<List<Annonce>> getNonValidatedAnnonces() {
        List<Annonce> nonValidatedAnnonces = annonceService.getUnvalidatedAnnonces();
        return ResponseEntity.ok(nonValidatedAnnonces);
    }
}

