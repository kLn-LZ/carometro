package com.fatec.carometro.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("COORDENADOR")
public class Coordenador extends Usuario{
}
