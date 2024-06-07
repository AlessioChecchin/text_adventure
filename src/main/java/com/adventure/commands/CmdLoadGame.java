package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.config.ApplicationContext;
import javafx.application.Platform;

import java.util.ArrayList;

public class CmdLoadGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(this.correctArgumentsNumber(1))
        {
            String gameName = this.getArgs().get(0);
            Game loadedGame;
            try {
                loadedGame = this.context.getConfig().getStorageService().loadGame(gameName);
            } catch (GameStorageException e) {
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
    }

    /**
     * Get all possible arguments for this command
     * @return all possible game files
     */
    public static ArrayList<String> args(ApplicationContext context) throws GameStorageException {
        return new ArrayList<>(context.getConfig().getStorageService().listGames());
    }
}
