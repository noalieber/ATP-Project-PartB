package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;

/**
 * ServerStrategySolveSearchProblem
 * receives a Maze object from client
 * checks if solution exists in cache (by hashing byte[])
 * if not solved, solves it using a search algorithm (from config)
 * saves the solution to a file in the system temp directory
 * sends the solution back to the client
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try (ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
             ObjectOutputStream toClient = new ObjectOutputStream(outToClient)) {

            // receive maze from client
            Maze maze = (Maze) fromClient.readObject();

            // generate hash for maze
            String tmpDir = System.getProperty("java.io.tmpdir");
            int mazeHash = Arrays.hashCode(maze.toByteArray());
            File solutionFile = new File(tmpDir, "maze_solution_" + mazeHash + ".sol");

            Solution solution;

            if (solutionFile.exists()) {
                // load existing solution from file
                try (ObjectInputStream solutionInput = new ObjectInputStream(new FileInputStream(solutionFile))) {
                    solution = (Solution) solutionInput.readObject();
                    System.out.println("Loaded solution from cache.");
                }
            } else {
                // get algorithm from configuration
                String algorithmName = Configurations.getInstance().getMazeSearchingAlgorithm();
                ISearchingAlgorithm solver;

                switch (algorithmName) {
                    case "DepthFirstSearch":
                        solver = new DepthFirstSearch();
                        break;
                    case "BreadthFirstSearch":
                        solver = new BreadthFirstSearch();
                        break;
                    default:
                        solver = new BestFirstSearch();
                        break;
                }

                // solve the maze
                ISearchable searchableMaze = new SearchableMaze(maze);
                solution = solver.solve(searchableMaze);
                System.out.println("Solved maze using " + solver.getClass().getSimpleName());

                // save solution to file
                try (ObjectOutputStream solutionOutput = new ObjectOutputStream(new FileOutputStream(solutionFile))) {
                    solutionOutput.writeObject(solution);
                    solutionOutput.flush();
                }
            }

            // send solution back to client
            toClient.writeObject(solution);
            toClient.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
