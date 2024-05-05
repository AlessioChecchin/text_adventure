package com.adventure.paths;

public class Action
{
    public Action(String actionName)
    {
        this.actionName = actionName;
    }

    public String getActionName()
    {
        return this.actionName;
    }

    private final String actionName;
}
