package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

/**
 * server strategy for generating a maze.
 * receives maze dimensions from the client, generates a maze using the configured generator,
 * compresses it, and sends it back to the client.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {

    /**
     * handles client request to generate a maze.
     *
     * @param inFromClient input stream from the client
     * @param outToClient output stream to the client
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient);

            // read maze dimensions from client
            int[] dimensions = (int[]) objectInputStream.readObject(); // [rows, cols]

            // select maze generator based on config
            String generatorName = Configurations.getInstance().getMazeGeneratingAlgorithm();
            AMazeGenerator generator;

            switch (generatorName) {
                case "SimpleMazeGenerator":
                    generator = new SimpleMazeGenerator();
                    break;
                case "EmptyMazeGenerator":
                    generator = new EmptyMazeGenerator();
                    break;
                default:
                    generator = new MyMazeGenerator();
                    break;
            }

            // generate the maze
            Maze maze = generator.generate(dimensions[0], dimensions[1]);

            // compress the maze data
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteStream);
            compressor.write(maze.toByteArray());
            compressor.flush();
            compressor.close();

            // send compressed maze to client
            byte[] compressedMaze = byteStream.toByteArray();
            objectOutputStream.writeObject(compressedMaze);
            objectOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
