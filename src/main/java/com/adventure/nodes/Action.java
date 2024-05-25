package com.adventure.nodes;

import com.adventure.serializers.ActionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ActionSerializer.class)
public class Action
{
    //
    //  CONSTRUCTOR
    //

    /**
     * Default constructor
     * @param actionName Name of the action
     */
    public Action(String actionName)
    {
        this.actionName = actionName;
    }

    //
    //  GETTERS
    //

    /**
     * Name getter
     * @return String name of the action
     */
    public String getActionName() { return this.actionName; }



    //
    //  OTHERS
    //

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



    //
    //  VARIABLES
    //

    /**
     * Name of the action
     */
    private final String actionName;
}
