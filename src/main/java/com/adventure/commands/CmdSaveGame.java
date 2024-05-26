package com.adventure.commands;

import com.adventure.models.Game;
import com.adventure.storage.StorageService;

public class CmdSaveGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        Game currentGame = this.context.getGame();
        StorageService storageService = this.context.getStorageService();

        if(currentGame.getId() == null)
        {
            writer.print("Enter game name: ");
            String id = this.safeReadNext();
            writer.flush();

            currentGame.setId(id);
        }

        try
        {
            storageService.saveGame(currentGame);
            writer.println("Game saved!");
        }
        catch(Exception e)
        {
            writer.println("Error saving game: " + e.getMessage());
        }
    }
}
