package com.adventure.interfaces;

import com.adventure.models.Game;
import com.adventure.nodes.StoryNode;
import com.adventure.storage.StorageService;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public interface ApplicationContext
{
    public void setGame(Game game);
    public Game getGame();
    public Properties getProperties();
    public StorageService getStorageService();
}
