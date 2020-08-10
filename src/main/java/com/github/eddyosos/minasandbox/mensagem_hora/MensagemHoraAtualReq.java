package com.github.eddyosos.minasandbox.mensagem_hora;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;

public class MensagemHoraAtualReq {
    private ZoneId timeZoneId;

    MensagemHoraAtualReq(byte[] DATA) {
        String tzIdString = new String(DATA, StandardCharsets.US_ASCII);
        timeZoneId = ZoneId.of(tzIdString);
    }

    MensagemHoraAtualResp getHoraAtual() {
        return new MensagemHoraAtualResp(timeZoneId);
    }
}
