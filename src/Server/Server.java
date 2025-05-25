package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * a generic server class that accepts client connections and processes them using a strategy.
 * the server listens on a specific port and handles requests using a thread pool.
 */
public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop = false;

    private ExecutorService threadPool;

    /**
     * constructs a new server instance.
     *
     * @param port the port to listen on
     * @param listeningIntervalMS the timeout (in milliseconds) for accepting new clients
     * @param strategy the strategy used to handle each client
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        // initialize thread pool with size from config
        int poolSize = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * starts the server in a new thread.
     */
    public void start() {
        new Thread(this::runServer).start();
    }

    /**
     * main server loop: accepts new clients and delegates them to the thread pool.
     */
    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("server started at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("client connected: " + clientSocket);

                    threadPool.submit(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
                    // check stop flag periodically
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown(); // release all resources
        }
    }

    /**
     * handles a single client using the configured strategy.
     *
     * @param clientSocket the client socket
     */
    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * signals the server to stop accepting new clients.
     */
    public void stop() {
        stop = true;
    }
}
