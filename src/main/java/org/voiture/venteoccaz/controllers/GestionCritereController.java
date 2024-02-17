package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.reponse.Reponse;
import org.voiture.venteoccaz.repositories.*;
import org.voiture.venteoccaz.models.*;
import org.voiture.venteoccaz.services.authentification.AuthService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class GestionCritereController {
    private final CategorieRepository categorieRepository;
    private final ModeleRepository modeleRepository;
    private final TypeOccasionRepository typeOccasionRepository;
    private final CouleurRepository couleurRepository;
    private final MarqueRepository marqueRepository;
    private final AuthService authService;

    @Autowired
    public GestionCritereController(CategorieRepository categorieRepository, ModeleRepository modeleRepository, TypeOccasionRepository typeOccasionRepository, CouleurRepository couleurRepository, MarqueRepository marqueRepository, AuthService authService) {
        this.categorieRepository = categorieRepository;
        this.modeleRepository = modeleRepository;
        this.typeOccasionRepository = typeOccasionRepository;
        this.couleurRepository = couleurRepository;
        this.marqueRepository = marqueRepository;
        this.authService = authService;
    }


    // CREATE
    @PostMapping("categories")
    public ResponseEntity<Reponse> createCategorie(@RequestBody Categorie categorie, @RequestHeader Map<String,String> headers) {
        return authService.secure(headers, categorieRepository.save(categorie));
    }
    @PostMapping("modeles")
    public ResponseEntity<Reponse> createModele(@RequestBody Modele modele, @RequestHeader Map<String,String> headers) {
        return authService.secure(headers, modeleRepository.save(modele));
    }
    @PostMapping("type-occasions")
    public ResponseEntity<Reponse> createTypeOccasion(@RequestBody TypeOccasion typeOccasion, @RequestHeader Map<String,String> headers) {
        return authService.secure(headers, typeOccasionRepository.save(typeOccasion));
    }
    @PostMapping("couleurs")
    public ResponseEntity<Reponse> createCouleur(@RequestBody Couleur couleur, @RequestHeader Map<String,String> headers) {
        return authService.secure(headers, couleurRepository.save(couleur));
    }
    @PostMapping("marques")
    public ResponseEntity<Reponse> createMarque(@RequestBody Marque Marque, @RequestHeader Map<String,String> headers) {
        return authService.secure(headers,  marqueRepository.save(Marque));
    }

    // READ
    @GetMapping("categories")
    public ResponseEntity<Reponse> readCategorie(@RequestHeader Map<String,String> headers) {
        return authService.secure(headers, categorieRepository.findAll());
    }
    @GetMapping("modeles")
    public ResponseEntity<Reponse> readModele(@RequestHeader Map<String,String> headers) {
        return authService.secure(headers, modeleRepository.findAll());
    }
    @GetMapping("type-occasions")
    public ResponseEntity<Reponse> readTypeOccasion(@RequestHeader Map<String,String> headers) {
        return authService.secure(headers, typeOccasionRepository.findAll());
    }

    @GetMapping("couleurs")
    public ResponseEntity<Reponse> readCouleur(@RequestHeader Map<String,String> headers) {
        return authService.secure(headers, couleurRepository.findAll());
    }

    @GetMapping("marques")
    public ResponseEntity<Reponse> readMarque(@RequestHeader Map<String,String> headers) {
        return authService.secure(headers, marqueRepository.findAll());
    }


    // READ NO-AUTH
    @GetMapping("/no-auth/categories")
    public ResponseEntity<Reponse> readCategorieNoAuth() {
        return ResponseEntity.ok(new Reponse(categorieRepository.findAll()));
    }
    @GetMapping("/no-auth/modeles")
    public ResponseEntity<Reponse> readModeleNoAuth() {
        return ResponseEntity.ok(new Reponse(modeleRepository.findAll()));
    }
    @GetMapping("/no-auth/type-occasions")
    public ResponseEntity<Reponse> readTypeOccasionNoAuth() {
        return ResponseEntity.ok(new Reponse(typeOccasionRepository.findAll()));
    }

    @GetMapping("/no-auth/couleurs")
    public ResponseEntity<Reponse> readCouleurNoAuth() {
        return ResponseEntity.ok(new Reponse(couleurRepository.findAll()));
    }

    @GetMapping("/no-auth/marques")
    public ResponseEntity<Reponse> readMarqueNoAuth() {
        return ResponseEntity.ok(new Reponse(marqueRepository.findAll()));
    }


}
