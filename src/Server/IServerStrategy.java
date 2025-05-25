package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * interface for implementing server-side strategies.
 * each strategy defines how to handle input and output streams for a single client.
 */
public interface IServerStrategy {
    /**
     * executes the logic for handling a client request.
     * the method receives input and output streams connected to the client.
     *
     * @param inFromClient input stream from the client
     * @param outToClient output stream to the client
     */
    void serverStrategy(InputStream inFromClient, OutputStream outToClient);
}
