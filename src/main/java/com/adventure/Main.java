package com.adventure;

import com.adventure.interfaces.ApplicationContext;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        ApplicationContext context = ApplicationContextProvider.getInstance();

        // Loads game by json object.
        context.load("", stage);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}