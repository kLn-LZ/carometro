package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O ano é obrigatório")
    private Integer ano;

    @Lob
    private byte[] foto;
    private String linkedIn;
    private String gitHub;
    private String lattes;

    @NotBlank(message = "O histórico é obrigatório")
    private String historico;

    @NotBlank(message = "As comentarios são obrigatórias")
    private String comentarios;
    private boolean consentePublicacao;

    @Column(name = "validado", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private Validacao validado;

    private String descricaoReprovacao; 
}
