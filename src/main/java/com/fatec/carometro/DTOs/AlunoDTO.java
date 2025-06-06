package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.*;
import com.fatec.carometro.Services.CadastroAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public record AlunoDTO(
        Long id,
        String nome,
        String email,
        Long cursoId,
        String cursoNome,
        Integer ano,
        String linkedIn,
        String gitHub,
        String lattes,
        String historico,
        String comentarios,
        Boolean consentePublicacao,
        MultipartFile foto,
        String fotoBase64,
        StatusValidacao status,
        String descricaoReprovacao
)  {
}
