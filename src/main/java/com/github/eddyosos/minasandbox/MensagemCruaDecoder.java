package com.github.eddyosos.minasandbox;

import com.crccalc.Crc8;
import com.crccalc.CrcCalculator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class MensagemCruaDecoder extends ProtocolDecoderAdapter {
    private static final byte MIN_MESSAGE_SIZE = 0x05;

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        out.write(extraiDe(in));
    }

    MensagemCrua extraiDe(IoBuffer in) {
        if (in.remaining() < MIN_MESSAGE_SIZE) {
            throw new IllegalArgumentException("Mensagem menor do que o mínimo esperado");
        }
        byte INIT = in.get();
        if(INIT != 0x0A) throw new IllegalArgumentException("Mensagem não começa com 0x0A");
        byte BYTES = in.get();
        int BYTES_VAL = BYTES & 0xFF;
        if(BYTES_VAL < MIN_MESSAGE_SIZE || in.remaining() < BYTES_VAL - 2) {
            throw new IllegalArgumentException("Mensagem não tem o tamanho especificado no campo BYTES");
        }
        byte FRAME = in.get();
        byte[] DATA = new byte[BYTES_VAL - MIN_MESSAGE_SIZE];
        in.get(DATA);
        byte CRC = in.get();
        if(!isCRCvalido(BYTES, FRAME, DATA, CRC)) {
            throw new IllegalArgumentException("CRC não bate");
        }
        byte END = in.get();
        if(END != 0x0D) throw new IllegalArgumentException("Mensagem não termina me 0x0D");
        return new MensagemCrua(INIT, BYTES, FRAME, DATA, CRC, END);
    }

    boolean isCRCvalido(byte BYTES, byte FRAME, byte[] DATA, byte CRC) {
        byte[] crcData = new byte[DATA.length + 2];
        crcData[0] = BYTES;
        crcData[1] = FRAME;
        System.arraycopy(DATA, 0, crcData, 2, DATA.length);
        return CRC == (byte) crc8(crcData);
    }

    long crc8(byte... data) {
        CrcCalculator calculator = new CrcCalculator(Crc8.Crc8);
        return calculator.Calc(data, 0, data.length);
    }

}
