package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop = false;

    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        // get thread pool size from the config file
        int poolSize = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("server started at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("client connected: " + clientSocket);

                    // handle each client using a thread from the pool
                    threadPool.submit(() -> handleClient(clientSocket));

                } catch (SocketTimeoutException e) {
                    // timeout allows checking if the server should stop
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown(); // shut down all threads gracefully
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
