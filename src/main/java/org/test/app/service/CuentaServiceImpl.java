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
    return cuentaRepository.findById(id).orElseThrow(null);
  }

  @Override
  public int revisarTotalTransferencias(Long bancoId) {
    Banco banco = bancoRepository.findById(bancoId).orElseThrow(null);
    return banco.getTotaltransferencias();
  }

  @Override
  public BigDecimal revisarSaldo(Long cuentaId) {
    Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(null);
    return cuenta.getSaldo();
  }

  @Override
  public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino,
      BigDecimal monto, Long bancoId) {
    Cuenta cuentaOrigen = cuentaRepository.findById(numeroCuentaOrigen).orElseThrow(null);
    cuentaOrigen.debito(monto);
    cuentaRepository.save(cuentaOrigen);
    
    Cuenta cuentaDestino = cuentaRepository.findById(numeroCuentaDestino).orElseThrow(null);
    cuentaDestino.credito(monto);
    cuentaRepository.save(cuentaDestino);
    
    Banco banco = bancoRepository.findById(bancoId).orElseThrow(null);
    int totalTransferencias = banco.getTotaltransferencias();
    banco.setTotaltransferencias(++totalTransferencias);
    bancoRepository.save(banco);
  }

}
