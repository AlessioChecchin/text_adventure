package com.adventure.commands;

import java.util.ArrayList;

/**
 * Command that does nothing
 * Only to be used when you want "dummy" command to appear as enabled (e.g. TextInput, y/n ...)
 */
public class DummyCmd extends AbstractCommand
{
    /**
     * Dummy execution method.
     * @throws InterruptedException Never thrown.
     */
    @Override
    public void execute() throws InterruptedException {}

    /**
     * Dummy arguments.
     * @return Empty list.
     */
    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
