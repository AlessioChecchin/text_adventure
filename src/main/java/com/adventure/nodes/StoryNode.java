package com.adventure.nodes;

import com.adventure.interfaces.ApplicationContext;

public abstract class StoryNode
{
    private String targetView;

    public void setTargetView(String targetView)
    {
        this.targetView = targetView;
    }

    public String getTargetView()
    {
        return this.targetView;
    }

}
