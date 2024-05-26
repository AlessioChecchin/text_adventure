package com.adventure.utils;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.commands.Command;

import java.util.*;

public class CommandParser
{
    private static CommandParser instance;

    private ApplicationContext context;

    private final HashMap<String, CommandMetadata> lookupTable;

    private Set<String> enabledCommands;

    private CommandParser()
    {
        this.lookupTable = new HashMap<>();
        this.enabledCommands = new HashSet<>();
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
    public Command parseCommand(String command)
    {
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
        CommandMetadata commandMetadata = lookupTable.get(key);

        if(commandMetadata == null || !this.enabledCommands.contains(key))
        {
            return null;
        }

        Class<? extends Command> commandClass = commandMetadata.getCommandClass();

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
        this.lookupTable.put(key, new CommandMetadata(commandClass));
    }

    /**
     * Registers a new command.
     * @param key Command name. If a command with the same name is already registered, then is replaced.
     * @param commandClass Command class used to instantiate the concrete object.
     * @param description Command description.
     */
    public void registerCommand(String key, Class<? extends Command> commandClass, String description)
    {
        Objects.requireNonNull(description, "Description cannot be null");
        this.lookupTable.put(key, new CommandMetadata(commandClass, description));
    }

    /**
     * Unregisters a command.
     * @param key Command name to unregister.
     * @return The unregistered command class if the command had been previously registered, null otherwise.
     */
    public Class<? extends Command> unregisterCommand(String key)
    {
        CommandMetadata metadata = this.lookupTable.remove(key);
        if(metadata == null) return null;
        return metadata.getCommandClass();
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
     * Enables all commands.
     */
    public void enableAll()
    {
        this.enabledCommands = new HashSet<>(this.lookupTable.keySet());
    }

    /**
     * Disables all commands.
     */
    public void disableAll()
    {
        this.enabledCommands.clear();
    }

    /**
     * Enables command.
     * @param key Command to enable.
     */
    public void enable(String key)
    {
        if(this.lookupTable.containsKey(key))
        {
            this.enabledCommands.add(key);
        }
    }

    /**
     * Disable command.
     * @param key Command to disable.
     */
    public void disable(String key)
    {
        this.enabledCommands.remove(key);
    }

    /**
     * Obtains current context.
     * @return Current context.
     */
    public ApplicationContext getContext()
    {
        return this.context;
    }

    /**
     * Returns available commands.
     * @return Command keys list.
     */
    public List<String> getCommands()
    {
        List<String> commands = new ArrayList<>();

        for(String key: this.lookupTable.keySet())
        {
            if(this.enabledCommands.contains(key))
            {
                commands.add(key);
            }
        }

        return commands;
    }

    /**
     * Returns command description.
     * @param key Command name.
     * @return Command description.
     */
    public String getCommandDescription(String key)
    {
        CommandMetadata metadata = this.lookupTable.get(key);
        if(metadata == null || !this.enabledCommands.contains(key)) return null;
        return metadata.getDescription();
    }

    /**
     * Contains command information.
     */
    protected static class CommandMetadata
    {
        private final Class<? extends Command> commandClass;
        private final String description;

        public CommandMetadata(Class<? extends Command> commandClass, String description)
        {
            this.commandClass = commandClass;
            this.description = description;
        }

        public CommandMetadata(Class<? extends Command> commandClass)
        {
            this.commandClass = commandClass;
            this.description = "";
        }

        public Class<? extends Command> getCommandClass()
        {
            return this.commandClass;
        }

        public String getDescription()
        {
            return this.description;
        }
    }
}
