package br.com.tads.manutencaoequipamentoapi.commom;

public class Util {
    public static String onlyNumbers(String str) {
        return str.replaceAll("[^0-9]", "");
    }
}
