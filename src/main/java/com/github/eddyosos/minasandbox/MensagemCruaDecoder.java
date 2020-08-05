package com.github.eddyosos.minasandbox;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.stereotype.Service;

@Service
public class MensagemCruaDecoder extends CumulativeProtocolDecoder {
    private static final int MIN_MESSAGE_SIZE = 5;

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        if(in.remaining() >= MIN_MESSAGE_SIZE) {
            byte INIT = in.get();
            byte BYTES = in.get();
            if(BYTES < MIN_MESSAGE_SIZE) throw new IllegalArgumentException("Valor inválido para BYTES");
            if(in.remaining() < BYTES - 2) return false; // Mensagem incompleta
            byte FRAME = in.get();
            byte[] DATA = new byte[BYTES - MIN_MESSAGE_SIZE];
            in.get(DATA);
            byte CRC = in.get();
            byte END = in.get();
            out.write(new MensagemCrua(INIT,BYTES,FRAME,DATA,CRC,END));
            return true;
        } else return false;
    }
}
