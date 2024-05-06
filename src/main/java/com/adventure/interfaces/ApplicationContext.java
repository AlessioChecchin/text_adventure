package com.adventure.interfaces;

import com.adventure.nodes.StoryNode;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public interface ApplicationContext
{
    ApplicationContext load(Class<? extends StoryNode> stateNode) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    public void setStage(Stage stage);
    public Stage getStage();
    public void activate() throws Exception;
}
