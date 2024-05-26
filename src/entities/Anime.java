package entities;


public class Anime {
  private String nome;
  private String tipo;
  private int avaliacao;

  public Anime(String nome, String tipo, int avaliacao) {
    this.nome = nome;
    this.tipo = tipo;
    setAvaliacao(avaliacao); 
  }

  public Anime() {
    this.nome = null;
    this.tipo = null;
    this.avaliacao = 0;
  }

  public String getNome() {
    return nome;
  }

  public int getAvaliacao() {
    return avaliacao;
  }

  public String getTipo() {
    return tipo;
  }


  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setAvaliacao(int avaliacao) {
    this.avaliacao = avaliacao;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
