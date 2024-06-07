package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;

import java.util.ArrayList;

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
