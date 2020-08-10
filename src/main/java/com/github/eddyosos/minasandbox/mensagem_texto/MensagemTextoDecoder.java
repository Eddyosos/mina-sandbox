package com.github.eddyosos.minasandbox.mensagem_texto;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCruaDecoder;
import org.springframework.stereotype.Service;

@Service
public class MensagemTextoDecoder extends MensagemCruaDecoder {

    @Override
    protected byte getFrame() {
        return MensagemTexto.FRAME;
    }

    @Override
    protected Object getMessage(byte[] DATA) {
        return new MensagemTexto(DATA);
    }
}
