package com.adventure.commands;

import java.util.Objects;

public class CommandMetadata
{
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

    public void setDescription(String description)
    {
        Objects.requireNonNull(description, "description cannot be null");
        this.description = description;
    }

    private final Class<? extends Command> commandClass;
    private String description;
}
