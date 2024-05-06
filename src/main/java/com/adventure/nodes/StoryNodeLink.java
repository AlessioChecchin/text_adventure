package com.adventure.nodes;

import org.jgrapht.graph.DefaultEdge;

public class StoryNodeLink extends DefaultEdge
{
    public void setAction(Action switchAction)
    {
        this.action = switchAction;
    }

    public Action getAction()
    {
        return this.action;
    }

    private Action action;
}
