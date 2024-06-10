package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to run away from a fight.
 */
public class CmdRun extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {

        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Invalid number of arguments! Usage: run");
            return;
        }

        this.context.getGame().getPlayer().setFightingStatus(false);
        this.writer.println("You run away from the battle");
    }

    @Override
    public List<String> getPossibleArgs() throws GameStorageException
    {
        return new ArrayList<>();
    }
}
