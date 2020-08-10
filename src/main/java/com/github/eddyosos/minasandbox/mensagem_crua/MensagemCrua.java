package com.github.eddyosos.minasandbox.mensagem_crua;

import com.crccalc.Crc8;
import com.crccalc.CrcCalculator;
import com.github.eddyosos.minasandbox.Servidor;
import org.apache.mina.core.buffer.IoBuffer;

public class MensagemCrua {
    public static final byte MIN_MESSAGE_SIZE = 0x05;
    public static final byte INIT_ESPERADO = 0x0A;
    public static final byte END_ESPERADO = 0x0D;

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


    public MensagemCrua(IoBuffer mensagem) {
        if (mensagem.remaining() < MIN_MESSAGE_SIZE) {
            throw new MensagemIncompletaException("Mensagem menor do que o mínimo esperado");
        }
        INIT = mensagem.get();
        if(INIT != INIT_ESPERADO) throw new IllegalArgumentException("Mensagem não começa com 0x0A");
        BYTES = mensagem.get();
        if(BYTES_val() < MIN_MESSAGE_SIZE) {
            throw new MensagemInvalidaException("Campo BYTES com valor inválido");
        }
        if(mensagem.remaining() < BYTES_val() - 2) {
            throw new MensagemIncompletaException("Mensagem não tem o tamanho especificado no campo BYTES");
        }
        FRAME = mensagem.get();
        DATA = new byte[BYTES_val() - MIN_MESSAGE_SIZE];
        mensagem.get(DATA);
        CRC = mensagem.get();
        if(!isCRC_valido()) {
            throw new MensagemInvalidaException("CRC não bate");
        }
        END = mensagem.get();
        if(END != END_ESPERADO) throw new MensagemInvalidaException("Mensagem não termina me 0x0D");
    }

    private int BYTES_val() {
        return BYTES & 0xFF;
    }

    private boolean isCRC_valido() {
        return CRC == calculaCRC();
    }

    private byte calculaCRC() {
        byte[] crcData = new byte[DATA.length + 2];
        crcData[0] = BYTES;
        crcData[1] = FRAME;
        System.arraycopy(DATA, 0, crcData, 2, DATA.length);
        return (byte) crc8(crcData);
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

    public MensagemCrua(Servidor.Codificavel mensagem) {
        INIT = INIT_ESPERADO;
        BYTES = (byte) (mensagem.getDATA().length + MIN_MESSAGE_SIZE);
        FRAME = mensagem.getFRAME();
        DATA = mensagem.getDATA();
        CRC = calculaCRC();
        END = END_ESPERADO;
    }

    public byte getFRAME() {
        return FRAME;
    }

    public byte[] getDATA(){
        return DATA;
    }
}
