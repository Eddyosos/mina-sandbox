package com.github.eddyosos.minasandbox;

import com.github.eddyosos.minasandbox.mensagem_info_usuario.MensagemInfoUsuarioRepositorio;
import com.github.eddyosos.minasandbox.mensagem_texto.MensagemTextoRepositorio;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServidorTest {

    @Autowired IoAcceptor acceptor;

    @Autowired MensagemTextoRepositorio mensagemTextoRepositorio;
    @Autowired MensagemInfoUsuarioRepositorio mensagemInfoUsuarioRepositorio;

    @Test
    void mensagemTexto() throws Exception {
        byte[] input = {0x0A, 0x10, (byte) 0XA1, 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x20, 0x57, 0x6F, 0x72, 0x6C, 0x64, (byte) 0xDC, 0x0D};
        byte[] output = {0x0A, 0x05, (byte) 0xA0, 0x28, 0x0D};
        escreveRecebeFecha(input,output);

        var mensagens = mensagemTextoRepositorio.findAll().iterator();
        assertEquals("Hello World", mensagens.next().getTexto());
        assertFalse(mensagens.hasNext());
    }

    @Test
    void informacoesUsuario() throws Exception {
        byte[] input = {
                0x0A, 0x15, (byte) 0xA2, 0x20, 0x7A, (byte) 0xC3, 0x0C, 0x4D, 0x69, 0x63, 0x68,
                0x65, 0x6C, 0x20, 0x52, 0x65, 0x69, 0x70, 0x73, 0x16, 0x0D};
        byte[] output = {0x0A, 0x05, (byte) 0xA0, 0x28, 0x0D};
        escreveRecebeFecha(input,output);

        var mensagens = mensagemInfoUsuarioRepositorio.findAll().iterator();
        assertEquals("Michel Reips", mensagens.next().getNome());
        assertFalse(mensagens.hasNext());
    }

    @Test
    void horaAtual() throws Exception {
        byte[] input = {
                0x0A, 0x16, (byte)0xA3, 0x41, 0x6D, 0x65, 0x72, 0x69, 0x63,
                0x61, 0x2F, 0x53, 0x61, 0x6F, 0x5F, 0x50, 0x61, 0x75,
                0x6C, 0x6F, (byte)0xCD, 0x0D};
        escreveRecebeFecha(input);
    }
    private void escreveRecebeFecha(byte[] input) throws InterruptedException {
        escreveRecebeFecha(input, null);
    }
    private void escreveRecebeFecha(byte[] input, byte[] output) throws InterruptedException {
        IoConnector connector = new NioSocketConnector();
        MyIoHandlerAdapter handler = spy(new MyIoHandlerAdapter(input));
        connector.setHandler(handler);
        assertTrue(connector.connect(acceptor.getLocalAddress()).await(1000, TimeUnit.MILLISECONDS));
        verify(handler, timeout(1000)).messageReceived(any(), output != null ? eq(output) : any());
    }

    private static class MyIoHandlerAdapter extends IoHandlerAdapter {
        private final byte[] input;

        public MyIoHandlerAdapter(byte[] input) {
            this.input = input;
        }

        @Override
        public void sessionOpened(IoSession session) {
            session.write(IoBuffer.wrap(input));
        }

        @Override
        public void messageReceived(IoSession session, Object message) {
            session.closeOnFlush();
        }
    }
}