package com.adventure.commands;

import java.util.Objects;

/**
 * Object that stores metadata for a specific command.
 */
public class CommandMetadata
{
    /**
     * Constructs a new metadata object.
     * @param commandClass Class of the target command.
     * @param description Brief command description.
     */
    public CommandMetadata(Class<? extends Command> commandClass, String description)
    {
        this.commandClass = commandClass;
        this.description = description;
    }

    /**
     * Constructs a new metadata object with an empty description.
     * @param commandClass Class if the target command.
     */
    public CommandMetadata(Class<? extends Command> commandClass)
    {
        this.commandClass = commandClass;
        this.description = "";
    }

    /**
     * Command class getter.
     * @return Target command class.
     */
    public Class<? extends Command> getCommandClass()
    {
        return this.commandClass;
    }

    /**
     * Command description getter.
     * @return Target command description.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Sets the description for the command.
     * @param description New target command description.
     */
    public void setDescription(String description)
    {
        Objects.requireNonNull(description, "description cannot be null");
        this.description = description;
    }

    /**
     * Target command class.
     */
    private final Class<? extends Command> commandClass;

    /**
     * Target command description.
     */
    private String description;
}
