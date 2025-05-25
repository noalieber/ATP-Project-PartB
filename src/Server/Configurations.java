package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * singleton class that loads configuration values from a config.properties file
 * located in the resources directory. provides access to the following settings:
 * - thread pool size
 * - maze generation algorithm
 * - maze searching algorithm
 */
public class Configurations {
    private static Configurations instance = null;
    private final Properties properties;

    /**
     * private constructor that loads configuration values from config.properties.
     * if the file is not found, default values will be used.
     */
    private Configurations() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the singleton instance of the configuration class.
     * initializes it if not already created.
     */
    public static Configurations getInstance() {
        if (instance == null) {
            synchronized (Configurations.class) {
                if (instance == null)
                    instance = new Configurations();
            }
        }
        return instance;
    }

    /**
     * retrieves the number of threads to be used in the thread pool.
     * returns 4 if the value is not specified in the config file.
     */
    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "4"));
    }

    /**
     * retrieves the name of the maze generation algorithm.
     * returns "MyMazeGenerator" if the value is not specified.
     */
    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    /**
     * retrieves the name of the maze searching algorithm.
     * returns "BestFirstSearch" if the value is not specified.
     */
    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }
}
