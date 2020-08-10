package com.github.eddyosos.minasandbox.mensagem_texto;

import com.github.eddyosos.minasandbox.Servidor;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemTextoDispachante implements Servidor.Dispachante<MensagemTexto> {

    @Autowired MensagemTextoRepositorio repositorio;

    @Override
    public Class<MensagemTexto> capacidade() {
        return MensagemTexto.class;
    }

    @Override
    public void handleMessage(IoSession session, MensagemTexto message) {
        repositorio.save(message);
    }
}
