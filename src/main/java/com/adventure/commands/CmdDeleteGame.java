package com.adventure.commands;

import java.util.NoSuchElementException;

public class CmdDeleteGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        //  Checks syntax
        if(this.getArgs().size() == 0)
            writer.println("Too few arguments for command \"delete\"");
        else if(this.getArgs().size() > 1)
            writer.println("Too many arguments for command \"delete\"");
        else {

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
