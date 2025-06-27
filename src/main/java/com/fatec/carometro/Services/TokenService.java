package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.CadastroToken;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Repositories.CadastroTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private CadastroTokenRepository cadastroTokenRepository;

    @Transactional
    public List<String> geraTokens(int quantidade, Integer diasParaExpirar) {
        List<String> tokens = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiraEm = diasParaExpirar != null ? now.plusDays(diasParaExpirar) : null;

        for (int i = 0; i < quantidade; i++) {
            CadastroToken token = new CadastroToken();
            token.setToken(UUID.randomUUID().toString());
            token.setCriadoEm(now);
            token.setUsado(false);
            token.setExpiraEm(expiraEm);
            cadastroTokenRepository.save(token);
            tokens.add(token.getToken());
        }
        return tokens;
    }

    public List<CadastroToken> listaTokens() {
        return cadastroTokenRepository.findAll();
    }

    public CadastroToken validaEUsaToken(String token, Usuario usuario) {
        CadastroToken cadastroToken =  cadastroTokenRepository.findByToken(token).isEmpty()? null: cadastroTokenRepository.findByToken(token).get();
        if (cadastroToken.isUsado()) {
            return null;
        }
        if (cadastroToken.getExpiraEm() != null && LocalDateTime.now().isAfter(cadastroToken.getExpiraEm())) {
            return null;
        }
        cadastroToken.setUsado(true);
        cadastroToken.setUsadoPor(usuario);

        return cadastroToken;
    }

    public void registraUsoToken(CadastroToken cadastroToken) {
        if (cadastroToken == null)
            throw new RuntimeException("Token inválido");
        cadastroTokenRepository.save(cadastroToken);
    }
}
