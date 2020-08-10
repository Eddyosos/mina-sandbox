package com.github.eddyosos.minasandbox;

import com.github.eddyosos.minasandbox.mensagem_texto.MensagemTexto;
import com.github.eddyosos.minasandbox.mensagem_texto.MensagemTextoRepositorio;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServidorTest {

    @Autowired IoAcceptor acceptor;
    @Autowired MensagemTextoRepositorio mensagemTextoRepositorio;

    @Test
    void mensagemTexto() throws Exception {
        byte[] input = {0x0A, 0x10, (byte) 0XA1, 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x20, 0x57, 0x6F, 0x72, 0x6C, 0x64, (byte) 0xDC, 0x0D};
        escreveFecha(input);

        var mensagens = mensagemTextoRepositorio.findAll().iterator();
        assertEquals("Hello World", mensagens.next().getTexto());
        assertFalse(mensagens.hasNext());
    }

    private void escreveFecha(byte[] input) throws InterruptedException {
        IoConnector connector = new NioSocketConnector();
        connector.setHandler(new IoHandlerAdapter() {
            @Override
            public void sessionOpened(IoSession session) {
                session.write(IoBuffer.wrap(input));
                session.closeOnFlush();
            }
        });
        assertTrue(connector.connect(acceptor.getLocalAddress()).await(1000, TimeUnit.MILLISECONDS));
    }
}