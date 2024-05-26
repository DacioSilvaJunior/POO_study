import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import entities.Anime;
import controller.animeController;
import view.animeListView;

public class Main {
  public static void main(String[] args) throws IOException {
    Anime animex = new Anime();
    animeController animeController = new animeController();
    userController userController = new userController();
    animeListView animeView = new animeListView();
    userView userView = new userView();
    Scanner leitor = new Scanner(System.in);
    char repetir;

    String arquivo = userController.wellCome();
    if (arquivo != null) {
      animeView.showMenu();
    } else {
      return;
    }

    int opcao = leitor.nextInt();
    leitor.nextLine();

    while (opcao != 6) {
      switch (opcao) {
        case 1:
          do {
            animeController.registrarAnimes(animex);
            animeController.escreverEmArquivo(animex, arquivo);
            System.out.println("Deseja registrar um novo Anime? (s/n)");
            repetir = leitor.next().charAt(0);
            leitor.nextLine();
          } while (repetir == 's');
          animeView.exibirListaAnimes(arquivo);
          break;
        case 2:
          animeController.recomendarAnimes(arquivo);
          break;
        case 3:
          do {
            System.out.println("Deseja deletar algum anime? (s/n)");
            repetir = leitor.next().charAt(0);
            leitor.nextLine();
            if (repetir == 's') {
              System.out.println("Informe o nome do anime que deseja deletar:");
              String nomeAnime = leitor.nextLine();
              animeController.deletarAnime(arquivo, nomeAnime);
            }
          } while (repetir == 's');
          animeView.exibirListaAnimes(arquivo);
          break;
        case 4:
          System.out.println("Deseja deletar o arquivo por completo? (s/n)");
          char dellArquivo = leitor.next().charAt(0);
          leitor.nextLine();
          if (dellArquivo == 's') {
            animeController.deletarArquivo(arquivo);
          }
          break;
        case 6:
          System.out.println("programa encerrado");
        default:
          System.out.println("Opção inválida.");
      }
    }
    leitor.close();
  }
}
