package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import javafx.application.Platform;

import java.util.NoSuchElementException;

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
                loadedGame = this.context.getStorageService().loadGame(gameName);
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
}
