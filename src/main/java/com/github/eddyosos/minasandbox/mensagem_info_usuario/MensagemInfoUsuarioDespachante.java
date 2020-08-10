package com.github.eddyosos.minasandbox.mensagem_info_usuario;

import com.github.eddyosos.minasandbox.Servidor;
import com.github.eddyosos.minasandbox.mensagem_ack.MensagemAck;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemInfoUsuarioDespachante implements Servidor.Despachante<MensagemInfoUsuario> {

    @Autowired MensagemInfoUsuarioRepositorio mensagemInfoUsuarioRepositorio;

    @Override
    public Class<MensagemInfoUsuario> capacidade() { return MensagemInfoUsuario.class; }

    @Override
    public void handleMessage(IoSession session, MensagemInfoUsuario message) {
        mensagemInfoUsuarioRepositorio.save(message);
        session.write(new MensagemAck());
    }
}
