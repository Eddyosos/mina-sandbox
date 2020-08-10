package com.github.eddyosos.minasandbox.mensagem_info_usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Entity
public class MensagemInfoUsuario {
    @Id @GeneratedValue
    private Long id;
    private byte idade;
    private byte peso;
    private byte altura;
    private String nome;

    MensagemInfoUsuario(){}

    MensagemInfoUsuario(byte[] DATA) {
        idade = DATA[0];
        peso = DATA[1];
        altura = DATA[2];
        var tamanhoNome = DATA[3] & 0xFF;
        var nomeBytes = Arrays.copyOfRange(DATA, 4, tamanhoNome + 4);
        nome = new String(nomeBytes, StandardCharsets.US_ASCII);
    }

    public Long getId() {
        return id;
    }

    public byte getIdade() {
        return idade;
    }

    public byte getPeso() {
        return peso;
    }

    public byte getAltura() {
        return altura;
    }

    public String getNome() {
        return nome;
    }
}
