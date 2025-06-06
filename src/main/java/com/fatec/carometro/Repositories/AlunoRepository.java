package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.StatusValidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByValidacao_Status(StatusValidacao status);
}
