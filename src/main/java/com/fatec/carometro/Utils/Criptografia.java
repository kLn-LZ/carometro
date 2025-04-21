package com.fatec.carometro.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class Criptografia {
    static public String criptografarSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
    static public boolean descriptografaSenha(String senhaArmazeada, String senhaEntrada) {
        return BCrypt.checkpw(senhaArmazeada, senhaEntrada);
    }
}
