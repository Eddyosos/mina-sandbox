package com.github.eddyosos.minasandbox;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemCruaFactory implements ProtocolCodecFactory {

    @Autowired private MensagemCruaEncoder encoder;
    @Autowired private MensagemCruaDecoder decoder;

    @Override
    public ProtocolEncoder getEncoder(IoSession session) {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) {
        return decoder;
    }
}
