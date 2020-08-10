package com.github.eddyosos.minasandbox.mensagem_ack;

import com.github.eddyosos.minasandbox.Servidor;

public class MensagemAck implements Servidor.Codificavel {

    @Override
    public byte getFRAME() {
        return (byte) 0xA0;
    }

    @Override
    public byte[] getDATA() {
        return new byte[0];
    }
}
