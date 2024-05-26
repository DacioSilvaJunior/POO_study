package controller;

import java.io.*;
import java.util.Scanner;
import entities.Anime;

public class animeController {
    private static Scanner leitor = new Scanner(System.in);

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
                "nome: " + anime.getNome() + "; " + "tipo: " + anime.getTipo() + "; " + "avaliação: " + anime.getAvaliacao());
            System.out.println("Anime adicionado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void deletarAnime(String nomeArquivo, String nomeAnime) {
        try {
            File arquivo = new File(nomeArquivo);
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            StringBuilder novoConteudo = new StringBuilder();
            boolean encontrado = false;

            while ((linha = br.readLine()) != null) {
                if (!linha.contains("nome: " + nomeAnime + ";")) {
                    novoConteudo.append(linha).append("\n");
                } else {
                    encontrado = true;
                }
            }

            br.close();
            fr.close();

            if (encontrado) {
                FileWriter fw = new FileWriter(arquivo);
                fw.write(novoConteudo.toString());
                fw.close();
                System.out.println("Anime deletado com sucesso!");
            } else {
                System.out.println("Anime não encontrado no arquivo.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
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
        String nome = leitor.nextLine();

        System.out.println("Informe o tipo do anime:");
        String tipo = leitor.nextLine();

        System.out.println("Informe a avaliação do anime (entre 0 e 10):");
        int avaliacao = leitor.nextInt();
        leitor.nextLine(); // Consumir a nova linha

        anime.setNome(nome);
        anime.setTipo(tipo);
        anime.setAvaliacao(avaliacao);
    } 

    public static void alterarAnime(String nomeArquivo, String nomeAnime) {
        BufferedReader br = null;
        try {
            File arquivo = new File(nomeArquivo);
            FileReader fr = new FileReader(arquivo);
            br = new BufferedReader(fr);
            String linha;
            StringBuilder novoConteudo = new StringBuilder();
            boolean encontrado = false;

            while ((linha = br.readLine()) != null) {
                if (linha.contains("nome: " + nomeAnime + ";")) {
                    encontrado = true;

                    System.out.println("Anime encontrado. Informe os novos dados.");

                    System.out.println("Informe o novo nome do anime:");
                    String novoNome = leitor.nextLine();
                    if (animeExists(novoNome, nomeArquivo) && !novoNome.equals(nomeAnime)) {
                        System.out.println("Nome já existe no arquivo.");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        return; // Pule a edição desta linha
                    }

                    System.out.println("Informe o novo tipo do anime:");
                    String novoTipo = leitor.nextLine();

                    System.out.println("Informe a nova avaliação do anime (entre 0 e 10):");
                    int novaAvaliacao = leitor.nextInt();
                    if (novaAvaliacao < 0 || novaAvaliacao > 10) {
                        System.out.println("Avaliação inválida!");
                        novoConteudo.append(linha).append("\n"); // Mantenha a linha original
                        return; // Pule a edição desta linha
                    }
                    leitor.nextLine(); // Consumir a nova linha

                    novoConteudo.append("nome: ").append(novoNome)
                                .append("; tipo: ").append(novoTipo)
                                .append("; avaliação: ").append(novaAvaliacao).append("\n");
                } else {
                    novoConteudo.append(linha).append("\n");
                }
            }

            if (encontrado) {
                FileWriter fw = new FileWriter(arquivo);
                fw.write(novoConteudo.toString());
                fw.close();
                System.out.println("Anime alterado com sucesso!");
            } else {
                System.out.println("Anime não encontrado no arquivo.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
        } finally {
            // Fecha o BufferedReader no bloco finally
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao fechar o BufferedReader: " + e.getMessage());
            }
        }
    }    

    public static void recomendarAnimes(String nomeArquivo) {
        try {
            File arquivo = new File(nomeArquivo);
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            int encontrado = 0;

            while ((linha = br.readLine()) != null) {
                if (linha.contains("nome: ")) {
                    String nomeAnime = linha.substring(6, linha.indexOf(";"));
                    System.out.println("Recomendação: " + nomeAnime);
                    encontrado += 1;
                }
            }

            br.close();
            fr.close();

            if (encontrado == 0) {
                System.out.println("Nenhum anime encontrado no arquivo.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void fecharLeitor() {
        if (leitor != null) {
            leitor.close();
            //System.out.println("Scanner fechado.");
        }
    }
}