package rskupnik.edgar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientEmulator {

    private Socket socket;

    public ClientEmulator() throws IOException {
        try {
            socket = new Socket("localhost", 9432);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(1);
            outputStream.writeUTF("pieski małe dwa");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new ClientEmulator();
    }
}

