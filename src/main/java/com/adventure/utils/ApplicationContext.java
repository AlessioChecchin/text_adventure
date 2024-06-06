package com.adventure.utils;

import com.adventure.models.Game;
import com.adventure.services.StorageService;

import java.util.Properties;

/**
 * Interface for application context.
 */
public interface ApplicationContext
{
    /**
     * Game setter.
     * @param game Game.
     */
    void setGame(Game game);

    /**
     * Game getter.
     * @return Game
     */
    Game getGame();

    /**
     * Application properties getter.
     * @return Application properties.
     */
    Properties getProperties();

    /**
     * Storage service getter.
     * @return Storage service.
     */
    StorageService getStorageService();
}
