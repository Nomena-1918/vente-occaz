package org.voiture.venteoccaz.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.reponse.Reponse;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.services.AnnonceService;
import org.voiture.venteoccaz.services.authentification.AuthService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/annonces")
public class AnnonceController {
    private final AnnonceService annonceService;
    private final AuthService authService;

    @Autowired
    public AnnonceController(AnnonceService annonceService, AuthService authService) {
        this.annonceService = annonceService;
        this.authService = authService;
    }

    @GetMapping("/all/{idUtilisateur}")
    public ResponseEntity<Reponse> getAllAnnonceByIdUtilisateur(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> favoris = annonceService.getAllAnnonceByIdUtilisateur(idUtilisateur);
            return authService.secure(headers, favoris);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/favoris/{idUtilisateur}")
    public ResponseEntity<Reponse> getFavoris(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> favoris = annonceService.getAllAnnonceFavoris(idUtilisateur);
            return authService.secure(headers, favoris);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/historique-annonce/{idUtilisateur}")
    public ResponseEntity<Reponse> getHistoriqueAnnonce(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> historiqueAnnonce = annonceService.getAllAnnonceByIdUtilisateur(idUtilisateur);
            return authService.secure(headers, historiqueAnnonce);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/setAnnonceVendue")
    public ResponseEntity<Reponse> setAnnonceVendue(@RequestHeader Map<String,String> headers, @RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.setAnnonceVendue(idUtilisateur, idAnnonce, null);
            return authService.secure(headers, "");
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/creerAnnonce")
    public ResponseEntity<Reponse> creerAnnonce(@RequestHeader Map<String,String> headers, @RequestParam Integer idUtilisateur, @RequestParam Integer idMarque,@RequestParam Integer idModele, @RequestParam Integer idCategorie,@RequestParam Integer idTypeOccasion,@RequestParam Integer idCouleur,@RequestParam double prix, @RequestParam("photo1") String photo1,@RequestParam("photo2") String photo2, @RequestParam("photo3") String photo3, @RequestParam("photo4") String photo4, @RequestParam String description) {
        try {
            String [] listePhotos = {photo1, photo2, photo3, photo4};
            annonceService.creerAnnonce(idUtilisateur, idMarque, idModele, idCategorie, idTypeOccasion, idCouleur, prix, listePhotos, description);
            return authService.secure(headers, "");
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/enleverFavoris") 
    public ResponseEntity<Reponse> enleverFavoris(@RequestHeader Map<String,String> headers, @RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.enleverFavoris(idUtilisateur, idAnnonce);
            return authService.secure(headers, "");
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/mettreFavoris") 
    public ResponseEntity<Reponse> mettreFavoris(@RequestHeader Map<String,String> headers, @RequestParam Integer idUtilisateur,@RequestParam Integer idAnnonce) {
        try {
            annonceService.setFavoris(idUtilisateur, idAnnonce);
            return authService.secure(headers, "");
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues/{idUtilisateur}/{idCategorie}/{idMarque}/{idModele}/{prixMin}/{prixMax}")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur ,@PathVariable Integer idCategorie,@PathVariable Integer idMarque,@PathVariable Integer idModele,@PathVariable Double prixMin,@PathVariable  Double prixMax) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(idUtilisateur, idCategorie, idMarque, idModele, prixMin, prixMax);
            return authService.secure(headers, allAnnoncesValidesNonVendues);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues/{idCategorie}/{idMarque}/{idModele}/{prixMin}/{prixMax}")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces(@PathVariable Integer idCategorie,@PathVariable Integer idMarque,@PathVariable Integer idModele,@PathVariable Double prixMin,@PathVariable  Double prixMax) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(idCategorie, idMarque, idModele, prixMin, prixMax);
            return ResponseEntity.ok(new Reponse("200","liste des annonces", allAnnoncesValidesNonVendues));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues-nofiltre")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces() {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues();
            return ResponseEntity.ok(new Reponse("200","liste des annonces no login", allAnnoncesValidesNonVendues));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues-nofiltre/{idUtilisateur}")
    public ResponseEntity<Reponse> getValidatedNonVendueAnnonces(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(idUtilisateur);
            return authService.secure(headers, allAnnoncesValidesNonVendues);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/validation")
    public ResponseEntity<Reponse> validerAnnonce(@RequestHeader Map<String,String> headers, @RequestParam Integer idAnnonce,@RequestParam double pourcentageCommission,@RequestParam Integer idAdmin) {
        try {
            annonceService.validerAnnonce(idAnnonce, pourcentageCommission, idAdmin, null);
            return authService.secure(headers, "");
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/{idAnnonce}")
    public ResponseEntity<Reponse> getAnnonceById(@RequestHeader Map<String,String> headers, @PathVariable int idAnnonce) {
        try {
            Annonce annonce = annonceService.findByIdAnnonce(idAnnonce);
            return authService.secure(headers, annonce);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-validees")
    public ResponseEntity<Reponse> getNonValidatedAnnonces(@RequestHeader Map<String,String> headers) {
        try {
            List<Annonce> nonValidatedAnnonces = annonceService.getUnvalidatedAnnonces();
            return authService.secure(headers, nonValidatedAnnonces);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/non-vendues-nofiltre-etat-favori/{idUtilisateur}")
    public ResponseEntity<Reponse> getAnnonceEtatFavoriValidesNonVendues(@RequestHeader Map<String,String> headers, @PathVariable Integer idUtilisateur) {
        try {
            List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAnnonceEtatFavoriValidesNonVendues(idUtilisateur);
            return authService.secure(headers, allAnnoncesValidesNonVendues);
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

}

