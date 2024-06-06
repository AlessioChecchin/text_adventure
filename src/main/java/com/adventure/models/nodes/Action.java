package com.adventure.models.nodes;

import com.adventure.serializers.ActionSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents an action used to move in the game graph.
 */
@JsonSerialize(using = ActionSerializer.class)
public class Action
{
    /**
     * Default constructor
     * @param actionName Name of the action
     */
    @JsonCreator
    public Action(@JsonProperty("name") String actionName)
    {
        this.actionName = actionName;
    }

    //
    //  GETTERS.
    //

    /**
     * Name getter
     * @return String name of the action
     */
    public String getActionName() { return this.actionName; }

    //
    //  OTHERS.
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
    public int hashCode()
    {
        return this.actionName.hashCode();
    }

    //
    //  VARIABLES.
    //

    /**
     * Name of the action
     */
    private final String actionName;
}
