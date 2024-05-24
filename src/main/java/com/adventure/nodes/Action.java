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
    //  VARIABLES
    //

    /**
     * Name of the action
     */
    private final String actionName;
}
