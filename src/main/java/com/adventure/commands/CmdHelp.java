package com.adventure.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to display possible command with descriptions.
 */
public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {

        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Incorrect number of arguments! Usage: help");
            return;
        }

        CommandParser commandParser = CommandParser.getInstance();

        int maxLength = 0;

        for(String command: commandParser.getCommands())
        {
            if(command.length() > maxLength)
            {
                maxLength = command.length();
            }
        }

        for(String command: commandParser.getCommands())
        {
            int padding = maxLength - command.length();

            writer.printf("%s%s : %s%n", command, " ".repeat(padding), commandParser.getCommandMetadata(command).getDescription());
        }
    }

    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
