package com.adventure.commands;

import com.adventure.models.Game;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to create a new game.
 */
public class CmdNewGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Invalid number of arguments! Usage: newGame");
            return;
        }

        // Obtains the number of nodes loaded in the game.
        int n = this.context.getGame().getGameGraph().vertexSet().size();

        // n = 0:
        //      In this case the GameLoaderNode (detached from the graph) is loaded.
        //      A new game can be created without asking user consent (no data will be lost)
        //
        // n != 0:
        //      A valid game is loaded, confirmation required.
        if(n != 0 && n != 1)
        {
            this.writer.println("Do you really want to create a new game? Current unsaved progress will be lost! [yes/no]");

            // If the user says no, then the command terminates.
            if(!this.askConfirmation())
            {
                this.writer.flush();
                this.writer.println("No game was created.");
                return;
            }
        }

        this.writer.println("Enter player name: ");
        //  Disable all commands (so that the user must insert the name)
        disableSaveAll();
        // If we reach this code, then the user answered yes.
        String username = this.safeReadNextLine();
        //  Re-enable previously disabled commands
        reEnableSaved();
        logger.debug("Switching game...");

        Game game = this.context.getConfig().getStorageService().newGame(username);

        Platform.runLater(() -> {
            // Forwards the stage to the new game.
            game.setStage(this.context.getGame().getStage());

            this.context.setGame(game);
            game.load();
            game.getPlayer().setName(username);
        });

    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
