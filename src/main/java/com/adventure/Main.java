package com.adventure;

import com.adventure.commands.*;
import com.adventure.models.Game;
import com.adventure.nodes.GameLoaderNode;
import com.adventure.storage.BucketStorageService;
import com.adventure.storage.StorageService;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.utils.CommandParser;
import javafx.application.Application;
import javafx.stage.Stage;
import software.amazon.awssdk.services.ec2.model.Storage;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContext context = ApplicationContextProvider.getInstance();

        commandParser.setContext(context);

        commandParser.registerCommand("help", CmdHelp.class, "Displays available commands with brief descriptions");
        commandParser.registerCommand("clear", CmdClear.class, "Clears the screen.");
        commandParser.registerCommand("listGames", CmdListGames.class, "Lists all available games.");
        commandParser.registerCommand("save", CmdSaveGame.class, "Saves the current game.");
        commandParser.registerCommand("newGame", CmdNewGame.class, "Creates a new game.");

        // Generating dummy game to host the proper game loader.
        Game dummyGame = new Game(context.getProperties(), stage);
        context.setGame(dummyGame);

        dummyGame.setCurrentNode(new GameLoaderNode());
        dummyGame.load();

        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}