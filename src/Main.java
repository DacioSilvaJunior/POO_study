import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import entities.Anime;
import controller.animeController;
import view.animeListView;

public class Main {
  public static void main(String[] args) throws IOException {
    Anime animex = new Anime();
    animeController Controller = new animeController();
    animeListView View = new animeListView();
    Scanner leitor = new Scanner(System.in);
    char repetir;
    
    animeListView.showMenu();
    int opcao = leitor.nextInt();
    leitor.nextLine();
    while (opcao != 6){
      switch (opcao){
        case 1:
          do {
            Controller.registrarAnimes(animex);
            Controller.escreverEmArquivo(animex, "listaAnimes.txt");
            System.out.println("Deseja registrar um novo Anime? (s/n)");
            repetir = leitor.next().charAt(0);
            leitor.nextLine();
          } while (repetir == 's');
          View.exibirListaAnimes("listaAnimes.txt");
          break;
        case 2:
          Controller.recomendarAnimes("listaAnimes.txt");
          break;
        case 3:
          do {
            System.out.println("Deseja deletar algum anime? (s/n)");
            repetir = leitor.next().charAt(0);
            leitor.nextLine();
            if (repetir == 's') {
              System.out.println("Informe o nome do anime que deseja deletar:");
              String nomeAnime = leitor.nextLine();
              Controller.deletarAnime("listaAnimes.txt", nomeAnime);
            }
          } while (repetir == 's');
          View.exibirListaAnimes("listaAnimes.txt");
          break;
        case 4:
          System.out.println("Deseja deletar o arquivo por completo? (s/n)");
          char dellArquivo = leitor.next().charAt(0);
          leitor.nextLine();
          if (dellArquivo == 's') {
            Controller.deletarArquivo("listaAnimes.txt");
          }
          break;
        case 5:
          System.out.println("Informe o nome do usuario:");
          String nomeUsuario = leitor.nextLine();
          userController.novaLista(nomeUsuario);
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
