package com.github.eddyosos.minasandbox;

import org.apache.mina.core.buffer.IoBuffer;

public class MensagemCrua {

    /**
     * Indica o início da mensagem
     */
    private byte INIT;

    /**
     * Indica quantos bytes a mensagem vai ter (A quantidade de bytes inclui todos os campos descritos
     * aqui)
     */
    private byte BYTES;

    /**
     * Indica qual é o tipo da mensagem
     */
    private byte FRAME;

    /**
     * É o corpo da mensagem, quando necessário será descrito cada campo individualmente na sua
     * mensagem.
     */
    private byte[] DATA;

    /**
     * Indica o cálculo do CRC para validar se a mensagem foi recebida corretamente.
     */
    private byte CRC;

    /**
     * Indica o final da mensagem
     */
    private byte END;

    public MensagemCrua(byte INIT, byte BYTES, byte FRAME, byte[] DATA, byte CRC, byte END) {
        this.INIT = INIT;
        this.BYTES = BYTES;
        this.FRAME = FRAME;
        this.DATA = DATA;
        this.CRC = CRC;
        this.END = END;
    }

    public byte getINIT() {
        return INIT;
    }

    public byte getBYTES() {
        return BYTES;
    }

    public byte getFRAME() {
        return FRAME;
    }

    public byte[] getDATA() {
        return DATA;
    }

    public byte getCRC() {
        return CRC;
    }

    public byte getEND() {
        return END;
    }
}
