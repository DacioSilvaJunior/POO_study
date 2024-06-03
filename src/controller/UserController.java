package controller;

import java.io.*;
import java.util.Scanner;
import entities.User;
import modeu.UserModeu;

public class UserController {

  private static final String USERS_FILE = "Users.txt";
  private static final UserModeu userModeu = new UserModeu();
  private final Scanner scanner;

  public UserController() {
    this.scanner = new Scanner(System.in);
  }

  public boolean checarRegistro(String usuario, String senha) {
    try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] detalhesUsuario = linha.split(",");
        if (detalhesUsuario.length == 4) {
          String usuarioArquivo = detalhesUsuario[0].trim();
          String senhaArquivo = detalhesUsuario[1].trim();
          if (usuarioArquivo.equals(usuario) && senhaArquivo.equals(senha)) {
            return true;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public String bemVindo() {
    User usuario = new User();
    String arquivo = null;
    System.out.println("Bem-vindo ao sistema de recomendação de animes! Digite seu nome para começar:");
    String nome = scanner.nextLine();
    System.out.println("Agora digite sua senha:");
    String senha = scanner.nextLine();

    if (checarRegistro(nome, senha)) {
      arquivo = nome.equalsIgnoreCase("ADMIN") ? "listaAnimes.txt" : "animes-" + nome + ".txt";
      System.out.println("Bem-vindo " + nome + "! Aqui estão suas opções:");
    } else {
      System.out.println(nome + ", não encontramos seu registro. Deseja criar uma conta? (sim/não):");
      String resposta = scanner.nextLine();
      if (resposta.equalsIgnoreCase("sim")) {
        userModeu.registrarUsuario(usuario);
        userModeu.armazenarUsuario(usuario);
        arquivo = userModeu.novaLista(usuario);
        System.out.println("Sua conta foi criada com sucesso! Aqui estão suas opções:");
      }
    }
    return arquivo;
  }

  public void fecharLeitor() {
    if (scanner != null) {
      scanner.close();
    }
  }
}
