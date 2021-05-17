package org.test.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.test.app.model.Cuenta;
import org.test.app.repository.ICuentaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class IntegracionJpaTest {

  @Autowired
  ICuentaRepository cuentaRepository;

  @Test
  void testFindById() {
    Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

    assertTrue(cuenta.isPresent());
    assertEquals("Julio", cuenta.orElseThrow(null).getNombrePersona());
  }

  @Test
  void testFindByPersona() {
    Optional<Cuenta> cuenta = cuentaRepository.findByNombrePersona("Julio");

    assertTrue(cuenta.isPresent());
    assertEquals("Julio", cuenta.orElseThrow(null).getNombrePersona());
    assertEquals("1000.00", cuenta.orElseThrow(null).getSaldo().toPlainString());
  }

  @Test
  void testFindByPersonaThrowException() {
    Optional<Cuenta> cuenta = cuentaRepository.findByNombrePersona("Rod");

    assertThrows(NullPointerException.class, () -> {
      cuenta.orElseThrow(null);
    });
    assertFalse(cuenta.isPresent());
  }

  @Test
  void testFindAll() {
    List<Cuenta> cuentas = cuentaRepository.findAll();

    assertFalse(cuentas.isEmpty());
    assertEquals(2, cuentas.size());
  }

  @Test
  void testSaveFindByNombre() {
    // Given
    Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
    cuentaRepository.save(cuentaPepe);

    // When
    Cuenta cuenta = cuentaRepository.findByNombrePersona("Pepe").orElseThrow(null);

    // Then
    assertEquals("Pepe", cuenta.getNombrePersona());
    assertEquals("3000", cuenta.getSaldo().toPlainString());
  }

  @Test
  void testSaveFindById() {
    // Given
    Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

    // When
    Cuenta nuevaCuenta = cuentaRepository.save(cuentaPepe);
    // Cuenta cuenta = cuentaRepository.findById(save.getId()).orElseThrow(null);

    // Then
    assertEquals("Pepe", nuevaCuenta.getNombrePersona());
    assertEquals("3000", nuevaCuenta.getSaldo().toPlainString());
  }

  @Test
  void testUpdate() {
    // Given
    Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));

    // When
    Cuenta nuevaCuenta = cuentaRepository.save(cuentaPepe);
    // Cuenta cuenta = cuentaRepository.findById(save.getId()).orElseThrow(null);

    // Then
    assertEquals("Pepe", nuevaCuenta.getNombrePersona());
    assertEquals("3000", nuevaCuenta.getSaldo().toPlainString());
    
    // When
    nuevaCuenta.setSaldo(new BigDecimal("3800"));
    Cuenta cuentaActualizada = cuentaRepository.save(nuevaCuenta);
    
    // Then
    assertEquals("Pepe", cuentaActualizada.getNombrePersona());
    assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
  }
  
  @Test
  void testDelete() {
    Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow(null);
    
    assertEquals("John", cuenta.getNombrePersona());
    
    cuentaRepository.delete(cuenta);
    
    assertThrows(NullPointerException.class, () -> {
      cuentaRepository.findByNombrePersona("John").orElseThrow(null);
    });
    
    assertEquals(1, cuentaRepository.findAll().size());
  }

}
