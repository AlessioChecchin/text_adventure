package com.adventure.models.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jgrapht.graph.DefaultEdge;

import java.util.Objects;

public class StoryNodeLink extends DefaultEdge
{

    public StoryNodeLink()
    {
        super();
        this.action = new Action("");
        this.key = "";
        this.locked = false;
    }

    //
    //  GETTERS
    //

    /**
     * ID getter
     * @return String ID of the edge
     */
    public String getID()
    {
        return "edge_" + System.identityHashCode(this);
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
        this.locked = true;
        this.key = key;
    }

    //
    // DEFAULT OVERRIDES.
    //

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
}
