package com.adventure.commands;

public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {
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

            writer.printf("%s%s : %s%n", command, " ".repeat(padding), commandParser.getCommandDescription(command));
        }
    }
}
