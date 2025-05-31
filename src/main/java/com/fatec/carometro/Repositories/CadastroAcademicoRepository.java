package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.CadastroAcademico;
import com.fatec.carometro.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadastroAcademicoRepository extends JpaRepository <CadastroAcademico, Long> {
    Optional<CadastroAcademico> findByAluno_Id(Long id);
}
