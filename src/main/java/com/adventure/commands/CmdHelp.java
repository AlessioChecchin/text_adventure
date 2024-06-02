package com.adventure.commands;

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
