package org.voiture.venteoccaz.Reponse;

import lombok.Data;

@Data
public class Reponse {
    String code = "200";
    String message = "";
    Object donnee;

    public Reponse(Object data) {
        setDonnee(data);
    }

    public Reponse(String codeErreur, String msg) {
        setCode(codeErreur);
        setMessage(msg);
        setDonnee(new Object[0]);
    }

    public Reponse(String codeErreur, String msg, Object data) {
        setCode(codeErreur);
        setMessage(msg);
        setDonnee(data);
    }
}
