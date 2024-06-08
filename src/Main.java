import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import controller.AnimeController;
import controller.UserController;
import entities.Anime;
import view.AnimeListView;
import modeu.AnimeModeu;
import modeu.UserModeu;

public class Main {

    public static void main(String[] args) {
        try {
            Anime anime = new Anime();
            UserModeu userModeu = new UserModeu();
            AnimeModeu animeModeu = new AnimeModeu();
            AnimeController animeController = new AnimeController();
            AnimeListView view = new AnimeListView();
            UserController userController = new UserController();
            String arquivo = userController.bemVindo(); // Nome do arquivo específico do usuário

            Scanner leitor = new Scanner(System.in);

            while (true) {
                exibirOpcoes();
                int opcao = lerOpcao(leitor);

                switch (opcao) {
                    case 1:
                        animeModeu.escreverEmArquivo(anime, arquivo);
                        break;
                    case 2:
                        animeModeu.deletarAnime(arquivo);
                        break;
                    case 3:
                        animeModeu.alterarAnime(arquivo);
                        break;
                    case 4:
                        view.exibirListaAnimes(arquivo);
                        break;
                    case 5:
                        userModeu.alterarUsuario(arquivo);
                        break;
                    case 6:
                        animeModeu.rankDeAnimes();
                        break;
                    case 8:
                        userModeu.apagarUsuario(arquivo);
                        break;
                    case 9:
                        animeModeu.deletarArquivo(arquivo);
                        break;
                    case 0:
                        System.out.println("Saindo do programa...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }

    public static void exibirOpcoes() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Registrar novo anime");
        System.out.println("2. Deletar anime");
        System.out.println("3. Alterar anime");
        System.out.println("4. Exibir lista de animes");
        System.out.println("5. Alterar dados de usuário");
        System.out.println("6. Exibir rank de animes");
        System.out.println("8. Apagar usuário");
        System.out.println("9. Deletar o arquivo");
        System.out.println("0. Sair");
    }

    public static int lerOpcao(Scanner leitor) {
        System.out.print("Digite sua opção: ");
        try {
            while (!leitor.hasNextInt()) {
                System.out.println("Por favor, insira um número válido.");
                leitor.next(); // Descarta a entrada inválida
            }
            int opcao = leitor.nextInt();
            leitor.nextLine(); // Limpa o buffer após nextInt()
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
        return 0;
    }
}
