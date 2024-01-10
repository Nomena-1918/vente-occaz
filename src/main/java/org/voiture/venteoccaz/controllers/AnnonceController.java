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

    @GetMapping("/favoris/{idUtilisateur}")
    public ResponseEntity<Reponse> getFavoris(@PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> favoris = annonceService.getAllAnnonceFavoris(idUtilisateur);
            return ResponseEntity.ok(new Reponse(favoris));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/historique-annonce/{idUtilisateur}")
    public ResponseEntity<Reponse> getHistoriqueAnnonce(@PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> historiqueAnnonce = annonceService.getAllAnnonceByIdUtilisateur(idUtilisateur);
            return ResponseEntity.ok(new Reponse(historiqueAnnonce));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/setAnnonceVendue")
    public ResponseEntity<Reponse> setAnnonceVendue(@RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.setAnnonceVendue(idUtilisateur, idAnnonce, null);
            return ResponseEntity.ok(new Reponse(""));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/creerAnnonce")
    public ResponseEntity<Reponse> creerAnnonce(@RequestParam Integer idUtilisateur, @RequestParam Integer idMarque,@RequestParam Integer idModele, @RequestParam Integer idCategorie,@RequestParam Integer idTypeOccasion,@RequestParam Integer idCouleur,@RequestParam double prix, @RequestParam("photo1") String photo1,@RequestParam("photo2") String photo2, @RequestParam("photo3") String photo3, @RequestParam("photo4") String photo4, @RequestParam String description) {
        try {
            String [] listePhotos = {photo1, photo2, photo3, photo4};
            annonceService.creerAnnonce(idUtilisateur, idMarque, idModele, idCategorie, idTypeOccasion, idCouleur, prix, listePhotos, description);
            return ResponseEntity.ok(new Reponse(""));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/enleverFavoris") 
    public ResponseEntity<Reponse> enleverFavoris(@RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.enleverFavoris(idUtilisateur, idAnnonce);
            return ResponseEntity.ok(new Reponse(""));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/mettreFavoris") 
    public ResponseEntity<Reponse> mettreFavoris(@RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.setFavoris(idUtilisateur, idAnnonce);
            return ResponseEntity.ok(new Reponse(""));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues/{idUtilisateur}/{idCategorie}/{idMarque}/{idModele}/{prixMin}/{prixMax}")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces(@PathVariable Integer idUtilisateur ,@PathVariable Integer idCategorie,@PathVariable Integer idMarque,@PathVariable Integer idModele,@PathVariable Double prixMin,@PathVariable  Double prixMax) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(idUtilisateur, idCategorie, idMarque, idModele, prixMin, prixMax);
            return ResponseEntity.ok(new Reponse(allAnnoncesValidesNonVendues));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues-nofiltre/{idUtilisateur}")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces(@PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(idUtilisateur);
            return ResponseEntity.ok(new Reponse(allAnnoncesValidesNonVendues));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

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

