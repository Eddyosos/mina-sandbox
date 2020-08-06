package com.github.eddyosos.minasandbox;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MensagemCruaDecoderTest {

    MensagemCruaDecoder decoder = new MensagemCruaDecoder();

    @Test
    void OK() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        decoder.extraiDe(IoBuffer.wrap(input));
    }

    @Test
    void inicioInvalido() {
        byte[] input = {0x00, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }

    @Test
    void fimInvalido() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0C};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }

    @Test
    void crcInvalido() {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC7, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }

    @Test
    void curto() {
        byte[] input = {0x0A, 0x09};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }

    @Test
    void byteInvalido() {
        byte[] input = {0x0A, 0x03, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }

    @Test
    void mensagemIncompleta() {
        byte[] input = {0x0A, (byte)0xFF, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        assertThrows(
                IllegalArgumentException.class,
                () -> decoder.extraiDe(IoBuffer.wrap(input)));
    }


}