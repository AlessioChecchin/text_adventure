package com.adventure.nodes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class, name = "Room")
})
public abstract class StoryNode
{
    //
    //  CONSTRUCTOR
    //

    public StoryNode( String targetView)
    {
        Objects.requireNonNull(targetView, "targetView can't be null");
        this.targetView = targetView;
    }



    //
    //  GETTERS
    //

    /**
     * Get the ID of the object
     * @return String ID of the object
     */
    public String getID() { return ""+System.identityHashCode(this); }

    /**
     * Get the current view
     * @return String current view
     */
    public String getTargetView() { return this.targetView; }



    //
    //  SETTERS
    //

    /**
     * Set the current view
     * @param targetView current view
     */
    public void setTargetView(String targetView) { this.targetView = targetView; }



    //
    //  VARIABLES
    //

    /**
     * Represents the view to be used
     */
    private String targetView;
}
