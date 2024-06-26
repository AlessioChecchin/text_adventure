package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to load a new game.
 */
public class CmdLoadGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(1))
        {
            this.writer.println("Invalid number of arguments! Usage: loadGame <game identifier>");
            return;
        }

        String gameName = this.getArgs().get(0);
        Game loadedGame;
        try
        {
            loadedGame = this.context.getConfig().getStorageService().loadGame(gameName);
        }
        catch (GameStorageException e)
        {
            logger.error("Error loading game {}", gameName);
            writer.println("Error loading game " + gameName);
            return;
        }

        Platform.runLater(() -> {
            // Forwards the stage to the new game.
            loadedGame.setStage(this.context.getGame().getStage());

            this.context.setGame(loadedGame);
            loadedGame.load();
        });

        writer.println("Game loaded");
    }

    /**
     * @return all possible game files
     */
    public List<String> getPossibleArgs() throws GameStorageException
    {
        return new ArrayList<>(context.getConfig().getStorageService().listGames());
    }
}
