package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.config.ApplicationContext;

import java.util.ArrayList;
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
                this.context.getConfig().getStorageService().deleteGame(gameName);
                writer.println("Deleted game " + gameName);
            }
            catch (NoSuchElementException e)
            {
                writer.println("Error: No such game");
            }
            catch(Exception e)
            {
                writer.println("Error deleting game");
                logger.error("Error deleting game", e);
            }
        }
    }

    /**
     * Get all possible arguments for this command
     * @return all game files
     */
    public static ArrayList<String> args(ApplicationContext context) throws GameStorageException {
        return new ArrayList<>(context.getConfig().getStorageService().listGames());
    }
}
