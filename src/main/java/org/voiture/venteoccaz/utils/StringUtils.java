package org.voiture.venteoccaz.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StringUtils {
    public static String tronquer(String texte, int longueurMax) {
        if (texte == null || texte.length() <= longueurMax) {
            return texte;
        }
        return texte.substring(0, longueurMax - 3) + "...";
    }

    public static String formaterDateTime(LocalDateTime localDateTime) {
        Locale locale = new Locale("fr", "FR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE. d MMM yyyy HH:mm:ss", locale);
        return formatter.format(localDateTime);
    }

}
