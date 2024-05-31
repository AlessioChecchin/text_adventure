package com.adventure.commands;

import com.adventure.utils.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractCommand implements Command
{
    protected PrintWriter writer;

    protected InputStream inputStream;

    protected List<String> args;

    protected ApplicationContext context;

    protected boolean shouldTerminate;

    public static final int BUSY_WAITING_QUANTUM = 10;

    protected static final Logger logger = LogManager.getLogger();

    public AbstractCommand()
    {
        this.writer = new PrintWriter(System.out);
        this.inputStream = System.in;
        this.shouldTerminate = false;
    }

    @Override
    public void setWriter(Writer out)
    {
        this.writer = new PrintWriter(out);
    }

    @Override
    public Writer getWriter()
    {
        return this.writer;
    }

    @Override
    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getInputStream()
    {
        return this.inputStream;
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
    public ApplicationContext getContext()
    {
        return this.context;
    }

    public void kill()
    {
        this.shouldTerminate = true;
    }

    protected String safeReadNext() throws InterruptedException
    {
        Scanner scanner = new Scanner(this.inputStream);
        while(!scanner.hasNext())
        {
            Thread.sleep(BUSY_WAITING_QUANTUM);
            if(this.shouldTerminate) throw new InterruptedException();
        }

        return scanner.next();
    }

    protected String safeReadNextLine() throws InterruptedException
    {
        Scanner scanner = new Scanner(this.inputStream);
        while(!scanner.hasNextLine())
        {
            Thread.sleep(BUSY_WAITING_QUANTUM);
            if(this.shouldTerminate) throw new InterruptedException();
        }

        return scanner.nextLine();
    }

    protected boolean askConfirmation() throws InterruptedException
    {
        boolean decided = true;

        do {
            String response = this.safeReadNextLine().toLowerCase();

            if(response.equals("yes"))
            {
                return true;
            }
            else if(response.equals("no"))
            {
                return false;
            }
            else
            {
                decided = false;
            }

            if(!decided)
            {
                this.writer.println("Please, answer yes/no");
            }

        } while(!decided);

        return false;
    }
}
