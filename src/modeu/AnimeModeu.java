package modeu;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import entities.Anime;
import controller.AnimeController;

public class AnimeModeu {

    private static Scanner leitor = new Scanner(System.in);
    private static AnimeController animeController = new AnimeController();
    private static final String ARQUIVO_PRINCIPAL = "listaAnimes.txt";

    public static void escreverEmArquivo(Anime anime, String nomeArquivo) {
        registrarAnimes(anime);
        if (animeController.animeExists(anime.getNome(), nomeArquivo)) {
            System.out.println("Anime já existe no arquivo.");
            return;
        } else if (anime.getAvaliacao() < 0 || anime.getAvaliacao() > 10) {
            System.out.println("Avaliação inválida!");
            return;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            writer.println(
                    "nome: " + anime.getNome() + "; tipo: " + anime.getTipo() + "; avaliação: " + anime.getAvaliacao());
            System.out.println("Anime adicionado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
        escreverEmArquivoPrincipal(anime);
    }

    public static void escreverEmArquivoPrincipal(Anime anime) {
        String nomeDoAnime = anime.getNome();
        StringBuilder novoConteudo = new StringBuilder();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PRINCIPAL))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains(nomeDoAnime)) {
                    encontrado = true;
                    int telespectadores = Integer
                            .parseInt(linha.substring(linha.indexOf(">") + 1, linha.lastIndexOf(";")));
                    telespectadores++;
                    float pontuacao = Float.parseFloat(linha.substring(linha.indexOf("=") + 1, linha.lastIndexOf(";")));
                    pontuacao = (pontuacao * (telespectadores - 1) + anime.getAvaliacao()) / telespectadores;
                    novoConteudo.append("nome: ").append(anime.getNome())
                            .append("; tipo: ").append(anime.getTipo())
                            .append("; avaliação: ").append(anime.getAvaliacao())
                            .append("; telespectadores > ").append(telespectadores)
                            .append("; pontuação = ").append(pontuacao).append("\n");
                } else {
                    novoConteudo.append(linha).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        if (!encontrado) {
            novoConteudo.append("nome: ").append(anime.getNome())
                    .append("; tipo: ").append(anime.getTipo())
                    .append("; avaliação: ").append(anime.getAvaliacao())
                    .append("; telespectadores > 1")
                    .append("; pontuação = ").append(anime.getAvaliacao()).append("\n");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PRINCIPAL, true))) {
            writer.print(novoConteudo.toString());
            System.out.println("Anime atualizado no arquivo principal com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void deletarAnime(String nomeArquivo) {
        System.out.println("Informe o nome do anime que deseja alterar:");
        String nomeAnime = leitor.nextLine();
        File arquivo = new File(nomeArquivo);
        StringBuilder novoConteudo = new StringBuilder();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.contains("nome: " + nomeAnime + ";")) {
                    novoConteudo.append(linha).append("\n");
                } else {
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        if (encontrado) {
            try (FileWriter fw = new FileWriter(arquivo)) {
                fw.write(novoConteudo.toString());
                System.out.println("Anime deletado com sucesso!");
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Anime não encontrado no arquivo.");
        }
    }

    public static void deletarArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            if (arquivo.delete()) {
                System.out.println("Arquivo deletado com sucesso!");
            } else {
                System.out.println("Erro ao deletar o arquivo.");
            }
        } else {
            System.out.println("O arquivo não existe.");
        }
    }

    public static void registrarAnimes(Anime anime) {
        System.out.println("Informe o nome do anime:");
        String nome = leitor.nextLine().toLowerCase(Locale.ROOT);
        nome = animeController.capitalizeEachWord(nome);

        System.out.println("Informe o tipo do anime:");
        String tipo = leitor.nextLine().toLowerCase(Locale.ROOT);
        tipo = animeController.capitalizeEachWord(tipo);

        System.out.println("Informe a avaliação do anime (entre 0 e 10):");
        int avaliacao;
        try {
            avaliacao = leitor.nextInt();
            if (avaliacao < 0 || avaliacao > 10) {
                System.out.println("Avaliação inválida!");
                return;
            }
            leitor.nextLine(); // Consumir a nova linha
        } catch (Exception e) {
            System.out.println("Por favor, insira um número válido.");
            leitor.nextLine(); // Limpar o buffer do scanner
            return;
        }

        anime.setNome(nome);
        anime.setTipo(tipo);
        anime.setAvaliacao(avaliacao);
    }

    public static void alterarAnime(String nomeArquivo) {
        System.out.println("Informe o nome do anime que deseja alterar:");
        String nomeAnime = leitor.nextLine();
        File arquivo = new File(nomeArquivo);
        StringBuilder novoConteudo = new StringBuilder();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("nome: " + nomeAnime + ";")) {
                    encontrado = true;

                    System.out.println("Anime encontrado. Informe os novos dados.");

                    System.out.println("Informe o novo nome do anime:");
                    String novoNome = leitor.nextLine().toLowerCase(Locale.ROOT);
                    novoNome = animeController.capitalizeEachWord(novoNome);

                    if (!animeController.animeExistsInMasterList(novoNome) && !novoNome.equals(nomeAnime)) {
                        System.out.println("Anime não encontrado na lista principal. Alteração não permitida.");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        continue; // Pule a edição desta linha
                    } else if (animeController.animeExists(novoNome, nomeArquivo) && !novoNome.equals(nomeAnime)) {
                        System.out.println("Nome já existe no arquivo.");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        continue; // Pule a edição desta linha
                    }

                    System.out.println("Informe o novo tipo do anime:");
                    String novoTipo = leitor.nextLine().toLowerCase(Locale.ROOT);
                    novoTipo = animeController.capitalizeEachWord(novoTipo);

                    System.out.println("Informe a nova avaliação do anime (entre 0 e 10):");
                    int novaAvaliacao = leitor.nextInt();
                    if (novaAvaliacao < 0 || novaAvaliacao > 10) {
                        System.out.println("Avaliação inválida!");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        continue; // Pule a edição desta linha
                    }
                    leitor.nextLine(); // Consumir a nova linha

                    novoConteudo.append("nome: ").append(novoNome)
                            .append("; tipo: ").append(novoTipo)
                            .append("; avaliação: ").append(novaAvaliacao).append("\n");
                } else {
                    novoConteudo.append(linha).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
        }

        if (encontrado) {
            try (FileWriter fw = new FileWriter(arquivo)) {
                fw.write(novoConteudo.toString());
                System.out.println("Anime alterado com sucesso!");
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Anime não encontrado no arquivo.");
        }
    }

    public static void rankDeAnimes() {
        List<Anime> listaAnimes = new ArrayList<Anime>();

        // Ler o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PRINCIPAL))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                String nome = partes[0].split(":")[1].trim();
                String tipo = partes[1].split(":")[1].trim();
                int avaliacao = Integer.parseInt(partes[2].split(":")[1].trim());
                int telespectadores = Integer.parseInt(partes[3].split(">>")[1].trim());
                float pontuacao = Float.parseFloat(partes[4].split("=")[1].trim());

                Anime anime = new Anime(nome, tipo, avaliacao, telespectadores, pontuacao);
                listaAnimes.add(anime);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Organizar a lista de acordo com a pontuação
        Collections.sort(listaAnimes, new Comparator<Anime>() {
            public int compare(Anime a1, Anime a2) {
                // Comparar em ordem decrescente de pontuação
                return Float.compare(a2.getPontuacao(), a1.getPontuacao());
            }
        });

        // Imprimir a lista organizada
        for (Anime anime : listaAnimes) {
            System.out.println(anime);
        }
    }
}
