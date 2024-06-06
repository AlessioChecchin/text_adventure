package com.adventure.utils;

import com.adventure.Main;
import com.adventure.config.ApplicationContextProvider;
import com.adventure.models.Game;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ApplicationContextProviderTest {

    private static ApplicationContextProvider instance;
    /**
     * Application properties.
     */
    private Properties properties;
    /**
     * Current game instance.
     */
    private Game game;
    /**
     * Final json
     */
    private StringBuilder json = new StringBuilder();
    /**
     * Game current stage (passed from ApplicationContextProvider)
     */
    private Stage stage;
    @BeforeEach
    void setUp() {
        properties = new Properties();
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            properties.load(fis);
        }
        catch (IOException ignored)
        {
            ignored.printStackTrace();
        }
    }

    @Test
    void getTest() {
        //exercise

    }
}