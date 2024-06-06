package com.adventure.config;

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
     * Gets current application config.
     * @return Current application config.
     */
    Config getConfig();
}
