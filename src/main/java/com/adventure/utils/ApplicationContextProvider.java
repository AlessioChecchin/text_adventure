package com.adventure.utils;

import com.adventure.Resources;
import com.adventure.models.Game;

import com.adventure.services.BucketStorageService;
import com.adventure.services.FileSystemStorageService;
import com.adventure.services.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplicationContextProvider implements ApplicationContext
{
    //
    // CONSTRUCTORS
    //

    /**
     * Private singleton constructor.
     * @throws IOException Thrown if the configuration file is not found.
     */
    private ApplicationContextProvider() throws IOException
    {
        this.properties = new Properties();

        try (InputStream fis = Resources.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            this.properties.load(fis);
        }
        catch (IOException e)
        {
            logger.fatal("Error loading application.conf", e);
            throw e;
        }

        String storageProvider = this.properties.getProperty("storage.provider", "filesystem");
        if(storageProvider.equals("aws"))
        {
            this.storageService = new BucketStorageService(this.properties);
        }
        // Fallback storage service.
        else
        {
            logger.debug("Preferred storage provider set to filesystem...");
            this.storageService = new FileSystemStorageService(this.properties);
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
            catch(IOException e)
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
     * Properties getter.
     * @return Properties.
     */
    @Override
    public Properties getProperties()
    {
        return this.properties;
    }

    /**
     * Storage service getter.
     * @return Storage service.
     */
    public StorageService getStorageService()
    {
        return this.storageService;
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
     * Application properties.
     */
    private final Properties properties;

    /**
     * Current game instance.
     */
    private Game game;

    /**
     * Storage service.
     */
    private final StorageService storageService;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();

}
