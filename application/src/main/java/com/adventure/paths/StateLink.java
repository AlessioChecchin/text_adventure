package com.adventure.paths;

import org.jgrapht.graph.DefaultEdge;

public class StateLink extends DefaultEdge
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
