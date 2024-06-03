package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserView {

    // Método para exibir os usuários a partir de um arquivo
    public void exibirUsuarios(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");

                if (partes.length == 4) {
                    String nome = partes[0].trim();
                    String senha = partes[1].trim();
                    String email = partes[2].trim();
                    boolean pagante = Boolean.parseBoolean(partes[3].trim());

                    // Exibir informações do usuário
                    System.out.println("Nome: " + nome);
                    System.out.println("Email: " + email);
                    System.out.println("Senha: " + senha);
                    System.out.println("Pagante: " + (pagante ? "Sim" : "Não"));
                    System.out.println(); // Linha em branco entre os usuários
                } else {
                    System.err.println("Erro ao processar linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
