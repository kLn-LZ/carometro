package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String curso;
    private String ano;
    @Lob
    private byte[] foto;
    private String linkedIn;
    private String gitHub;
    private String lattes;
    private String historico;
    private String conquistas;
    private boolean consentePublicacao;
}
