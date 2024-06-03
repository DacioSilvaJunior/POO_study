package controller;

import java.io.*;
import java.util.Scanner;
import entities.User;

public class UserController {

  private Scanner sc = new Scanner(System.in);
  private static final String USERS_FILE = "Users.txt";

  public boolean checkUserRegistration(String user, String password) {
    try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] userDetails = line.split(",");
        if (userDetails.length == 4) {
          String fileUser = userDetails[0].trim();
          String filePassword = userDetails[1].trim();
          if (fileUser.equals(user) && filePassword.equals(password)) {
            return true;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public String createNewList(User user) {
    String nome = user.getNome();
    String nomeArquivo = nome.equalsIgnoreCase("ADMIN") ? "listaAnimes.txt" : "animes-" + nome + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nomeArquivo;
  }

  public void registerUser(User user) {
    System.out.println("Informe o nome do usuário:");
    user.setNome(sc.nextLine());
    System.out.println("Informe o email do usuário:");
    user.setEmail(sc.nextLine());
    System.out.println("Informe a senha do usuário:");
    user.setSenha(sc.nextLine());
    System.out.println("O usuário é pagante? (true/false)");
    user.setPagante(sc.nextBoolean());
    sc.nextLine(); // Consume the newline character
  }

  public void storeUser(User user) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
      writer
          .write(String.join(",", user.getNome(), user.getSenha(), user.getEmail(), String.valueOf(user.isPagante())));
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String welcomeUser() {
    User user = new User();
    String file;
    System.out.println("Bem-vindo ao sistema de recomendação de animes! Digite seu nome para começar:");
    String nome = sc.nextLine();
    System.out.println("Agora digite sua senha:");
    String senha = sc.nextLine();

    if (checkUserRegistration(nome, senha)) {
      file = nome.equalsIgnoreCase("ADMIN") ? "listaAnimes.txt" : "animes-" + nome + ".txt";
      System.out.println("Bem-vindo " + nome + "! Aqui estão suas opções:");
    } else {
      System.out.println(nome + ", não encontramos seu registro. Deseja criar uma conta? (sim/não):");
      String resposta = sc.nextLine();
      if (resposta.equalsIgnoreCase("sim")) {
        registerUser(user);
        storeUser(user);
        file = createNewList(user);
        System.out.println("Sua conta foi criada com sucesso! Aqui estão suas opções:");
      } else {
        file = null;
      }
    }
    return file;
  }

  public void updateUser(String nomeArquivo, String nomeUsuario) {
    try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
      String linha;
      StringBuilder novoConteudo = new StringBuilder();
      boolean encontrado = false;

      while ((linha = br.readLine()) != null) {
        if (linha.startsWith(nomeUsuario + ",")) {
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
          sc.nextLine(); // Consume the newline character

          novoConteudo.append(String.join(",", novoNome, novaSenha, novoEmail, String.valueOf(novoPagante)))
              .append("\n");
        } else {
          novoConteudo.append(linha).append("\n");
        }
      }

      if (encontrado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
          writer.write(novoConteudo.toString());
          System.out.println("Usuário atualizado com sucesso!");
        } catch (IOException e) {
          System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
      } else {
        System.out.println("Usuário não encontrado.");
      }
    } catch (IOException e) {
      System.err.println("Erro ao ler o arquivo: " + e.getMessage());
    }
  }

  public static void deleteUser(String nomeUsuario) {
    try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
      String linha;
      StringBuilder novoConteudo = new StringBuilder();
      boolean encontrado = false;

      while ((linha = br.readLine()) != null) {
        if (!linha.startsWith(nomeUsuario + ",")) {
          novoConteudo.append(linha).append("\n");
        } else {
          encontrado = true;
        }
      }

      if (encontrado) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
          writer.write(novoConteudo.toString());
        }
        System.out.println("Usuário deletado com sucesso!");

        String nomeArquivoUsuario = "animes-" + nomeUsuario + ".txt";
        File arquivoUsuario = new File(nomeArquivoUsuario);
        if (arquivoUsuario.exists() && arquivoUsuario.delete()) {
          System.out.println("Arquivo do usuário apagado com sucesso!");
        } else {
          System.out.println("Erro ao apagar arquivo do usuário.");
        }
      } else {
        System.out.println("Usuário não encontrado no arquivo.");
      }
    } catch (IOException e) {
      System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
    }
  }

  public void closeScanner() {
    if (sc != null) {
      sc.close();
    }
  }
}
