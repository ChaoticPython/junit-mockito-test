package org.test.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.test.app.model.Banco;
import org.test.app.model.Cuenta;
import org.test.app.repository.IBancoRepository;
import org.test.app.repository.ICuentaRepository;
import org.test.app.service.CuentaServiceImpl;
import org.test.app.service.ICuentaService;

import java.math.BigDecimal;

@SpringBootTest
class JunitMockitoTestApplicationTests {


  ICuentaRepository cuentaRepository;
  IBancoRepository bancoRepository;
  
  ICuentaService cuentaService;
  
  @BeforeEach
  void setup() {
    cuentaRepository = Mockito.mock(ICuentaRepository.class);
    bancoRepository = Mockito.mock(IBancoRepository.class);
    cuentaService = new CuentaServiceImpl(cuentaRepository, bancoRepository);
  }
  
  @Test
  void contextLoads() {
    Mockito.when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
    Mockito.when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
    Mockito.when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
    
    BigDecimal saldoOrigen = cuentaService.revisarSaldo(1L);
    BigDecimal saldoDestino = cuentaService.revisarSaldo(2L);
    
    assertEquals("1000", saldoOrigen.toPlainString());
    assertEquals("2000", saldoDestino.toPlainString());
    
    cuentaService.transferir(1L, 2L, new BigDecimal("100"), 1L);
    
    saldoOrigen = cuentaService.revisarSaldo(1L);
    saldoDestino = cuentaService.revisarSaldo(2L);
    
    assertEquals("900", saldoOrigen.toPlainString());
    assertEquals("2100", saldoDestino.toPlainString());
    
    int total = cuentaService.revisarTotalTransferencias(1L);
    
    assertEquals(1, total);
    
    Mockito.verify(cuentaRepository, times(3)).findById(1L);
    Mockito.verify(cuentaRepository, times(3)).findById(2L);
    Mockito.verify(cuentaRepository, times(2)).update(Mockito.any(Cuenta.class));
    
    Mockito.verify(bancoRepository, times(2)).findById(1L);
    Mockito.verify(bancoRepository).update(Mockito.any(Banco.class));
  }

}
