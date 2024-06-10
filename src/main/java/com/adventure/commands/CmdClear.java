package com.adventure.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Clears the console.
 */
public class CmdClear extends AbstractCommand
{
    @Override
    public void execute()
    {
        if (!this.correctArgumentsNumber(0))
        {
            System.out.println("Invalid number of arguments! Usage: clear");
            return;
        }

        writer.flush();
    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
