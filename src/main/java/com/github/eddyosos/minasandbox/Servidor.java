package com.github.eddyosos.minasandbox;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Servidor {

    @Bean(initMethod = "bind", destroyMethod = "dispose")
    public IoAcceptor acceptor(IoHandler handler, ProtocolCodecFactory fabrica) {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.setHandler(handler);
        acceptor.getFilterChain().addLast("mensagemCruaCodec", new ProtocolCodecFilter(fabrica));

        return acceptor;
    }


    @Bean
    public IoHandlerAdapter handler(){
        return new IoHandlerAdapter();
    }
}
