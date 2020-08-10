package com.github.eddyosos.minasandbox;

import java.nio.charset.StandardCharsets;

public class MensagemTexto implements Servidor.Codificavel {
    public static final byte FRAME = (byte) 0xA1;
    private final byte[] DATA;

    public MensagemTexto(byte[] DATA) {
        this.DATA = DATA;
    }

    public String getTexto(){
        return new String(DATA, StandardCharsets.US_ASCII);
    }

    @Override
    public byte getFRAME() {
        return FRAME;
    }

    @Override
    public byte[] getDATA() {
        return DATA;
    }
}
