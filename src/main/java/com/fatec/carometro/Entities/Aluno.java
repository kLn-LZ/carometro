package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O curso é obrigatório")
    private String curso;

    @NotBlank(message = "O ano é obrigatório")
    private String ano;

    @Lob
    private byte[] foto;
    private String linkedIn;
    private String gitHub;
    private String lattes;

    @NotBlank(message = "O histórico é obrigatório")
    private String historico;

    @NotBlank(message = "As conquistas são obrigatórias")
    private String conquistas;
    private boolean consentePublicacao;
    private boolean validado;
}
