package org.test.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.test.app.model.Cuenta;
import org.test.app.model.Transaccion;
import org.test.app.service.ICuentaService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

  @Autowired
  private ICuentaService cuentaService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Cuenta detalle(@PathVariable(name = "id") Long id) {
    return cuentaService.findById(id);
  }

  @PostMapping("/transferir")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> transferir(@RequestBody Transaccion transaccion) {
    cuentaService.transferir(transaccion.getCuentaOrigenId(), transaccion.getCuentaDestinoId(),
        transaccion.getMonto(), transaccion.getBancoId());
    
    Map<String, Object> response = new HashMap<>();
    
    response.put("date", LocalDate.now().toString());
    response.put("status", "OK");
    response.put("mensaje", "Transferencia realizada con éxito");
    response.put("detalles", transaccion);
    
    return ResponseEntity.ok(response);
  }
  
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Cuenta> listar() {
    return cuentaService.findAll();
  }
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Cuenta guardar(@RequestBody Cuenta cuenta) {
    return cuentaService.save(cuenta);
  }
  
}
