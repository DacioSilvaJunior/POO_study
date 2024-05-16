package entities;

public class User {
  private String nome;
  private String email;
  private String senha;
  private boolean pagante;

  public User(String nome, String email, String senha, boolean pagante) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.pagante = pagante;
  }

  public User() {
    this.nome = null;
    this.email = null;
    this.senha = null;
    this.pagante = false;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public boolean isPagante() {
    return pagante;
  }

  public void setPagante(boolean pagante) {
    this.pagante = pagante;
  }
}
