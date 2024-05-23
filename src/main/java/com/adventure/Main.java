package com.adventure;

import com.adventure.commands.CmdClear;
import com.adventure.commands.CmdHelp;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.utils.CommandParser;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        ApplicationContext context = ApplicationContextProvider.getInstance();

        CommandParser commandParser = CommandParser.getInstance();

        commandParser.setContext(context);

        commandParser.registerCommand("help", CmdHelp.class);
        commandParser.registerCommand("clear", CmdClear.class);

        // Loads game by json object.
        context.load("", stage);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}