package com.adventure.nodes;

import com.adventure.interfaces.ApplicationContext;

public abstract class StoryNode {
    public abstract void run(ApplicationContext applicationContext) throws Exception;
}
