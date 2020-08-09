package com.github.eddyosos.minasandbox.mensagem_crua;

import org.apache.mina.core.buffer.IoBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MensagemCruaTest {

    @Test
    void OK() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        new MensagemCrua(IoBuffer.wrap(input));
    }

    @Test
    void inicioInvalido() {
        byte[] input = {0x00, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void fimInvalido() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0C};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void crcInvalido() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC7, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void curto() {
        byte[] input = {0x0A, 0x09};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void byteInvalido() {
        byte[] input = {0x0A, 0x03, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void mensagemIncompleta() {
        byte[] input = {0x0A, (byte)0xFF, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> new MensagemCrua(IoBuffer.wrap(input)));
    }

    @Test
    void encodeDecode() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        var mensagem = new MensagemCrua(IoBuffer.wrap(input));
        var output = new byte[input.length];
        mensagem.serializada().get(output);
        assertArrayEquals(input, output);
    }
}