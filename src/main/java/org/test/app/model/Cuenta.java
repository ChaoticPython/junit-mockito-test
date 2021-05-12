package org.test.app.model;

import org.test.app.exception.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cuentas")
public class Cuenta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "nombre_persona")
  private String nombrePersona;
  
  private BigDecimal saldo;

  public Cuenta() {

  }

  public Cuenta(Long id, String nombrePersona, BigDecimal saldo) {
    super();
    this.id = id;
    this.nombrePersona = nombrePersona;
    this.saldo = saldo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombrePersona() {
    return nombrePersona;
  }

  public void setNombrePersona(String nombrePersona) {
    this.nombrePersona = nombrePersona;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombrePersona, saldo);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Cuenta cuenta = (Cuenta) obj;
    return Objects.equals(id, cuenta.id) 
        && Objects.equals(nombrePersona, cuenta.nombrePersona)
        && Objects.equals(saldo, cuenta.saldo);
  }
  
  public void debito(BigDecimal monto) {
    BigDecimal nuevoSaldo = this.saldo.subtract(monto);
    if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
      throw new DineroInsuficienteException("Dinero insuficiente en la cuenta.");
    }
    this.saldo = nuevoSaldo;
  }
  
  public void credito(BigDecimal monto) {
   this.saldo = this.saldo.add(monto); 
  }
  
}
