package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Exceptions.GlobalExceptionHandler;
import com.fatec.carometro.Exceptions.LoginException;
import com.fatec.carometro.Repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.fatec.carometro.Utils.Criptografia.criptografarSenha;
import static com.fatec.carometro.Utils.Criptografia.descriptografaSenha;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new LoginException("Email ou senha inválidos."));
        if (!descriptografaSenha(usuario.getSenha(), senha))
            throw new LoginException("Email ou senha inválidos.");
        return usuario;
    }
}
