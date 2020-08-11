package com.github.eddyosos.minasandbox;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCrua;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import org.apache.mina.handler.demux.MessageHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.List;

@Configuration
public class Servidor {

    @Bean(initMethod = "bind", destroyMethod = "dispose")
    public IoAcceptor acceptor(
            @Value("${port}") Integer port,
            IoHandler handler,
            IoFilterChainBuilder filterChainBuilder) {
        var resultado = new NioSocketAcceptor();
        resultado.setHandler(handler);
        resultado.setFilterChainBuilder(filterChainBuilder);
        resultado.setDefaultLocalAddress(new InetSocketAddress(port));
        return resultado;
    }

    @Bean
    public IoHandler handler(List<Despachante> dispachantes){
        var resultado = new DemuxingIoHandler();
        for(Despachante d : dispachantes) {
            resultado.addReceivedMessageHandler(d.capacidade(), d);
        }
        resultado.addSentMessageHandler(Codificavel.class, MessageHandler.NOOP);
        return resultado;
    }

    public interface Despachante<T> extends MessageHandler<T> {
        Class<T> capacidade();
    }

    @Bean
    public IoFilterChainBuilder filterChainBuilder(ProtocolCodecFilter codecFilter) {
        var resultado = new DefaultIoFilterChainBuilder();
        resultado.addLast("log", new LoggingFilter());
        resultado.addLast("codec",  codecFilter);
        return resultado;
    }

    @Bean
    public ProtocolCodecFilter codecFilter(List<MessageDecoder> decoders){
        var resultado = new DemuxingProtocolCodecFactory();
        decoders.forEach(resultado::addMessageDecoder);
        resultado.addMessageEncoder(Codificavel.class, (session, message, out) -> out.write(new MensagemCrua(message).serializada()));
        return new ProtocolCodecFilter(resultado);
    }

    public interface Codificavel {
        byte getFRAME();
        byte[] getDATA();
    }
}
