import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import entities.Anime;

public class userController {

  // Método para registrar um usuário no arquivo
  public void registrarUsuario(String nome, String email, String senha, boolean pagante) {
    String nomeArquivo = "users.txt"; // Nome do arquivo
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      // Escrever os dados do usuário no arquivo
      writer.write(nome + "," + email + "," + senha + "," + pagante);
      writer.newLine(); // Nova linha para o próximo usuário
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
