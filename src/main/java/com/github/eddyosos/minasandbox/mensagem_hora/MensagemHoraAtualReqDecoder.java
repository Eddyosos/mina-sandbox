package com.github.eddyosos.minasandbox.mensagem_hora;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCruaDecoder;
import org.springframework.stereotype.Service;

@Service
public class MensagemHoraAtualReqDecoder extends MensagemCruaDecoder {

    @Override
    protected byte getFrame() {
        return (byte) 0xA3;
    }

    @Override
    protected MensagemHoraAtualReq getMessage(byte[] DATA) {
        return new MensagemHoraAtualReq(DATA);
    }
}
