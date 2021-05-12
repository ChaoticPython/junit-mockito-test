package org.test.app;

import org.test.app.model.Banco;
import org.test.app.model.Cuenta;

import java.math.BigDecimal;

public class Datos {
  
  public static Cuenta crearCuenta001() {
    return new Cuenta(1L, "Julio", new BigDecimal("1000"));
  }
  
  public static Cuenta crearCuenta002() {
    return new Cuenta(2L, "John", new BigDecimal("2000"));
  }

  public static Banco crearBanco() {
    return new Banco(1L, "Banco Financiero", 0);
  }
  
}
