package com.adventure.models.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jgrapht.graph.DefaultEdge;

import java.util.Objects;

/**
 * This class represents a game graph edge.
 * It connects two story-nodes and represents a possible path for a player.
 */
public class StoryNodeLink extends DefaultEdge
{
    /**
     * Default constructor
     */
    public StoryNodeLink()
    {
        super();
        this.action = new Action("");
        this.key = "";
        this.locked = false;
        this.setID();
    }


    /**
     * Constructor for deserialization
     * @param id id to set when creating the edge
     */
    public StoryNodeLink(@JsonProperty("numericID") int id)
    {
        super();
        this.action = new Action("");
        this.key = "";
        this.locked = false;
        this.setID(id);
    }


    //
    //  GETTERS
    //

    /**
     * Get String ID of the edge
     * @return String ID of the edge
     */
    @JsonProperty("ID")
    public String getID()
    {
        return this.ID;
    }

    /**
     * Get int ID of the edge
     * @return int ID of the edge
     */
    public int getNumericID()
    {
        return this.numericID;
    }

    /**
     * Locked getter.
     * @return Locked flag.
     */
    public boolean getLocked()
    {
        return this.locked;
    }

    /**
     * Key getter.
     * @return Key.
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * Action getter
     * @return Action linked with this edge
     */
    public Action getAction()
    {
        return this.action;
    }


    //
    //  SETTERS
    //

    /**
     * Set numeric and stringID with IdManager given an int ID
     * @param ID int ID to set for the edge
     */
    public void setID(int ID)
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
     * Set numericID and stringID with IdManager
     */
    public void setID()
    {
        IdManager idManager = IdManager.getInstance();
        setID(idManager.getNext());
    }

    /**
     * Link another action to this edge
     * @param switchAction new action to link to the edge
     */
    public void setAction(Action switchAction)
    {
        Objects.requireNonNull(switchAction, "switchAction cant be null");
        this.action = switchAction;
    }

    /**
     * Sets flag locked.
     * @param locked Flag value.
     */
    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }

    /**
     * Key setter. If a key is set, then the edge is automatically locked.
     * @param key key.
     */
    public void setKey(String key)
    {
        Objects.requireNonNull(key, "key can't be null");
        this.locked = true;
        this.key = key;
    }

    //
    // OTHERS.
    //

    /**
     * Tries to unlock the link.
     * @param key Key used to unlock.
     * @return True if the link was unlocked successfully, false otherwise.
     */
    public boolean tryUnlock(String key)
    {
        if(key == null) return false;
        if(key.equals(this.key))
        {
            this.locked = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object edge)
    {
        if (edge == this)
        {
            return true;
        }

        if (edge.getClass() != this.getClass()) return false;

        StoryNodeLink link = (StoryNodeLink) edge;
        return (this.getSource().equals(link.getSource()) && (this.getTarget().equals(link.getTarget())) && (this.getAction().equals(link.getAction())));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(action, locked, key);
    }

    //
    //  VARIABLES
    //

    /**
     * Action linked to this edge
     */
    private Action action;

    /**
     * Link locked flag.
     */
    private boolean locked;

    /**
     * Key value to unlock the link.
     */
    private String key;

    /**
     * Numeric ID for the edge
     */
    private int numericID;
    /**
     * String ID for the edge
     */
    private String ID;
}
