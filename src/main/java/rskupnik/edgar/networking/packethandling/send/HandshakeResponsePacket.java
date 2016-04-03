package rskupnik.edgar.networking.packethandling.send;

import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakeResponsePacket extends Packet {

    private static final int id = 0x02;

    public HandshakeResponsePacket() {
        super(id);
    }

    @Override
    public void send(DataOutputStream outputStream) throws IOException {
        super.send(outputStream);
    }
}
