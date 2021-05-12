package org.test.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.app.model.Cuenta;

import java.util.List;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {

  // List<Cuenta> findAll();
  //
  // Cuenta findById(Long id);
  //
  // void update(Cuenta cuenta);

}
