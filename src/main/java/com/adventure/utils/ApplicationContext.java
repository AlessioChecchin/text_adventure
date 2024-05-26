package com.adventure.utils;

import com.adventure.models.Game;
import com.adventure.services.StorageService;

import java.util.Properties;

public interface ApplicationContext
{
    public void setGame(Game game);
    public Game getGame();
    public Properties getProperties();
    public StorageService getStorageService();
}
