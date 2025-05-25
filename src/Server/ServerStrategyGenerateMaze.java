package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient);

            int[] dimensions = (int[]) objectInputStream.readObject(); // [rows, cols]

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

            Maze maze = generator.generate(dimensions[0], dimensions[1]);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteStream);
            compressor.write(maze.toByteArray());
            compressor.flush();
            compressor.close();

            byte[] compressedMaze = byteStream.toByteArray();
            objectOutputStream.writeObject(compressedMaze);
            objectOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
