package com.adventure;

import com.adventure.paths.StartState;

import java.lang.reflect.InvocationTargetException;

public final class Main
{

    /**
     * Main demo entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        DefaultApplicationContext applicationContext = DefaultApplicationContext.getInstance();
        applicationContext.load(StartState.class);
        while(applicationContext.nextNode() != null) {}
    }
}