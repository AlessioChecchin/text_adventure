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
        if (!this.correctArgumentsNumber(0)) { return; }

        writer.flush();
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
