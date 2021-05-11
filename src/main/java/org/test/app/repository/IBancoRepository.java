package org.test.app.repository;

import org.test.app.model.Banco;

import java.util.List;

public interface IBancoRepository {

  List<Banco> findAll();
  
  Banco findById(Long id);
  
  void update(Banco banco);
  
}
