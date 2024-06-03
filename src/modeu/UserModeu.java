package modeu;

import java.io.*;
import java.util.Scanner;
import entities.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class UserModeu {

  private static final String USERS_FILE = "Users.txt";
  private Scanner sc;

  public UserModeu() {
    sc = new Scanner(System.in);
  }

  public void fecharLeitor() {
    if (sc != null) {
      sc.close();
    }
  }

  public String novaLista(User usuario) {
    String nome = usuario.getNome();
    String nomeArquivo = nome.equalsIgnoreCase("ADMIN") ? "listaAnimes.txt" : "animes-" + nome + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write(""); // Creates the file if it doesn't exist
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nomeArquivo;
  }

  public void registrarUsuario(User usuario) {
    System.out.println("Informe o nome do usuário:");
    usuario.setNome(sc.nextLine());
    System.out.println("Informe o email do usuário:");
    usuario.setEmail(sc.nextLine());
    System.out.println("Informe a senha do usuário:");
    usuario.setSenha(sc.nextLine());
    System.out.println("O usuário é pagante? (true/false)");
    usuario.setPagante(sc.nextBoolean());
    sc.nextLine(); // Consumir a nova linha
    fecharLeitor();
  }

  public void armazenarUsuario(User usuario) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
      writer.write(String.join(",", usuario.getNome(), usuario.getSenha(), usuario.getEmail(),
          String.valueOf(usuario.isPagante())));
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void alterarUsuario(String nomeDoArquivo) {

    String nomeDoUsuario = nomeDoArquivo.substring(nomeDoArquivo.lastIndexOf("-") + 1, nomeDoArquivo.indexOf("."));
    StringBuilder novoConteudo = new StringBuilder();
    boolean encontrado = false;

    try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
      String linha;

      while ((linha = br.readLine()) != null) {
        String[] partes = linha.split(",");
        if (partes[0].equals(nomeDoUsuario)) {
          encontrado = true;
          System.out.println("Usuário encontrado. Informe os novos dados.");

          System.out.println("Informe o novo nome do usuário:");
          String novoNome = sc.nextLine();

          System.out.println("Informe o novo email do usuário:");
          String novoEmail = sc.nextLine();

          System.out.println("Informe a nova senha do usuário:");
          String novaSenha = sc.nextLine();

          System.out.println("O usuário é pagante? (true/false)");
          boolean novoPagante = sc.nextBoolean();
          sc.nextLine(); // Consumir a nova linha

          novoConteudo.append(String.join(",", novoNome, novaSenha, novoEmail, String.valueOf(novoPagante)))
              .append("\n");
          String novoNomeArquivo = "animes-" + novoNome + ".txt";
          atualisarArquivo("animes-" + nomeDoUsuario + ".txt", novoNomeArquivo);
        } else {
          novoConteudo.append(linha).append("\n");
        }
      }
    } catch (IOException e) {
      System.err.println("Erro ao ler o arquivo: " + e.getMessage());
      return;
    }

    if (encontrado) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
        writer.write(novoConteudo.toString());
        System.out.println("Usuário atualizado com sucesso!");
      } catch (IOException e) {
        System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
      }
    } else {
      System.out.println("Usuário não encontrado.");
    }
  }

  public void atualisarArquivo(String nomeAtualDoArquivo, String novoNomeDoArquivo) {
    Path sourcePath = Paths.get(nomeAtualDoArquivo);
    Path destinationPath = Paths.get(novoNomeDoArquivo);

    try {
      Files.copy(sourcePath, destinationPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
      System.out.println("Arquivo copiado com sucesso!");

      // Deletar o arquivo fonte após a cópia
      Files.delete(sourcePath);
      System.out.println("Arquivo fonte deletado com sucesso!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void apagarUsuario(String nomeDoUsuario) {
    StringBuilder novoConteudo = new StringBuilder();
    boolean encontrado = false;

    try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
      String linha;

      while ((linha = br.readLine()) != null) {
        String[] partes = linha.split(",");
        if (partes[0].equals(nomeDoUsuario)) {
          encontrado = true;
        } else {
          novoConteudo.append(linha).append("\n");
        }
      }
    } catch (IOException e) {
      System.err.println("Erro ao ler o arquivo: " + e.getMessage());
      return;
    }

    if (encontrado) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
        writer.write(novoConteudo.toString());
        System.out.println("Usuário deletado com sucesso!");
      } catch (IOException e) {
        System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
      }

      String nomeDoArquivo = "animes-" + nomeDoUsuario + ".txt";
      File arquivoUsuario = new File(nomeDoArquivo);
      if (arquivoUsuario.exists() && arquivoUsuario.delete()) {
        System.out.println("Arquivo do usuário apagado com sucesso!");
      } else {
        System.out.println("Erro ao apagar arquivo do usuário.");
      }
    } else {
      System.out.println("Usuário não encontrado no arquivo.");
    }
  }
}
