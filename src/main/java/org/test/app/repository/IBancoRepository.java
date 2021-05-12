package org.test.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.app.model.Banco;

//import java.util.List;

public interface IBancoRepository extends JpaRepository<Banco, Long> {

  // List<Banco> findAll();
  //
  // Banco findById(Long id);
  //
  // void update(Banco banco);

}
