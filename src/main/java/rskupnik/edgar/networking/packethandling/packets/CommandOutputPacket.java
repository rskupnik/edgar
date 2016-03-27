package rskupnik.edgar.networking.packethandling.packets;

import java.io.DataOutputStream;
import java.io.IOException;

public class CommandOutputPacket extends Packet {

    private static final int id = 0x01;

    private String output;

    public CommandOutputPacket(String output) {
        super(id);
        this.output = output;
    }

    @Override
    public void send(DataOutputStream outputStream) throws IOException {
        super.send(outputStream);
        outputStream.writeUTF(output);
    }
}
