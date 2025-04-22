package com.fatec.carometro.Exceptions;

import com.fatec.carometro.DTOs.AlunoDTO;
import lombok.Getter;

@Getter
public class CadastroAlunoException extends RuntimeException {

    private final AlunoDTO alunoDTO;

    public CadastroAlunoException(AlunoDTO alunoDTO, String msg){
        super(msg);
        this.alunoDTO = alunoDTO;
    }

}
