package org.test.app.service;

import org.test.app.model.Cuenta;

import java.math.BigDecimal;

public interface ICuentaService {

  Cuenta findById(Long id);
  
  int revisarTotalTransferencias(Long bancoId);
  
  BigDecimal revisarSaldo(Long cuentaId);
  
  void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId);
  
}
