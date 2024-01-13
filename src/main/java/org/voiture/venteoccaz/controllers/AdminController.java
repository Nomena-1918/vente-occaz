package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.services.AdminService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/statistique")
    public ResponseEntity<Reponse> getStatistique(@RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getStatistique(dateDebut, dateFin)));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/statistiqueDefaut")
    public ResponseEntity<Reponse> getStatistiqueDefaut() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getStatistiqueDefaut()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/minCommissionDefaut")
    public ResponseEntity<Reponse> getMinCommissionDefaut() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMinCommissionDefaut()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/minCommission")
    public ResponseEntity<Reponse> getMinCommission(@RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMinCommission(dateDebut, dateFin)));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/moyenneCommissionDefaut")
    public ResponseEntity<Reponse> getMoyenneCommissionDefaut() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMoyenneCommissionDefaut()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/moyenneCommission")
    public ResponseEntity<Reponse> getMoyenneCommission(@RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMoyenneCommission(dateDebut, dateFin)));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/maxCommissionDefaut")
    public ResponseEntity<Reponse> getMaxCommissionDefaut() {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMaxCommissionDefaut()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/maxCommission")
    public ResponseEntity<Reponse> getMaxCommission(@RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return ResponseEntity.ok(new Reponse(adminService.getMaxCommission(dateDebut, dateFin)));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

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
