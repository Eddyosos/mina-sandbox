package com.github.eddyosos.minasandbox.mensagem_crua;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public abstract class MensagemCruaDecoder extends MessageDecoderAdapter {

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        MessageDecoderResult result;
        try {
            var crua = new MensagemCrua(in);
            if (crua.getFRAME() == getFrame()) {
                result = MessageDecoderResult.OK;
            } else result = MessageDecoderResult.NOT_OK;
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
            if (crua.getFRAME() == getFrame()) {
                result = MessageDecoderResult.OK;
                out.write(getMessage(crua.getDATA()));
            } else result = MessageDecoderResult.NOT_OK;
        } catch (MensagemIncompletaException ex) {
            result = MessageDecoderResult.NEED_DATA;
        } catch (MensagemInvalidaException ex) {
            result = MessageDecoderResult.NOT_OK;
        }

        return result;
    }

    protected abstract byte getFrame();
    protected abstract Object getMessage(byte[] DATA);
}
