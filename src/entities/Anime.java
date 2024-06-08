package entities;

public class Anime {
  private String nome;
  private String tipo;
  private int avaliacao;
  private int telespectadores;
  private float pontuacao;

  // Construtor completo
  public Anime(String nome, String tipo, int avaliacao, int telespectadores, float pontuacao) {
    this.nome = nome;
    this.tipo = tipo;
    this.avaliacao = avaliacao;
    this.telespectadores = telespectadores;
    this.pontuacao = pontuacao;
  }

  // Construtor original
  public Anime(String nome, String tipo, int avaliacao) {
    this(nome, tipo, avaliacao, 1, avaliacao);
  }

  // Construtor vazio
  public Anime() {
    this(null, null, 0, 0, 0.0f);
  }

  // Getters e Setters
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public int getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(int avaliacao) {
    this.avaliacao = avaliacao;
  }

  public int getTelespectadores() {
    return telespectadores;
  }

  public void setTelespectadores(int telespectadores) {
    this.telespectadores = telespectadores;
  }

  public float getPontuacao() {
    return pontuacao;
  }

  public void setPontuacao(float pontuacao) {
    this.pontuacao = pontuacao;
  }

  // Sobrescrita do método toString para facilitar a impressão
  @Override
  public String toString() {
    return "nome: " + nome + "; tipo: " + tipo + "; avaliação: " + avaliacao +
        "; telespectadores >> " + telespectadores + "; pontuação = " + pontuacao;
  }
}
