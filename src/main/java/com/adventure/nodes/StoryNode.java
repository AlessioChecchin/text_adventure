package com.adventure.nodes;

import com.adventure.interfaces.ApplicationContext;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class, name = "Room")
})
public abstract class StoryNode
{
    private String targetView;

    public void setTargetView(String targetView)
    {
        this.targetView = targetView;
    }

    public String getTargetView() {
        return this.targetView;
    }

}
