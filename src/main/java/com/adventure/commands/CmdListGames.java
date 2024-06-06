package com.adventure.commands;

import java.util.List;

public class CmdListGames extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        try
        {
            List<String> games = this.context.getStorageService().listGames();
            if(games.isEmpty())
                writer.print("No game saved");

            for(String game : this.context.getStorageService().listGames())
                writer.println(game);
        }
        catch (Exception e)
        {
            this.writer.print("Error listing games");
            logger.error("Error listing games", e);
        }


    }
}
