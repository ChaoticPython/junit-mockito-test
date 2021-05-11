package org.test.app.model;

public class Banco {

  private Long id;
  
  private String nombreBanco;
  
  private int totaltransferencias;

  public Banco() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Banco(Long id, String nombreBanco, int totaltransferencias) {
    super();
    this.id = id;
    this.nombreBanco = nombreBanco;
    this.totaltransferencias = totaltransferencias;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreBanco() {
    return nombreBanco;
  }

  public void setNombreBanco(String nombreBanco) {
    this.nombreBanco = nombreBanco;
  }

  public int getTotaltransferencias() {
    return totaltransferencias;
  }

  public void setTotaltransferencias(int totaltransferencias) {
    this.totaltransferencias = totaltransferencias;
  }
  
}
