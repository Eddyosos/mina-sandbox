package com.github.eddyosos.minasandbox.mensagem_texto;

import com.github.eddyosos.minasandbox.Servidor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

@Entity
public class MensagemTexto implements Servidor.Codificavel {
    public static final byte FRAME = (byte) 0xA1;

    @Id
    @GeneratedValue
    private Long id;
    private String texto;
    private Instant momentoCadastro;

    public MensagemTexto() {}

    public MensagemTexto(byte[] DATA) {
        this.texto = new String(DATA, StandardCharsets.US_ASCII);
        this.momentoCadastro = Instant.now();
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public String getTexto(){
        return texto;
    }

    public Instant getMomentoCadastro() {
        return momentoCadastro;
    }

    @Transient
    @Override
    public byte getFRAME() {
        return FRAME;
    }

    @Transient
    @Override
    public byte[] getDATA() {
        return texto.getBytes(StandardCharsets.US_ASCII);
    }

}
