package com.adventure.commands;


public class CmdListGames extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        writer.println(this.context.getStorageService().listGames());
    }
}
