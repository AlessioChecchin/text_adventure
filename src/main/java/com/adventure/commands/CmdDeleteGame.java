package com.adventure.commands;

import java.util.NoSuchElementException;

public class CmdDeleteGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(this.correctArgumentsNumber(1))
        {
            //  Delete game
            String gameName = this.getArgs().get(0);
            try {
                this.context.getStorageService().deleteGame(gameName);
                writer.println("Deleted game " + gameName);
            } catch (NoSuchElementException e) {
                writer.println("Error: No such game");
            }
        }
    }
}
