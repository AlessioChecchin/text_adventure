package com.adventure.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CmdClear extends AbstractCommand
{
    @Override
    public void execute()
    {
        writer.write("");
    }
}
