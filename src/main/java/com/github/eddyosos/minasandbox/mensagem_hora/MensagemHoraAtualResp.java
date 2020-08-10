package com.github.eddyosos.minasandbox.mensagem_hora;

import com.github.eddyosos.minasandbox.Servidor;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MensagemHoraAtualResp implements Servidor.Codificavel {
    private LocalDateTime horaAtual;

    public MensagemHoraAtualResp(ZoneId timeZone) {
        horaAtual = LocalDateTime.now(timeZone);
    }

    @Override
    public byte getFRAME() {
        return (byte) 0xA3;
    }

    @Override
    public byte[] getDATA() {
        return new byte[]{
                (byte) horaAtual.getDayOfMonth(),
                (byte) horaAtual.getMonthValue(),
                (byte) horaAtual.getYear(),
                (byte) horaAtual.getHour(),
                (byte) horaAtual.getMinute(),
                (byte) horaAtual.getSecond()
            };
    }
}
