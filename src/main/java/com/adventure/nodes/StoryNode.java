package com.adventure.nodes;

import com.adventure.interfaces.ApplicationContext;

import java.util.Objects;

public abstract class StoryNode
{
    private String targetView;

    public StoryNode( String targetView){
        Objects.requireNonNull(targetView, "targetView can't be null");
        this.targetView = targetView;
    }

    public void setTargetView(String targetView)
    {
        this.targetView = targetView;
    }

    public String getTargetView()
    {
        return this.targetView;
    }
}
