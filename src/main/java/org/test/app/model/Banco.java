package org.test.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bancos")
public class Banco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "nombre_banco")
  private String nombreBanco;
  
  @Column(name = "total_transferencias")
  private int totalTransferencias;

  public Banco() {
    
  }

  public Banco(Long id, String nombreBanco, int totaltransferencias) {
    super();
    this.id = id;
    this.nombreBanco = nombreBanco;
    this.totalTransferencias = totaltransferencias;
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
    return totalTransferencias;
  }

  public void setTotaltransferencias(int totaltransferencias) {
    this.totalTransferencias = totaltransferencias;
  }
  
}
