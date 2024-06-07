package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.services.StorageService;

import java.util.ArrayList;

public class CmdSaveGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        // User has passed no argument
        if(this.getArgs().isEmpty())
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
        else if(this.getArgs().size() == 1)
        {
            Game currentGame = this.context.getGame();
            currentGame.setId(this.getArgs().get(0));
            save(this.context.getConfig().getStorageService(), this.context.getGame());
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
            logger.error("Error saving game: {}", e.getLocalizedMessage());
        }
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
