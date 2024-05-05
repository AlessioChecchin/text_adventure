package com.adventure.paths;

import com.adventure.ApplicationContext;
import com.adventure.DefaultApplicationContext;

public abstract class StateNode
{
    public StateNode(String stateName)
    {
        this.stateName = stateName;
    }

    public String getStateName()
    {
        return this.stateName;
    }

    public String toString()
    {
        return this.stateName;
    }

    public abstract void run(ApplicationContext applicationContext);

    private final String stateName;
}
