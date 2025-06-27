package com.fatec.carometro.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cadastro_tokens")
public class CadastroToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private boolean usado;

    @OneToOne
    @JoinColumn(name = "used_by")
    private Usuario usadoPor;

    private LocalDateTime expiraEm;
}
