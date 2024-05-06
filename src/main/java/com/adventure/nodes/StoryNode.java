package com.adventure.nodes;

import com.adventure.interfaces.ApplicationContext;

public abstract class StoryNode
{
    public StoryNode(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getNodeName()
    {
        return this.nodeName;
    }

    public String toString()
    {
        return this.nodeName;
    }

    public abstract void run(ApplicationContext applicationContext) throws Exception;

    private final String nodeName;
}
