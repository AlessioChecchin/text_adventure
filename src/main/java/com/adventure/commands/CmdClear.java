package com.adventure.commands;

import java.util.ArrayList;

/**
 * Clears the console.
 */
public class CmdClear extends AbstractCommand
{
    @Override
    public void execute()
    {
        writer.flush();
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
