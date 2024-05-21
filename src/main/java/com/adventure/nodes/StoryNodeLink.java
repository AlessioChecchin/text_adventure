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

    @Override
    public boolean equals(Object edge){
        if (edge == this) {
            return true;
        }

        if (edge.getClass() != this.getClass()) return false;

        StoryNodeLink link = (StoryNodeLink) edge;
        return (this.getSource().equals(link.getSource()) && (this.getTarget().equals(link.getTarget())) && (this.getAction().equals(link.getAction())));
    }
}
