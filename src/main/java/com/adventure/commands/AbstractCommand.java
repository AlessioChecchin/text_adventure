package com.adventure.commands;

import com.adventure.utils.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public abstract class AbstractCommand implements Command
{
    /**
     * Default constructor.
     */
    public AbstractCommand()
    {
        this.args = new ArrayList<>();
        this.writer = new PrintWriter(System.out);
        this.inputStream = System.in;
        this.shouldTerminate = false;
    }

    /**
     * Writer setter.
     * @param out Output writer.
     */
    @Override
    public void setWriter(Writer out)
    {
        Objects.requireNonNull(out, "Writer cannot be null");
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
        Objects.requireNonNull(inputStream, "InputStream cannot be null");
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
        Objects.requireNonNull(args, "args cannot be null");
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
        Objects.requireNonNull(context, "context cannot be null");
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

    /**
     * Checks whether the number of arguments is correct or not
     * @param argumentsNumber number of arguments expected
     * @return True if the number of arguments is correct, False otherwise
     */
    protected boolean correctArgumentsNumber(int argumentsNumber)
    {
        if(this.getArgs().size() < argumentsNumber)
        {
            writer.println("Too few arguments for command this command");
            return false;
        }
        else if(this.getArgs().size() > argumentsNumber) {
            writer.println("Too many arguments for command this command");
            return false;
        }
        return true;
    }




    protected PrintWriter writer;

    protected InputStream inputStream;

    protected List<String> args;

    protected ApplicationContext context;

    protected boolean shouldTerminate;

    public static final int BUSY_WAITING_QUANTUM = 10;

    protected static final Logger logger = LogManager.getLogger();
}
