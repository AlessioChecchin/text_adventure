package com.adventure.commands;

import java.util.List;

public class CmdListGames extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        List<String> games = this.context.getStorageService().listGames();
        if(games.isEmpty())
            writer.print("No game saved");

        for(String game : this.context.getStorageService().listGames())
            writer.println(game);

    }
}
