package com.adventure.commands;

import com.adventure.interfaces.ApplicationContext;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command
{
    protected PrintStream out;

    protected List<String> args;

    protected ApplicationContext context;

    public AbstractCommand()
    {
        this.out = System.out;
    }

    @Override
    public void setOutputPipe(PrintStream out)
    {
        this.out = out;
    }

    @Override
    public PrintStream getOutputPipe()
    {
        return this.out;
    }

    @Override
    public void setArgs(List<String> args)
    {
        this.args = new ArrayList<>(args);
    }

    @Override
    public List<String> getArgs()
    {
        return new ArrayList<>(this.args);
    }

    @Override
    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }

    @Override
    public ApplicationContext getContext() {
        return this.context;
    }
}
