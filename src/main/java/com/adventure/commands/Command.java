package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.GameStorageException;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public interface Command
{
    /**
     * Gets a copy of the argument list.
     * @return A copy of the argument list.
     */
    List<String> getArgs();

    /**
     * Get a list of all the available argument for this command (necessary for command prediction)
     * @return ArrayList with all the possible arguments
     * @throws GameStorageException
     */
    ArrayList<String> getPossibleArgs() throws GameStorageException;

    /**
     * Sets command arguments.
     * @param args Arguments.
     */
    void setArgs(List<String> args);

    /**
     * Gets command context.
     * @return Application context.
     */
    ApplicationContext getContext();

    /**
     * Gets current stdout writer.
     * @return Stdout writer.
     */
    Writer getWriter();

    /**
     * Returns command stdin.
     * @return Input stream.
     */
    InputStream getInputStream();

    /**
     * Sets command context.
     * @param context Application context.
     */
    void setContext(ApplicationContext context);

    /**
     * Sets stdout writer for command.
     * @param out Output writer.
     */
    void setWriter(Writer out);

    /**
     * Sets command stdin.
     * @param inputStream Input stream.
     */
    void setInputStream(InputStream inputStream);

    /**
     * Executes the command.
     * @throws InterruptedException Thrown if the command is interrupted (e.g. when kill is well implemented).
     */
    void execute() throws InterruptedException;

    /**
     * Requests command termination. This ONLY asks the command to terminate, but
     * termination depends on the correct implementation of the command.
     * If the command was executing then it will throw InterruptedException.
     */
    void kill();
}
