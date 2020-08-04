package com.github.eddyosos.minasandbox;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.CapturingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServerTest {

    @MockBean IoHandler mockHandler;
    @Autowired IoAcceptor acceptor;

    @Test
    void isSocketOpenTest() throws Exception {
        IoConnector connector = new NioSocketConnector();
        byte[] input = DatatypeConverter.parseHexBinary("0A090131323334C60D");
        connector.setHandler(new IoHandlerAdapter(){
            @Override
            public void sessionOpened(IoSession session) throws Exception {
                session.write(IoBuffer.wrap(input));
                session.closeOnFlush();
            }
        });
        assertTrue(connector.connect(acceptor.getLocalAddress()).await(10, TimeUnit.SECONDS));
        ArgumentCaptor<IoBuffer> captor = ArgumentCaptor.forClass(IoBuffer.class);
        verify(mockHandler, after(Duration.ofSeconds(5).toMillis()).atLeastOnce())
                .messageReceived(any(), captor.capture());
        byte[] output = new byte[input.length];
        captor.getValue().get(output);
        assertArrayEquals(input, output);
    }
}