package com.github.eddyosos.minasandbox.mensagem_info_usuario;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCruaDecoder;
import com.github.eddyosos.minasandbox.mensagem_texto.MensagemTexto;
import org.springframework.stereotype.Service;

@Service
public class MensagemInfoUsuarioDecoder extends MensagemCruaDecoder {

    @Override
    protected byte getFrame() {
        return (byte) 0xA2;
    }

    @Override
    protected Object getMessage(byte[] DATA) {
        return new MensagemInfoUsuario(DATA);
    }
}
