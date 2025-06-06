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

    public Curso(){
        super();
    }

    public Curso(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }
}
