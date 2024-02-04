package org.voiture.venteoccaz.models.firebase;

import lombok.Data;
import org.voiture.venteoccaz.models.Utilisateur;

@Data
public class UtilisateurFCM {
    private Utilisateur utilisateur;
    private String tokenFcm;
}
