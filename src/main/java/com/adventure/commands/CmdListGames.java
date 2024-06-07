package com.adventure.commands;

import com.adventure.services.StorageService;

import java.util.ArrayList;
import java.util.List;

public class CmdListGames extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
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

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
