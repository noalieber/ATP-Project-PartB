package Client;

import Client.IClientStrategy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Generic client class. Communicates with server using a strategy.
 */
public class Client {
    private final InetAddress serverIP;
    private final int serverPort;
    private final IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * Connects to the server and applies the strategy.
     * This method replaces 'start' and is required by the project.
     */
    public void communicateWithServer() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
            System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
