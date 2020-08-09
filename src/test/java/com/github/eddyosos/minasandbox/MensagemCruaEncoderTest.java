package com.github.eddyosos.minasandbox;

import org.apache.mina.core.session.IoSession;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MensagemCruaEncoderTest {

    @Test
    void extraiDe() {
        var encoder = new MensagemCruaEncoder();
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        var mensagem = new MensagemCrua(input[0], input[1], input[2], Arrays.copyOfRange(input, 3, 7), input[7], input[8]);
        var output = new byte[input.length];
        encoder.extraiDe(mensagem).get(output);
        assertArrayEquals(input, output);
    }
}