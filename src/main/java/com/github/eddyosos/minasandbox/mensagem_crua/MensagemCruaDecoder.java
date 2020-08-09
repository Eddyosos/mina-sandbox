package com.github.eddyosos.minasandbox.mensagem_crua;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.stereotype.Service;

@Service
public class MensagemCruaDecoder extends ProtocolDecoderAdapter {

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        out.write(new MensagemCrua(in));
    }

}
