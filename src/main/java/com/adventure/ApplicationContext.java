package com.adventure;

import com.adventure.paths.StateNode;

import java.lang.reflect.InvocationTargetException;

public interface ApplicationContext
{
    void load(Class<? extends StateNode> stateNode) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    public StateNode nextNode();
}
