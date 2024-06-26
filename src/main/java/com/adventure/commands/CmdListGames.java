package com.adventure.commands;

import com.adventure.services.StorageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to list available games.
 */
public class CmdListGames extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Incorrect number of arguments! Usage: listGames");
            return;
        }

        try
        {
            StorageService storageService = this.context.getConfig().getStorageService();

            List<String> games = storageService.listGames();
            if(games.isEmpty())
                writer.print("No game saved");

            for(String game : games)
                writer.println(game);
        }
        catch (Exception e)
        {
            this.writer.print("Error listing games");
            logger.error("Error listing games", e);
        }


    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
