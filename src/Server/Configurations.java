package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    private static Configurations instance = null;
    private final Properties properties;

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

    public static Configurations getInstance() {
        if (instance == null) {
            synchronized (Configurations.class) {
                if (instance == null)
                    instance = new Configurations();
            }
        }
        return instance;
    }

    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "4"));
    }

    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }
}
