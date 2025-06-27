package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.CadastroToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroTokenRepository extends JpaRepository<CadastroToken, Long> {
    Optional<CadastroToken> findByToken(String token);
}
