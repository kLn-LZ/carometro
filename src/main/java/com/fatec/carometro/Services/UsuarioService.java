package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Repositories.UsuarioRepository;
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
        return usuarioRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) throw new RuntimeException("Usuário Não Encontrado");

        Usuario usuario = usuarioOpt.get();
        if (!descriptografaSenha(senha, usuario.getSenha())) throw new RuntimeException(("Senha Incorreta"));

        return usuario;
    }
}
