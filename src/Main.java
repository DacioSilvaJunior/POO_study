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
    do {
      Controller.registrarAnimes(animex);
      Controller.escreverEmArquivo(animex, "listaAnimes.txt");
      System.out.println("Deseja registrar um novo Anime? (s/n)");
      repetir = leitor.next().charAt(0);
      leitor.nextLine();
    } while (repetir == 's');
    leitor.close();
    View.exibirListaAnimes("listaAnimes.txt");
    do{
      System.out.println("Deseja deletar algum anime? (s/n)");
      repetir = leitor.next().charAt(0);
      leitor.nextLine();
      if(repetir == 's'){
        System.out.println("Informe o nome do anime que deseja deletar:");
        String nomeAnime = leitor.nextLine();
        Controller.deletarAnime("listaAnimes.txt", nomeAnime);
      }
    }while(repetir == 's');
    leitor.close();

    system.out.println("Deseja deletar o arquivo por completo? (ATENCAO: ISSO DELETARA TODOS OS DADOS DO ARQUIVO) (s/n)");
    dellArquivo = leitor.next().charAt(0);
    leitor.nextLine();
    if(dellArquivo == 's'){
    Controller.deletarArquivo("listaAnimes.txt");
    }
}
