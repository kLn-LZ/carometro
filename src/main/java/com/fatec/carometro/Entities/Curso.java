package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
}
