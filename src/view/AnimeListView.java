package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnimeListView {

    public void exibirListaAnimes(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public void showMenu() {
        System.out.println("1 - Adicionar Animes");
        System.out.println("2 - Recomendar Animes");
        System.out.println("3 - Deletar Anime");
        System.out.println("4 - Deletar Lista");
        System.out.println("5 - Alterar Anime");
        System.out.println("6 - Fechar Programa");
        System.out.println("Escolha uma opção:");
    }
}
