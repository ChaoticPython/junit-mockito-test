package org.test.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.test.app.model.Cuenta;

//import java.util.List;
import java.util.Optional;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {

  // List<Cuenta> findAll();
  //
  // Cuenta findById(Long id);
  //
  // void update(Cuenta cuenta);

  @Query("select c from Cuenta c where c.nombrePersona = ?1")
  Optional<Cuenta> findByNombrePersona(String nombrePersona);
  
}
