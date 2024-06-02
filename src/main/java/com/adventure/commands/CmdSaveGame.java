package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.services.StorageService;

public class CmdSaveGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        Game currentGame = this.context.getGame();


        if(currentGame.getId() == null)
        {
            writer.print("Enter game name: ");
            String id = this.safeReadNext();
            writer.flush();

            currentGame.setId(id);
        }

        StorageService storageService = this.context.getStorageService();

        try
        {
            storageService.saveGame(currentGame);
            writer.println("Game saved!");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            logger.error("Error saving game: {}", e.getLocalizedMessage());
            writer.println("Error saving game: " + e.getMessage());
        }
    }
}
