package com.github.eddyosos.minasandbox.mensagem_crua;

import com.crccalc.Crc8;
import com.crccalc.CrcCalculator;
import org.apache.mina.core.buffer.IoBuffer;

public class MensagemCrua {

    /**
     * Indica o início da mensagem
     */
    private final byte INIT;

    /**
     * Indica quantos bytes a mensagem vai ter (A quantidade de bytes inclui todos os campos descritos
     * aqui)
     */
    private final byte BYTES;

    /**
     * Indica qual é o tipo da mensagem
     */
    private final byte FRAME;

    /**
     * É o corpo da mensagem, quando necessário será descrito cada campo individualmente na sua
     * mensagem.
     */
    private final byte[] DATA;

    /**
     * Indica o cálculo do CRC para validar se a mensagem foi recebida corretamente.
     */
    private final byte CRC;

    /**
     * Indica o final da mensagem
     */
    private final byte END;

    public MensagemCrua(IoBuffer in) {
        final byte MIN_MESSAGE_SIZE = 0x05;

        if (in.remaining() < MIN_MESSAGE_SIZE) {
            throw new IllegalArgumentException("Mensagem menor do que o mínimo esperado");
        }
        INIT = in.get();
        if(INIT != 0x0A) throw new IllegalArgumentException("Mensagem não começa com 0x0A");
        BYTES = in.get();
        if(BYTES_val() < MIN_MESSAGE_SIZE || in.remaining() < BYTES_val() - 2) {
            throw new IllegalArgumentException("Mensagem não tem o tamanho especificado no campo BYTES");
        }
        FRAME = in.get();
        DATA = new byte[BYTES_val() - MIN_MESSAGE_SIZE];
        in.get(DATA);
        CRC = in.get();
        if(!isCRC_valido()) {
            throw new IllegalArgumentException("CRC não bate");
        }
        END = in.get();
        if(END != 0x0D) throw new IllegalArgumentException("Mensagem não termina me 0x0D");
    }

    private int BYTES_val() {
        return BYTES & 0xFF;
    }

    private boolean isCRC_valido() {
        byte[] crcData = new byte[DATA.length + 2];
        crcData[0] = BYTES;
        crcData[1] = FRAME;
        System.arraycopy(DATA, 0, crcData, 2, DATA.length);
        return CRC == (byte) crc8(crcData);
    }

    private long crc8(byte... data) {
        CrcCalculator calculator = new CrcCalculator(Crc8.Crc8);
        return calculator.Calc(data, 0, data.length);
    }

    public IoBuffer serializada(){
        IoBuffer buffer = IoBuffer.allocate(BYTES);
        buffer.put(INIT);
        buffer.put(BYTES);
        buffer.put(FRAME);
        buffer.put(DATA);
        buffer.put(CRC);
        buffer.put(END);
        buffer.flip();
        return buffer;
    }
}
