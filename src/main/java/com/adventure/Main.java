package com.adventure;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.nodes.StartNode;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        ApplicationContext context = ApplicationContextProvider.getInstance();
        stage.setTitle("App Title");

        context.setStage(stage);
        context.load(StartNode.class).activate();

        stage.show();
    }

    public static void main(String[] args) { launch(); }
}