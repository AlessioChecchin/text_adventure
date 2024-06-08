package com.adventure.models.nodes;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

/**
 * Class that represents a node in the game graph.
 * It's a fundamental object to represent game state.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class, name = "Room"),
        @JsonSubTypes.Type(value = VictoryNode.class, name = "VictoryNode")
})
public abstract class StoryNode
{
    /**
     * Constructor.
     * @param targetView Node target view.
     */
    public StoryNode(String targetView)
    {
        Objects.requireNonNull(targetView, "targetView can't be null");
        this.targetView = targetView;
    }

    //
    //  GETTERS
    //

    /**
     * Get the current view
     * @return String current view
     */
    public String getTargetView()
    {
        return this.targetView;
    }

    /**
     * Get the String ID of the object
     * @return String ID of the object
     */
    @JsonProperty("ID")
    public String getID()
    {
        return this.ID;
    }

    /**
     * Get the numeric ID of the object
     * @return int numeric ID of the object
     */
    public int getNumericID()
    {
        return this.numericID;
    }

    //
    // SETTERS
    //

    public void setTargetView(String targetView)
    {
        this.targetView = targetView;
    }

    /**
     * Sets the numeric ID and the String ID for the class
     * @param ID int id of the current object
     */
    protected void setID(int ID)
    {
        IdManager idManager = IdManager.getInstance();
        idManager.check(ID);
        // Set node numeric ID
        this.numericID = ID;
        // Set node String ID
        String[] classpath = this.getClass().getName().split("\\.");
        this.ID = classpath[classpath.length - 1] + "_" + ID;
    }

    /**
     * Sets the numeric ID and the String ID for the class
     * @apiNote the ID is set using the IdManager
     */
    protected void setID()
    {
        IdManager idManager = IdManager.getInstance();
        setID(idManager.getNext());
    }

    //
    //  VARIABLES
    //

    /**
     * Represents the view to be used
     */
    private String targetView;
    private int numericID;
    private String ID;

}
