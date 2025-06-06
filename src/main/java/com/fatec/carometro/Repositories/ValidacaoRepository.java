package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.CadastroAcademico;
import com.fatec.carometro.Entities.Validacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidacaoRepository extends JpaRepository<Validacao, Long> {
    Optional<Validacao> findByAluno_Id(Long id);

}
