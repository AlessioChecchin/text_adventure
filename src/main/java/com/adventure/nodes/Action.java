package com.adventure.nodes;

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

    @Override
    public boolean equals(Object action) {
        if (action == this) {
            return true;
        }

        if (action.getClass() != this.getClass()) return false;

        Action act = (Action) action;
        return this.getActionName().equals(act.getActionName());
    }

    @Override
    public int hashCode() {
        return this.actionName.hashCode();
    }

    private final String actionName;
}
