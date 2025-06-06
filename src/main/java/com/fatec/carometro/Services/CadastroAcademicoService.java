package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.CadastroAcademico;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Repositories.CadastroAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroAcademicoService {

    @Autowired
    private CadastroAcademicoRepository cadastroAcademicoRepository;

    public CadastroAcademico buscarPorAlunoId(Long id) {
        return cadastroAcademicoRepository.findByAluno_Id(id).orElseThrow(() -> new RuntimeException("Cadastro Acadêmico não encontrado."));
    }
}
