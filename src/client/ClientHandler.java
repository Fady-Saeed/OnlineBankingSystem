package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private String ip;
    private int port;
    private Socket clientSocket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Socket connect() throws IOException {
        this.clientSocket = new Socket(this.ip, this.port);
        this.dis = new DataInputStream(clientSocket.getInputStream());
        this.dos = new DataOutputStream(clientSocket.getOutputStream());
        return this.clientSocket;
    }
    public void close() throws IOException {
        this.dis.close();
        this.dos.close();
        this.clientSocket.close();
    }

    public DataOutputStream getDOS() {
        return dos;
    }

    public DataInputStream getDIS() {
        return dis;
    }
}
