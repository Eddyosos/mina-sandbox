package com.github.eddyosos.minasandbox.mensagem_texto;

import com.github.eddyosos.minasandbox.Servidor;
import com.github.eddyosos.minasandbox.mensagem_ack.MensagemAck;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemTextoDespachante implements Servidor.Despachante<MensagemTexto> {

    @Autowired MensagemTextoRepositorio repositorio;

    @Override
    public Class<MensagemTexto> capacidade() {
        return MensagemTexto.class;
    }

    @Override
    public void handleMessage(IoSession session, MensagemTexto message) {
        repositorio.save(message);
        session.write(new MensagemAck());
    }
}
