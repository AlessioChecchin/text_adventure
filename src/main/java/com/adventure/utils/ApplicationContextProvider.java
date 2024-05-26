package com.adventure.utils;

import com.adventure.Main;
import com.adventure.models.Game;

import com.adventure.services.BucketStorageService;
import com.adventure.services.StorageService;

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
     */
    private ApplicationContextProvider()
    {
        this.properties = new Properties();
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            this.properties.load(fis);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.storageService = new BucketStorageService(this.properties);
    }

    /**
     * Singleton getter.
     * @return Singleton instance.
     */
    public static ApplicationContextProvider getInstance()
    {
        if(instance == null) { instance = new ApplicationContextProvider(); }
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
}
