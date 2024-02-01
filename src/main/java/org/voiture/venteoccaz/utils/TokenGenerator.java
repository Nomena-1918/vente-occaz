package org.voiture.venteoccaz.utils;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TokenGenerator {

    @Value("${custom.token.key}")
    private static String tokenKey;

    public static String getToken(String username) throws NoSuchAlgorithmException, InvalidKeyException {
        String secretKey = System.currentTimeMillis()+tokenKey;

        // Créer une instance de HMAC avec l'algorithme SHA-256
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        hmac.init(secretKeySpec);

        // Calculer le HMAC du nom d'utilisateur
        byte[] macBytes = hmac.doFinal(username.getBytes());

        // Convertir le résultat en une représentation en base64
        String str = Base64.getEncoder().encodeToString(macBytes);
        List <Character> list = str.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(list);

        return list.stream().map(String::valueOf).collect(Collectors.joining());

    }
}
