package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
