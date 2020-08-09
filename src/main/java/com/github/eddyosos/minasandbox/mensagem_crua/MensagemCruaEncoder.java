package com.github.eddyosos.minasandbox.mensagem_crua;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.stereotype.Service;

@Service
public class MensagemCruaEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) {
        MensagemCrua resposta = (MensagemCrua) message;
        out.write(resposta.serializada());
    }
}
