package com.adventure.commands;

import com.adventure.interfaces.ApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class CmdHelp extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException {
        writer.println("Command 1");

        String str = this.safeReadNext();

        writer.println("You entered " + str);
        writer.println("Command 3");
        writer.println("Command 4");
        writer.println("Command 5");
        writer.println("Command 6");
    }
}
