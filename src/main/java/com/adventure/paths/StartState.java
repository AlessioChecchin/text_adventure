package com.adventure.paths;

import com.adventure.ApplicationContext;

public class StartState extends StateNode
{
    public StartState()
    {
        super("Start state");
    }

    public void run(ApplicationContext applicationContext)
    {
        System.out.println("Running " + this.getStateName());
    }
}
