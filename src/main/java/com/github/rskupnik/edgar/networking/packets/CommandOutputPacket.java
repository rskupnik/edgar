package com.github.rskupnik.edgar.networking.packets;

import com.github.rskupnik.pigeon.commons.Packet;
import com.github.rskupnik.pigeon.commons.annotations.PacketDataField;
import com.github.rskupnik.pigeon.commons.annotations.PigeonPacket;

@PigeonPacket(id=5)
public class CommandOutputPacket extends Packet {

    public CommandOutputPacket() {

    }

    public CommandOutputPacket(String output) {
        this.output = output;
    }

    @PacketDataField
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
