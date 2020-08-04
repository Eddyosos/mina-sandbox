package com.github.eddyosos.minasandbox;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Server {

    @Bean(initMethod = "bind", destroyMethod = "dispose")
    public IoAcceptor acceptor(IoHandler handler) {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.setHandler(handler);
        return acceptor;
    }
}
