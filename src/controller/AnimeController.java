package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnimeController {

    private static final String ARQUIVO_PRINCIPAL = "listaAnimes.txt";

    public static boolean animeExistsInMasterList(String nomeAnime) {
        return animeExists(nomeAnime, ARQUIVO_PRINCIPAL);
    }

    public static boolean animeExists(String nomeAnime, String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("nome: " + nomeAnime + ";")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
        return false;
    }

    public static String capitalizeEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split(" ");
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalizedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalizedString.toString().trim();
    }
}
