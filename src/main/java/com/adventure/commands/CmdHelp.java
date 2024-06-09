package com.adventure.commands;

import java.util.ArrayList;

/**
 * Command used to display possible command with descriptions.
 */
public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {

        if (!this.correctArgumentsNumber(0)) { return; }

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

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
