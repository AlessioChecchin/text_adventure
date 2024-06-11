package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.services.StorageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to save the current game.
 */
public class CmdSaveGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        // User has passed no argument
        if(this.correctArgumentsNumber(0))
        {
            Game currentGame = this.context.getGame();

            // If the game has no name, the user must enter it
            // Otherwise the saving can continue
            if (currentGame.getId() == null)
            {
                writer.print("Enter game name: ");

                //  User MUST insert save name
                this.disableSaveAll();
                String id = this.safeReadNext();
                this.reEnableSaved();
                writer.flush();

                currentGame.setId(id);
            }

            // Saving the game
            save(this.context.getConfig().getStorageService(), currentGame);
        }
        // User specifies the name of the game. If the game had another name, now it is overwritten
        else if(this.correctArgumentsNumber(1))
        {
            Game currentGame = this.context.getGame();
            currentGame.setId(this.getArgs().get(0));
            save(this.context.getConfig().getStorageService(), this.context.getGame());
        }
        else
        {
            this.writer.println("Invalid number of arguments! Usage: show <opt: inventory, opt: stats>");
        }
    }

    /**
     * Try to save the game
     * @param storageService
     * @param currentGame
     */
    private void save(StorageService storageService, Game currentGame)
    {
        try
        {
            storageService.saveGame(currentGame);
            writer.println("Game saved!");
        }
        catch (Exception e)
        {
            writer.println("Error saving game");
            logger.error("Error saving game", e);
        }
    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
