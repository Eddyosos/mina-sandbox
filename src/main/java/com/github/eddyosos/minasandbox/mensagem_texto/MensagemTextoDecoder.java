package com.github.eddyosos.minasandbox.mensagem_texto;

import com.github.eddyosos.minasandbox.mensagem_crua.MensagemCrua;
import com.github.eddyosos.minasandbox.mensagem_crua.MensagemIncompletaException;
import com.github.eddyosos.minasandbox.mensagem_crua.MensagemInvalidaException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

@Service
public class MensagemTextoDecoder extends MessageDecoderAdapter {

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        MessageDecoderResult result;
        try {
            var crua = new MensagemCrua(in);
            if (crua.getFRAME() == MensagemTexto.FRAME) {
                result = MessageDecoderResult.OK;
            } else result = NOT_OK;
        } catch (MensagemIncompletaException ex) {
            result = MessageDecoderResult.NEED_DATA;
        } catch (MensagemInvalidaException ex) {
            result = MessageDecoderResult.NOT_OK;
        }
        return result;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        MessageDecoderResult result;
        try {
            var crua = new MensagemCrua(in);
            if (crua.getFRAME() == MensagemTexto.FRAME) {
                result = MessageDecoderResult.OK;
                out.write(new MensagemTexto(crua.getDATA()));
            } else result = NOT_OK;
        } catch (MensagemIncompletaException ex) {
            result = MessageDecoderResult.NEED_DATA;
        } catch (MensagemInvalidaException ex) {
            result = MessageDecoderResult.NOT_OK;
        }

        return result;
    }
}
