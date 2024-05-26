import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import entities.Anime;

public class userController {


  public void registrarUsuario(String nome, String email, String senha, boolean pagante) {
    String nomeArquivo = "users.txt"; 
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write(nome + "," + email + "," + senha + "," + pagante);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
//funçao que cria um arquivo txt com o nome do usuario
  public void novaLista(String nome){
    String nomeArquivo ="animes-" + nome + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
      writer.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }  
  
}
