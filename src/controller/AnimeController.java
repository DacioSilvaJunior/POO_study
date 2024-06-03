package controller;

import java.io.*;
import java.util.Scanner;
import entities.Anime;
import java.util.Locale;

public class AnimeController {
    private static Scanner leitor = new Scanner(System.in);
    private static final String ARQUIVO_PRINCIPAL = "listaAnimes.txt";

    private static boolean animeExistsInMasterList(String nomeAnime) {
        return animeExists(nomeAnime, ARQUIVO_PRINCIPAL);
    }

    private static boolean animeExists(String nomeAnime, String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("nome: " + nomeAnime + ";")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return false;
    }

    public static void escreverEmArquivo(Anime anime, String nomeArquivo) {
        if (animeExists(anime.getNome(), nomeArquivo)) {
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
                            .parseInt(linha.substring(linha.indexOf(">>") + 2, linha.lastIndexOf(";")));
                    telespectadores++;
                    float pontuacao = Float.parseFloat(linha.substring(linha.indexOf("=") + 1, linha.lastIndexOf(";")));
                    pontuacao = (pontuacao * (telespectadores - 1) + anime.getAvaliacao()) / telespectadores;
                    novoConteudo.append("nome: ").append(anime.getNome())
                            .append("; tipo: ").append(anime.getTipo())
                            .append("; avaliação: ").append(anime.getAvaliacao())
                            .append("; telespectadores >> ").append(telespectadores)
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
                    .append("; telespectadores >> 1")
                    .append("; pontuação = ").append(anime.getAvaliacao()).append("\n");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PRINCIPAL, false))) {
            writer.print(novoConteudo.toString());
            System.out.println("Anime atualizado no arquivo principal com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void deletarAnime(String nomeArquivo, String nomeAnime) {
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
        nome = capitalizeEachWord(nome);

        System.out.println("Informe o tipo do anime:");
        String tipo = leitor.nextLine().toLowerCase(Locale.ROOT);
        tipo = capitalizeEachWord(tipo);

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

    public static void alterarAnime(String nomeArquivo, String nomeAnime) {
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
                    novoNome = capitalizeEachWord(novoNome);

                    if (!animeExistsInMasterList(novoNome) && !novoNome.equals(nomeAnime)) {
                        System.out.println("Anime não encontrado na lista principal. Alteração não permitida.");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        continue; // Pule a edição desta linha
                    } else if (animeExists(novoNome, nomeArquivo) && !novoNome.equals(nomeAnime)) {
                        System.out.println("Nome já existe no arquivo.");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        continue; // Pule a edição desta linha
                    }

                    System.out.println("Informe o novo tipo do anime:");
                    String novoTipo = leitor.nextLine().toLowerCase(Locale.ROOT);
                    novoTipo = capitalizeEachWord(novoTipo);

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
}