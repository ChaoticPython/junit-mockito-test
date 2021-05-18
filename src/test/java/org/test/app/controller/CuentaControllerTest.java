package org.test.app.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.test.app.Datos;
import org.test.app.service.ICuentaService;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ICuentaService cuentaService;

  @Test
  void testDetalle() throws Exception {
    // Given
    Mockito.when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow(null));
    // When
    mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
    // Then  
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.nombrePersona").value("Julio"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value("1000"));
    
    Mockito.verify(cuentaService).findById(1L);
  }

}
