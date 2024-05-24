package com.adventure.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jgrapht.graph.DefaultEdge;

public class StoryNodeLink extends DefaultEdge
{

    //
    //  GETTERS
    //

    /**
     * ID getter
     * @return String ID of the edge
     */
    @JsonIgnore
    public String getID() { return "edge_" + System.identityHashCode(this); }

    /**
     * Action getter
     * @return Action linked with this edge
     */
    public Action getAction() { return this.action; }


    //
    //  SETTERS
    //

    /**
     * Link another action to this edge
     * @param switchAction new action to link to the edge
     */
    public void setAction(Action switchAction) { this.action = switchAction; }



    //
    //  VARIABLES
    //

    /**
     * Action linked to this edge
     */
    private Action action;
}
