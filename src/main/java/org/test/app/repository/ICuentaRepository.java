package org.test.app.repository;

import org.test.app.model.Cuenta;

import java.util.List;

public interface ICuentaRepository {

  List<Cuenta> findAll();
  
  Cuenta findById(Long id);
  
  void update(Cuenta cuenta);
  
}
