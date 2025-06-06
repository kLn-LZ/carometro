package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Validacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id", unique = true, nullable = false)
    @ToString.Exclude
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "coordenador_id", referencedColumnName = "id", nullable = true)
    @ToString.Exclude
    private Coordenador coordenador;

    @Column(nullable = false)
    private LocalDate dataCadastro;
    private LocalDate dataAprovacao;
    private boolean consentePublicacao;
    @Column(name = "status", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private StatusValidacao status;

    private String descricaoReprovacao;

    public Validacao(Aluno aluno, LocalDate dataCadastro) {
        this.aluno = aluno;
        this.dataCadastro = dataCadastro;
        this.status = StatusValidacao.PENDENTE;
        this.descricaoReprovacao = null;
    }
}
