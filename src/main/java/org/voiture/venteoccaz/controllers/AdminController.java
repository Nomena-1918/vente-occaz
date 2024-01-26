package org.voiture.venteoccaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voiture.venteoccaz.Reponse.Reponse;
import org.voiture.venteoccaz.services.AdminService;
import org.voiture.venteoccaz.services.authentification.AuthService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    private final AuthService authService;

    @Autowired
    public AdminController(AdminService adminService, AuthService authService) {
        this.adminService = adminService;
        this.authService = authService;
    }

    @PostMapping("/statistique")
    public ResponseEntity<Reponse> getStatistique(@RequestHeader Map<String,String> headers, @RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return authService.secure(headers, adminService.getStatistique(dateDebut, dateFin));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/statistiqueDefaut")
    public ResponseEntity<Reponse> getStatistiqueDefaut(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getStatistiqueDefaut());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/minCommissionDefaut")
    public ResponseEntity<Reponse> getMinCommissionDefaut(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getMinCommissionDefaut());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/minCommission")
    public ResponseEntity<Reponse> getMinCommission(@RequestHeader Map<String,String> headers, @RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return authService.secure(headers, adminService.getMinCommission(dateDebut, dateFin));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/moyenneCommissionDefaut")
    public ResponseEntity<Reponse> getMoyenneCommissionDefaut(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getMoyenneCommissionDefaut());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/moyenneCommission")
    public ResponseEntity<Reponse> getMoyenneCommission(@RequestHeader Map<String,String> headers, @RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return authService.secure(headers, adminService.getMoyenneCommission(dateDebut, dateFin));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/maxCommissionDefaut")
    public ResponseEntity<Reponse> getMaxCommissionDefaut(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getMaxCommissionDefaut());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/maxCommission")
    public ResponseEntity<Reponse> getMaxCommission(@RequestHeader Map<String,String> headers, @RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return authService.secure(headers, adminService.getMaxCommission(dateDebut, dateFin));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/chiffreAffaire")
    public ResponseEntity<Reponse> getChiffreAffaire(@RequestHeader Map<String,String> headers, @RequestParam String dateDebut, @RequestParam String dateFin) {
        try {
            return authService.secure(headers, adminService.getChiffreAffaire(dateDebut, dateFin));
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @PostMapping("/chiffreAffaireDefaut")
    public ResponseEntity<Reponse> getChiffreAffaireDefaut(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getChiffreAffaireDefaut());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }

    @GetMapping("/countUsersNoAdmin")
    public ResponseEntity<Reponse> getCountUtilisateursNonAdmin(@RequestHeader Map<String,String> headers) {
        try {
            return authService.secure(headers, adminService.getCountUtilisateursNonAdmin());
        } catch (Exception e) {
            return ResponseEntity.ok(new Reponse("500", e.getMessage()));
        }
    }
    
}
