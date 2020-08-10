package com.github.eddyosos.minasandbox.mensagem_texto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemTextoRepositorio extends CrudRepository<MensagemTexto, Long> {}
