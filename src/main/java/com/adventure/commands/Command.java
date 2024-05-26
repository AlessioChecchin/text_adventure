package com.adventure.commands;

import com.adventure.utils.ApplicationContext;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

public interface Command
{
    /**
     * Sets command arguments.
     * @param args Arguments.
     */
    void setArgs(List<String> args);

    /**
     * Gets a copy of the argument list.
     * @return A copy of the argument list.
     */
    List<String> getArgs();

    /**
     * Sets command context.
     * @param context Application context.
     */
    void setContext(ApplicationContext context);

    /**
     * Gets command context.
     * @return Application context.
     */
    ApplicationContext getContext();

    /**
     * Executes the command.
     * @throws InterruptedException Thrown if the command is interrupted (eg. when kill is well implemented).
     */
    void execute() throws InterruptedException;

    /**
     * Sets stdout writer for command.
     * @param out Output writer.
     */
    void setWriter(Writer out);

    /**
     * Gets current stdout writer.
     * @return Stdout writer.
     */
    Writer getWriter();

    /**
     * Sets command stdin.
     * @param inputStream Input stream.
     */
    void setInputStream(InputStream inputStream);

    /**
     * Returns command stdin.
     * @return Input stream.
     */
    InputStream getInputStream();

    /**
     * Requests command termination. This ONLY asks the command to terminate, but
     * termination depends on the correct implementation of the command.
     * If the command was executing then it will throw InterruptedException.
     */
    void kill();
}
