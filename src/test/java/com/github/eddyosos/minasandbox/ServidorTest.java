package com.github.eddyosos.minasandbox;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCrua;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServidorTest {

    @MockBean IoHandler mockHandler;
    @Autowired IoAcceptor acceptor;

    @Test
    void isSocketRecebendoMensagemCrua() throws Exception {
        byte[] input = {0x0A, 0x09, 0x01, 0x31, 0x32, 0x33, 0x34, (byte) 0xC6, 0x0D};
        IoConnector connector = new NioSocketConnector();
        connector.setHandler(new IoHandlerAdapter(){
            @Override
            public void sessionOpened(IoSession session) {
                session.write(IoBuffer.wrap(input));
                session.closeOnFlush();
            }
        });
        assertTrue(connector.connect(acceptor.getLocalAddress()).await(1000, TimeUnit.MILLISECONDS));
        verify(mockHandler, timeout(1000)).messageReceived(any(), isA(MensagemCrua.class));
    }
}