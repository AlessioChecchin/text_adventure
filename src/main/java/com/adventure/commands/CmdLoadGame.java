package com.adventure.commands;

public class CmdLoadGame extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {

        writer.println("Game loaded");
    }
}
