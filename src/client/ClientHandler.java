package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler {

    private String ip;
    private int port;
    private Socket clientSocket;
    private DataInputStream dis;
    private DataOutputStream dos;

    // Done
    ClientHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // Done
    void connect() throws IOException {
        this.clientSocket = new Socket(this.ip, this.port);
        this.dis = new DataInputStream(clientSocket.getInputStream());
        this.dos = new DataOutputStream(clientSocket.getOutputStream());
    }

    // Done
    void close() throws IOException {
        this.dis.close();
        this.dos.close();
        this.clientSocket.close();
    }

    // Done
    DataOutputStream getDOS() {
        return dos;
    }

    // Done
    DataInputStream getDIS() {
        return dis;
    }
}
