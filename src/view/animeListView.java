package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import entities.Anime;

public class animeListView {

    public void exibirListaAnimes(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenu(){
        System.out.println("1 - Adicionar Animes");
        System.out.println("2 - Recomendar Animes");
        System.out.println("3 - Deletar Anime");
        System.out.println("4 - Deletar lista");
        System.out.println("5 - criar nova lista");
        System.out.println("6 - fechar programa");
    }
}
