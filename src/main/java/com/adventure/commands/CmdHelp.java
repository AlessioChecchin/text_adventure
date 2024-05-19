package com.adventure.commands;

import com.adventure.interfaces.ApplicationContext;

import java.util.List;

public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute()
    {
        this.out.println("Command 1");
        this.out.println("Command 2");
        this.out.println("Command 3");
        this.out.println("Command 4");
        this.out.println("Command 5");
        this.out.println("Command 6");
    }
}
