import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import entities.User;

public class userView {

    // Método para exibir os usuários a partir de um arquivo
    public void exibirUsuarios(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\n"); 
                String nome = partes[0];
                String email = partes[1];
                String senha = partes[2];
                boolean pagante = Boolean.parseBoolean(partes[3]);

                // Exibir informações do usuário
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                System.out.println("Senha: " + senha);
                System.out.println("Pagante: " + (pagante ? "Sim" : "Não"));
                System.out.println(); // Linha em branco entre os usuários
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
