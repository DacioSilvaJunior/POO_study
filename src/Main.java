import java.io.IOException;
import java.util.Scanner;
import controller.AnimeController;
import controller.UserController;
import entities.Anime;
import view.AnimeListView;

public class Main {

    public static void main(String[] args) throws IOException {
        Anime anime = new Anime();
        AnimeController animeController = new AnimeController();
        AnimeListView view = new AnimeListView();
        UserController userController = new UserController();
        String arquivo = userController.wellCome(); // Nome do arquivo específico do usuário

        Scanner leitor = new Scanner(System.in);

        while (true) {
            exibirOpcoes();
            int opcao = lerOpcao(leitor);

            switch (opcao) {
                case 1:
                    registrarAnime(animeController, anime, arquivo);
                    break;
                case 2:
                    deletarAnime(animeController, view, leitor, arquivo);
                    break;
                case 3:
                    alterarAnime(animeController, view, leitor, arquivo);
                    break;
                case 4:
                    view.exibirListaAnimes(arquivo);
                    break;
                case 8:
                    apagarUsuario(userController, leitor, arquivo);
                    break;
                case 9:
                    deletarArquivo(animeController, leitor, arquivo);
                    break;
                case 0:
                    sair(leitor, animeController);
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void exibirOpcoes() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Registrar novo anime");
        System.out.println("2. Deletar anime");
        System.out.println("3. Alterar anime");
        System.out.println("4. Exibir lista de animes");
        System.out.println("8. Apagar usuário");
        System.out.println("9. Deletar o arquivo");
        System.out.println("0. Sair");
    }

    public static int lerOpcao(Scanner leitor) {
        System.out.print("Digite sua opção: ");
        while (!leitor.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            leitor.next(); // Descarta a entrada inválida
        }
        return leitor.nextInt();
    }

    public static void registrarAnime(AnimeController animeController, Anime anime, String arquivo) {
        animeController.registrarAnimes(anime);
        animeController.escreverEmArquivo(anime, arquivo);
    }

    public static void deletarAnime(AnimeController animeController, AnimeListView view, Scanner leitor,
            String arquivo) {
        view.exibirListaAnimes(arquivo);
        System.out.println("Informe o nome do anime que deseja deletar:");
        String nomeAnime = leitor.nextLine();
        animeController.deletarAnime(arquivo, nomeAnime);
    }

    public static void alterarAnime(AnimeController animeController, AnimeListView view, Scanner leitor,
            String arquivo) {
        view.exibirListaAnimes(arquivo);
        System.out.println("Informe o nome do anime que deseja alterar:");
        String nomeParaAlterar = leitor.nextLine();
        animeController.alterarAnime(arquivo, nomeParaAlterar);
    }

    public static void apagarUsuario(UserController userController, Scanner leitor, String arquivo) {
        System.out.println("Deseja apagar seu usuário? Digite \"CONFIRMAR\" para seguir: ");
        if (leitor.nextLine().equals("CONFIRMAR")) {
            String nomeUsuario = arquivo.substring(7, arquivo.indexOf(".txt")); // Extrair o nome do usuário do arquivo
            userController.apagarUsuario(nomeUsuario);
            System.out.println("Usuário apagado com sucesso!");
            arquivo = userController.wellCome(); // Atualizar o nome do arquivo do usuário
        } else {
            System.out.println("Operação cancelada!\n");
        }
    }

    public static void deletarArquivo(AnimeController animeController, Scanner leitor, String arquivo) {
        System.out.println("Deseja deletar o arquivo por completo? Digite \"CONFIRMAR\" para seguir: ");
        if (leitor.nextLine().equals("CONFIRMAR")) {
            animeController.deletarArquivo(arquivo);
        } else {
            System.out.println("Operação cancelada!\n");
        }
    }

    public static void sair(Scanner leitor, AnimeController animeController) {
        System.out.println("Saindo...");
        leitor.close();
        animeController.fecharLeitor();
    }
}
