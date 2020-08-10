package com.github.eddyosos.minasandbox.mensagem_hora;

import com.github.eddyosos.minasandbox.Servidor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

@Service
public class MensagemHoraAtualReqDespachante implements Servidor.Despachante<MensagemHoraAtualReq> {

    @Override
    public Class<MensagemHoraAtualReq> capacidade() {
        return MensagemHoraAtualReq.class;
    }

    @Override
    public void handleMessage(IoSession session, MensagemHoraAtualReq message) {
        session.write(message.getHoraAtual());
    }
}
