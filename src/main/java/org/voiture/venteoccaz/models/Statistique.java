package org.voiture.venteoccaz.models;

import lombok.Data;

@Data
public class Statistique {
    Long nombreUtilisateur;
    double chiffreAffaire;
    double maxCommission;
    double moyenneCommission;
    double minCommission;
}
