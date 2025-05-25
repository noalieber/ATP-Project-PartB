package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Defines a communication strategy from client to server.
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
