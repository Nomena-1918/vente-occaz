package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.services.AdminService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/chiffreAffaire")
    public ResponseEntity<Reponse> getChiffreAffaire(@RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getChiffreAffaire(dateDebut, dateFin)));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/chiffreAffaireDefaut")
    public ResponseEntity<Reponse> getChiffreAffaireDefaut() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getChiffreAffaireDefaut()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/countUsersNoAdmin")
    public ResponseEntity<Reponse> getCountUtilisateursNonAdmin() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getCountUtilisateursNonAdmin()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }
    
}
