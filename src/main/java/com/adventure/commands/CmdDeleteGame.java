package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Command used to delete an existing game.
 */
public class CmdDeleteGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        if(!this.correctArgumentsNumber(1))
        {
            this.writer.println("Invalid number of arguments! Usage: delete <game identifier>");
            return;
        }

        //  Delete game
        String gameName = this.getArgs().get(0);
        try
        {
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

    /**
     * @return all possible game files
     */
    public List<String> getPossibleArgs() throws GameStorageException
    {
        return new ArrayList<>(context.getConfig().getStorageService().listGames());
    }

}
