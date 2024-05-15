package controller;

import java.io.*;
import entities.Anime;
import java.util.Scanner;

// Classe para escrever e ler dados de Anime em um arquivo
public class animeController {

  // Método para escrever os dados do anime em um arquivo
  public static void escreverEmArquivo(Anime anime, String nomeArquivo) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
      writer.println(
          "nome: " + anime.getNome() + "; " + "tipo: " + anime.getTipo() + "; " + "avaliaçao: " + anime.getAvaliacao());
    } catch (IOException e) {
      System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
    }
  }

  public static void registrarAnimes(Anime anime) {
    Scanner leitor = new Scanner(System.in);
    System.out.println("Informe o nome do anime:");
    String nome = leitor.nextLine();

    System.out.println("Informe o tipo do anime:");
    String tipo = leitor.nextLine();

    System.out.println("Informe a avaliação do anime (entre 0 e 10):");
    int avaliacao = leitor.nextInt();

    anime.setNome(nome);
    anime.setTipo(tipo);
    anime.setAvaliacao(avaliacao);
  }
}
