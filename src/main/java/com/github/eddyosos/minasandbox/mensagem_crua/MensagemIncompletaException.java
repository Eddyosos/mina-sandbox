package com.github.eddyosos.minasandbox.mensagem_crua;

public class MensagemIncompletaException extends IllegalArgumentException{

    public MensagemIncompletaException(String message) {
        super(message);
    }
}
