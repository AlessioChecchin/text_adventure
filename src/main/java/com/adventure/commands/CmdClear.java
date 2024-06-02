package com.adventure.commands;

public class CmdClear extends AbstractCommand
{
    @Override
    public void execute()
    {
        writer.flush();
    }
}
