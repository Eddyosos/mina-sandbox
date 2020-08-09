package com.github.eddyosos.minasandbox;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCruaFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Servidor {

    @Bean(initMethod = "bind", destroyMethod = "dispose")
    public IoAcceptor acceptor(
            IoHandler handler,
            IoFilterChainBuilder filterChainBuilder) {
        var resultado = new NioSocketAcceptor();
        resultado.setHandler(handler);
        resultado.setFilterChainBuilder(filterChainBuilder);
        return resultado;
    }

    @Bean public IoHandlerAdapter handler(){
        return new IoHandlerAdapter();
    }

    @Bean
    public IoFilterChainBuilder filterChainBuilder(
            MensagemCruaFactory fabrica) {
        var resultado = new DefaultIoFilterChainBuilder();
        resultado.addLast("codec",  new ProtocolCodecFilter(fabrica));
        return resultado;
    }
}
