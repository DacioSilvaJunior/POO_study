package controller;

import java.io.*;
import entities.Anime;
import java.util.Scanner;

public class animeController {

  public static void escreverEmArquivo(Anime anime, String nomeArquivo) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
      writer.println(
          "nome: " + anime.getNome() + "; " + "tipo: " + anime.getTipo() + "; " + "avaliaçao: " + anime.getAvaliacao());
    } catch (IOException e) {
      System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
    }
  }

  public static void deletarAnime(String nomeArquivo, String nomeAnime){
    try {
      File arquivo = new File(nomeArquivo);
      FileReader fr = new FileReader(arquivo);
      BufferedReader br = new BufferedReader(fr);
      String linha;
      String novoConteudo = "";
      boolean encontrado = false;

      while ((linha = br.readLine()) != null) {
        if (!linha.contains(nomeAnime)) {
          novoConteudo += linha + "\n";
        } else {
          encontrado = true;
        }
      }

      br.close();
      fr.close();

      if (encontrado) {
        FileWriter fw = new FileWriter(arquivo);
        fw.write(novoConteudo);
        fw.close();
        System.out.println("Anime deletado com sucesso!");
      } else {
        System.out.println("Anime não encontrado no arquivo.");
      }
    } catch (IOException e) {
      System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
    }
  }

  public static void deletarArquivo(String nomeArquivo){
    File arquivo = new File(nomeArquivo);
    if (arquivo.exists()) {
      if (arquivo.delete()) {
        System.out.println("Arquivo deletado com sucesso!");
      } else {
        System.out.println("Erro ao deletar o arquivo.");
      }
    } else {
      System.out.println("O arquivo não existe.");
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
