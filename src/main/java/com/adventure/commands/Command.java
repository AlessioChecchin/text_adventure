package com.adventure.commands;

import com.adventure.interfaces.ApplicationContext;

import java.io.PrintStream;
import java.util.List;

public interface Command
{
    void setArgs(List<String> args);
    List<String> getArgs();
    void setContext(ApplicationContext context);
    ApplicationContext getContext();
    void execute();
    void setOutputPipe(PrintStream out);
    PrintStream getOutputPipe();
}
