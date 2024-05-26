import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import entities.Anime;
import entities.User;
import java.util.Scanner;

public class userController {

  public boolean checarRegistro(String filename, String user, String password) {
    try (BufferedReader br = new BufferedReader(new FileReader("Users.txt"))) {
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

  public String novaLista(User user) {
    String nome = user.getNome();
    String nomeArquivo = "animes-" + nome + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nomeArquivo;
  }

  public void registrarUsuario(User user) {
    Scanner leitor = new Scanner(System.in);
    System.out.println("Informe o nome do usuário:");
    String nome = leitor.nextLine();
    user.setNome(nome);
    System.out.println("Informe o email do usuário:");
    String email = leitor.nextLine();
    user.setEmail(email);
    System.out.println("Informe a senha do usuário:");
    String senha = leitor.nextLine();
    user.setSenha(senha);
    System.out.println("O usuário é pagante? (true/false)");
    boolean pagante = leitor.nextBoolean();
    user.setPagante(pagante);
  }

  public void armazenarUsuario(User user) {
    String nomeArquivo = "Users.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write(user.getNome() + "," + user.getSenha() + "," + user.getEmail() + "," + user.isPagante());
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String wellCome() {
    Scanner sc = new Scanner(System.in);
    User user = new User();
    String arquivo;
    System.out
        .println("Bem vindo ao sistema de recomendação de animes! \n digite seu nome para que possamos começar: ");
    String nome = sc.nextLine();
    System.out.println("agora digite sua senha: ");
    String senha = sc.nextLine();

    if (checarRegistro("Users.txt", nome, senha)) {
      arquivo = "animes-" + nome + ".txt";
      System.out.println("Bem vindo " + nome + "! \n Aqui estao as sua opçoes : ");
      sc.close();
      return arquivo;
    }

    else {
      System.out.println(nome + " não podemos acha-lo em nosso banco de dados deseja criar uma lista? : ");
      String resposta = sc.nextLine();

      if (resposta.equalsIgnoreCase("sim")) {
        registrarUsuario(user);
        armazenarUsuario(user);
        arquivo = novaLista(user);
        System.out.println("sua lista foi criada com sucesso! \nAqui estao as sua opçoes : ");
        sc.close();
        return arquivo;
      }
    }
    sc.close();
    return null;
  }

}
