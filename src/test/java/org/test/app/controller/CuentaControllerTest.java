package org.test.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.test.app.Datos;
import org.test.app.model.Cuenta;
import org.test.app.model.Transaccion;
import org.test.app.service.ICuentaService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ICuentaService cuentaService;

  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void testDetalle() throws Exception {
    // Given
    Mockito.when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow(null));
    // When
    mvc.perform(get("/api/cuentas/1").contentType(APPLICATION_JSON))
        // Then
        .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.nombrePersona").value("Julio"))
        .andExpect(jsonPath("$.saldo").value("1000"));

    Mockito.verify(cuentaService).findById(1L);
  }

  @Test
  void testTransferir() throws JsonProcessingException, Exception {
    // Given
    Transaccion transaccion = new Transaccion();
    transaccion.setCuentaOrigenId(1L);
    transaccion.setCuentaDestinoId(2L);
    transaccion.setMonto(new BigDecimal("100"));
    transaccion.setBancoId(1L);
    // When
    mvc.perform(post("/api/cuentas/transferir").contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(transaccion)))
        // Then
        .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con éxito"))
        .andExpect(jsonPath("$.detalles.cuentaOrigenId").value(1L));
  }

  @Test
  void testListar() throws JsonProcessingException, Exception {
    // Given
    List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(null),
        Datos.crearCuenta002().orElseThrow(null));


    Mockito.when(cuentaService.findAll()).thenReturn(cuentas);

    // When
    mvc.perform(get("/api/cuentas").contentType(APPLICATION_JSON))

        // Then
        .andExpect(status().isOk()).andExpect(jsonPath("$.[0].nombrePersona").value("Julio"))
        .andExpect(jsonPath("$.[1].nombrePersona").value("John"))
        .andExpect(jsonPath("$.[0].saldo").value("1000"))
        .andExpect(jsonPath("$.[1].saldo").value("2000"))
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(content().json(objectMapper.writeValueAsString(cuentas)));
  }

  @Test
  void testGuardar() throws JsonProcessingException, Exception {
    // Given
    Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));
    Mockito.when(cuentaService.save(Mockito.any())).then(invocation -> {
      Cuenta c = invocation.getArgument(0);
      c.setId(3L);
      return c;
    });
    // When
    mvc.perform(post("/api/cuentas").contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cuenta)))

        // Then
        .andExpect(status().isCreated()).andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(3))).andExpect(jsonPath("$.nombrePersona", is("Pepe")))
        .andExpect(jsonPath("$.saldo", is(3000)));
    verify(cuentaService).save(Mockito.any());
  }

}
