package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CadastroAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O ano é obrigatório")
    private Integer ano;

    @Lob
    private byte[] foto;
    private String linkedIn;
    private String gitHub;
    private String lattes;
    private String historico;
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id", unique = true, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Aluno aluno;
}
