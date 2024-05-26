package com.adventure.commands;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.utils.CommandParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();

        for(String command: commandParser.getCommands())
        {
            writer.printf("%s:\t%s%n", command, commandParser.getCommandDescription(command));
        }
    }
}
