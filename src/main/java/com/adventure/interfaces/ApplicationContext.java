package com.adventure.interfaces;

import com.adventure.models.Game;
import com.adventure.nodes.StoryNode;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public interface ApplicationContext
{
    void load(String json, Stage stage);
    void load(Game game, Stage stage);
    public Game getGame();
}
