package com.github.rskupnik.edgar.networking.packets;

import com.github.rskupnik.pigeon.commons.Packet;
import com.github.rskupnik.pigeon.commons.annotations.PacketDataField;
import com.github.rskupnik.pigeon.commons.annotations.PigeonPacket;

@PigeonPacket(id=2)
public class CommandPacket extends Packet {

    @PacketDataField
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
