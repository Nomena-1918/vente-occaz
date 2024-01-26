package org.voiture.venteoccaz.util;

public class CodeGenerator {
    public static String getCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }
}
