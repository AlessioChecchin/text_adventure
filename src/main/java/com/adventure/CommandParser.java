package com.adventure;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CommandParser
{
    private static CommandParser instance;

    private ApplicationContext context;

    private HashMap<String, Class<? extends Command>> lookupTable;

    private CommandParser()
    {
        this.lookupTable = new HashMap<>();
    }

    /**
     * Returns CommandParser instance.
     * @return CommandParser instance.
     */
    public static CommandParser getInstance()
    {
        if(instance == null)
        {
            instance = new CommandParser();
        }

        return instance;
    }

    /**
     * Parses a command.
     * @param command Command to parse.
     * @return The instance of the correct command, null otherwise.
     */
    public Command parseCommand(String command) {
        // Scanner for input string.
        Scanner scanner = new Scanner(command);

        List<String> tokens = new ArrayList<>();

        while(scanner.hasNext())
        {
            tokens.add(scanner.next());
        }

        // Checking that there is at least one token.
        if(tokens.isEmpty())
        {
            return null;
        }

        String key = tokens.get(0);

        // Searching class in the lookup table.
        Class<? extends Command> commandClass = lookupTable.get(key);

        if(commandClass == null)
        {
            return null;
        }

        try
        {
            Command commandInstance = commandClass.getDeclaredConstructor().newInstance();

            // Populating command.
            commandInstance.setContext(this.context);
            tokens.remove(0);
            commandInstance.setArgs(tokens);

            return commandInstance;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Registers a new command.
     * @param key Command name. If a command with the same name is already registered, then is replaced.
     * @param commandClass Command class used to instantiate the concrete object.
     */
    public void registerCommand(String key, Class<? extends Command> commandClass)
    {
        this.lookupTable.put(key, commandClass);
    }

    /**
     * Unregisters a command.
     * @param key Command name to unregister.
     * @return The unregistered command class if the command had been previously registered, null otherwise.
     */
    public Class<? extends Command> unregisterCommand(String key)
    {
        return this.lookupTable.remove(key);
    }

    /**
     * Sets the active context. This context will be injected inside commands.
     * @param context Active context.
     */
    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }

    /**
     * Obtains current context.
     * @return Current context.
     */
    public ApplicationContext getContext()
    {
        return this.context;
    }
}
