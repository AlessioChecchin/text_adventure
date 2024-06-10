package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * Class responsible for parsing and instantiating commands.
 */
public class CommandParser
{
    /**
     * Constructs a new command parser.
     */
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
            LogManager.getLogger().error(e);
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
     * It gives a list of all the possible arguments for a command given its name
     * @param key String name of the command
     * @return ArrayList of Strings with all the possible arguments for this command
     */
    public List<String> argsFromCommand(String key)
    {
        CommandMetadata metadata = this.lookupTable.get(key);
        if(metadata != null)
            try {
                // Get command instance and set the context
                Command cmdInstance = metadata.getCommandClass().getDeclaredConstructor().newInstance();
                cmdInstance.setContext(this.context);
                // Get the possible arguments for the command
                return cmdInstance.getPossibleArgs();
            } catch (NoSuchMethodException e) {
                //  If method not found it means the command has no arguments -> fails silently
                return new ArrayList<>();
            } catch(Exception e) {
                LogManager.getLogger().error(e);
            }
        return new ArrayList<>();
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
        this.enabledCommands.remove(key);
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
     * Enables all commands in the list.
     */
    public void enableAll(List<String> commands)
    {
        for(String command : commands)
        {
            this.enable(command);
        }
    }

    /**
     * Disables all commands.
     * @return The commands that were enabled.
     */
    public List<String> disableAll()
    {
        List<String> previouslyEnabled = this.getCommands();
        this.enabledCommands.clear();
        return previouslyEnabled;
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

        // Alphabetically order the commands.
        Collections.sort(commands);

        return commands;
    }

    /**
     * Returns command description.
     * @param key Command name.
     * @return Command description.
     */
    public CommandMetadata getCommandMetadata(String key)
    {
        CommandMetadata metadata = this.lookupTable.get(key);
        if(metadata == null || !this.enabledCommands.contains(key)) return null;

        return metadata;
    }

    /**
     * Singleton instance.
     */
    private static CommandParser instance;

    /**
     * Application context.
     */
    private ApplicationContext context;

    /**
     * Lookup table used to search register commands.
     */
    private final HashMap<String, CommandMetadata> lookupTable;

    /**
     * Set used to check which commands are enabled.
     */
    private Set<String> enabledCommands;
}
