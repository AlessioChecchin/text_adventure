package com.adventure.config;

import com.adventure.Resources;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.Game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.*;

/**
 * This class implements a singleton pattern.
 * The responsibility of this class is to provide a context object in the whole application.
 */
public class ApplicationContextProvider implements ApplicationContext
{
    //
    // CONSTRUCTORS
    //

    /**
     * Private singleton constructor.
     * @throws ConfigurationException Thrown if the configuration file is not found or has a bad format.
     */
    private ApplicationContextProvider() throws ConfigurationException
    {
        Properties props = new Properties();

        try (InputStream fis = Resources.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            props.load(fis);

            this.config = new ApplicationConfig(props);
        }
        catch (Exception e)
        {
            logger.fatal("Error loading application.conf", e);
            throw new ConfigurationException(e.getMessage());
        }
    }

    /**
     * Singleton getter.
     * Returns null if the configuration file is not found.
     * @return Singleton instance.
     */
    public static ApplicationContextProvider getInstance()
    {
        if(instance == null)
        {
            try
            {
                instance = new ApplicationContextProvider();
            }
            catch(ConfigurationException e)
            {
                return null;
            }
        }
        return instance;
    }

    //
    // GETTERS
    //

    /**
     * Game getter.
     * @return Game.
     */
    @Override
    public Game getGame()
    {
        return this.game;
    }

    /**
     * Application configuration getter.
     * @return Current application config.
     */
    public Config getConfig()
    {
        return this.config;
    }

    //
    // SETTERS
    //

    /**
     * Game setter.
     * @param game Game.
     */
    @Override
    public void setGame(Game game)
    {
        Objects.requireNonNull(game, "game cannot be null");

        if(this.game != null)
        {
            this.game.shutdown();
        }

        this.game = game;
    }

    //
    //  VARIABLES
    //

    /**
     * Context singleton.
     */
    private static ApplicationContextProvider instance;

    /**
     * Application config.
     */
    private final Config config;

    /**
     * Current game instance.
     */
    private Game game;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();
}
