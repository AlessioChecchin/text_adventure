package com.adventure.utils;

import com.adventure.Main;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.models.Game;


import com.adventure.storage.BucketStorageService;
import com.adventure.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplicationContextProvider implements ApplicationContext
{
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
    private StorageService storageService;

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
        catch (IOException ignored)
        {
            ignored.printStackTrace();
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

    public void setGame(Game game)
    {
        Objects.requireNonNull(game, "game cannot be null");
        this.game = game;
    }

    @Override
    public Game getGame()
    {
        return this.game;
    }

    @Override
    public Properties getProperties()
    {
        return this.properties;
    }

    public StorageService getStorageService()
    {
        return this.storageService;
    }

}
