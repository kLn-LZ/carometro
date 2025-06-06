package com.fatec.carometro.Repositories;

import com.fatec.carometro.Entities.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
}
