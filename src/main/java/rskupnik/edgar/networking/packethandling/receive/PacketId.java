package rskupnik.edgar.networking.packethandling.receive;

final class PacketId {

    static final int DISCONNECT_PACKET = 0x00;
    static final int COMMAND_PACKET = 0x01;
    static final int HANDSHAKE_PACKET = 0x02;

    private PacketId() {

    }
}
