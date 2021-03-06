package org.test.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.test.app.exception.DineroInsuficienteException;
import org.test.app.model.Banco;
import org.test.app.model.Cuenta;
import org.test.app.repository.IBancoRepository;
import org.test.app.repository.ICuentaRepository;
import org.test.app.service.CuentaServiceImpl;
// import org.test.app.service.ICuentaService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class JunitMockitoTestApplicationTests {

  @Mock
  ICuentaRepository cuentaRepository;

  @Mock
  IBancoRepository bancoRepository;

  @InjectMocks
  CuentaServiceImpl cuentaService;

  @BeforeEach
  void setup() {
    // cuentaRepository = Mockito.mock(ICuentaRepository.class);
    // bancoRepository = Mockito.mock(IBancoRepository.class);
    // cuentaService = new CuentaServiceImpl(cuentaRepository, bancoRepository);
  }

  @Test
  void contextLoads() {
    Mockito.when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
    Mockito.when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
    Mockito.when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

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
    Mockito.verify(cuentaRepository, times(2)).save(Mockito.any(Cuenta.class));

    Mockito.verify(bancoRepository, times(2)).findById(1L);
    Mockito.verify(bancoRepository).save(Mockito.any(Banco.class));

    Mockito.verify(cuentaRepository, times(6)).findById(Mockito.anyLong());
    Mockito.verify(cuentaRepository, never()).findAll();
  }

  @Test
  void contextLoads2() {
    Mockito.when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
    Mockito.when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
    Mockito.when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

    BigDecimal saldoOrigen = cuentaService.revisarSaldo(1L);
    BigDecimal saldoDestino = cuentaService.revisarSaldo(2L);

    assertEquals("1000", saldoOrigen.toPlainString());
    assertEquals("2000", saldoDestino.toPlainString());

    assertThrows(DineroInsuficienteException.class, () -> {
      cuentaService.transferir(1L, 2L, new BigDecimal("1200"), 1L);
    });


    saldoOrigen = cuentaService.revisarSaldo(1L);
    saldoDestino = cuentaService.revisarSaldo(2L);

    assertEquals("1000", saldoOrigen.toPlainString());
    assertEquals("2000", saldoDestino.toPlainString());

    int total = cuentaService.revisarTotalTransferencias(1L);

    assertEquals(0, total);

    Mockito.verify(cuentaRepository, times(3)).findById(1L);
    Mockito.verify(cuentaRepository, times(2)).findById(2L);
    Mockito.verify(cuentaRepository, never()).save(Mockito.any(Cuenta.class));

    Mockito.verify(bancoRepository, times(1)).findById(1L);
    Mockito.verify(bancoRepository, never()).save(Mockito.any(Banco.class));
    Mockito.verify(cuentaRepository, times(5)).findById(Mockito.anyLong());
    Mockito.verify(cuentaRepository, never()).findAll();
  }

  @Test
  void contextLoads3() {
    Mockito.when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());

    Cuenta cuenta1 = cuentaService.findById(1L);
    Cuenta cuenta2 = cuentaService.findById(1L);

    assertSame(cuenta1, cuenta2);
    assertTrue(cuenta1 == cuenta2);

    assertEquals("Julio", cuenta1.getNombrePersona());
    assertEquals("Julio", cuenta2.getNombrePersona());

    Mockito.verify(cuentaRepository, times(2)).findById(1L);
  }

  @Test
  void testFindAll() {
    // Given
    List<Cuenta> datos = Arrays.asList(
        Datos.crearCuenta001().orElseThrow(null),
        Datos.crearCuenta002().orElseThrow(null));
    Mockito.when(cuentaRepository.findAll()).thenReturn(datos);
    // When
    List<Cuenta> cuentas = cuentaService.findAll();
    // Then
    assertFalse(cuentas.isEmpty());
    assertEquals(2, cuentas.size());
    assertTrue(cuentas.contains(Datos.crearCuenta002().orElseThrow(null)));
    
    verify(cuentaRepository).findAll();
  }
  
  @Test
  void testSave() {
    // Given
    Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
    Mockito.when(cuentaRepository.save(Mockito.any())).then(invocation -> {
      Cuenta c = invocation.getArgument(0);
      c.setId(3L);
      return c;
    });
    // When
    Cuenta cuenta = cuentaService.save(cuentaPepe);
    // Then
    assertEquals("Pepe", cuenta.getNombrePersona());
    assertEquals(3, cuenta.getId());
    assertEquals("3000", cuenta.getSaldo().toPlainString());
    
    Mockito.verify(cuentaRepository).save(Mockito.any());
  }

}
