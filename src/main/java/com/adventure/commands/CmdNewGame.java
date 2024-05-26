package com.adventure.commands;

import com.adventure.models.Game;

public class CmdNewGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        // Obtains the number of nodes loaded in the game.
        int n = this.context.getGame().getGameGraph().vertexSet().size();

        if(n == 0)
        {
            System.out.println("Automatically creating the game");
            // In this case the GameLoaderNode (detached from the graph) is loaded.
            // A new game can be created without asking user consent (no data will be lost)
        }
        else
        {
            // If the user says no, then the command terminates.
            if(!this.askConfirmation())
            {
                return;
            }
        }


        this.writer.println("Enter player name: ");

        // If we reach this code, then the user answered yes.
        String username = this.safeReadNextLine();

        Game game = this.context.getStorageService().newGame();

        // Forwards the stage to the new game.
        game.setStage(this.context.getGame().getStage());

        this.context.setGame(game);
        game.load();
    }
}
