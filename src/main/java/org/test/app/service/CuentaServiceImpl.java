package org.test.app.service;

import org.test.app.model.Banco;
import org.test.app.model.Cuenta;
import org.test.app.repository.IBancoRepository;
import org.test.app.repository.ICuentaRepository;

import java.math.BigDecimal;

public class CuentaServiceImpl implements ICuentaService {

  private ICuentaRepository cuentaRepository;
  
  private IBancoRepository bancoRepository;

  public CuentaServiceImpl(ICuentaRepository cuentaRespository, IBancoRepository bancoRepository) {
    this.cuentaRepository = cuentaRespository;
    this.bancoRepository = bancoRepository;
  }
  
  @Override
  public Cuenta findById(Long id) {
    return cuentaRepository.findById(id);
  }

  @Override
  public int revisarTotalTransferencias(Long bancoId) {
    Banco banco = bancoRepository.findById(bancoId);
    return banco.getTotaltransferencias();
  }

  @Override
  public BigDecimal revisarSaldo(Long cuentaId) {
    Cuenta cuenta = cuentaRepository.findById(cuentaId);
    return cuenta.getSaldo();
  }

  @Override
  public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino,
      BigDecimal monto, Long bancoId) {
    Cuenta cuentaOrigen = cuentaRepository.findById(numeroCuentaOrigen);
    cuentaOrigen.debito(monto);
    cuentaRepository.update(cuentaOrigen);
    
    Cuenta cuentaDestino = cuentaRepository.findById(numeroCuentaDestino);
    cuentaDestino.credito(monto);
    cuentaRepository.update(cuentaDestino);
    
    Banco banco = bancoRepository.findById(bancoId);
    int totalTransferencias = banco.getTotaltransferencias();
    banco.setTotaltransferencias(++totalTransferencias);
    bancoRepository.update(banco);
  }

}
