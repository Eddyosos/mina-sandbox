package com.github.eddyosos.minasandbox;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.stereotype.Service;

@Service
public class MensagemCruaEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) {
        MensagemCrua resposta = (MensagemCrua) message;
        out.write(extraiDe(resposta));
    }

    IoBuffer extraiDe(MensagemCrua resposta) {
        IoBuffer buffer = IoBuffer.allocate(resposta.getBYTES());
        buffer.put(resposta.getINIT());
        buffer.put(resposta.getBYTES());
        buffer.put(resposta.getFRAME());
        buffer.put(resposta.getDATA());
        buffer.put(resposta.getCRC());
        buffer.put(resposta.getEND());
        buffer.flip();
        return buffer;
    }
}
