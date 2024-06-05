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

/**
 * Abstract command that contains basic functionality for a generic command.
 */
public abstract class AbstractCommand implements Command
{
    /**
     * Default constructor.
     */
    public AbstractCommand()
    {
        // Using default stdin / stdout.
        this.writer = new PrintWriter(System.out);
        this.inputStream = System.in;

        // Default: no args provided.
        this.args = new ArrayList<>();

        // When spawned the command should not terminate.
        this.shouldTerminate = false;
    }

    //
    // GETTERS.
    //

    /**
     * Writer getter.
     * @return Writer object.
     */
    @Override
    public Writer getWriter()
    {
        return this.writer;
    }

    /**
     * Input stream getter.
     * @return Input stream.
     */
    @Override
    public InputStream getInputStream()
    {
        return this.inputStream;
    }

    /**
     * Arguments getter.
     * @return Arguments.
     */
    @Override
    public List<String> getArgs()
    {
        return new ArrayList<>(this.args);
    }

    @Override
    public ApplicationContext getContext()
    {
        return this.context;
    }

    //
    // SETTERS.
    //

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

    /**
     * Input stream setter.
     * @param inputStream Input stream.
     */
    @Override
    public void setInputStream(InputStream inputStream)
    {
        Objects.requireNonNull(inputStream, "InputStream cannot be null");
        this.inputStream = inputStream;
    }

    /**
     * Arguments setter.
     * @param args Arguments.
     */
    @Override
    public void setArgs(List<String> args)
    {
        Objects.requireNonNull(args, "args cannot be null");
        this.args = new ArrayList<>(args);
    }

    /**
     * Context setter.
     * @param context Application context.
     */
    @Override
    public void setContext(ApplicationContext context)
    {
        Objects.requireNonNull(context, "context cannot be null");
        this.context = context;
    }

    //
    // OTHER.
    //

    public void kill()
    {
        this.shouldTerminate = true;
    }

    //
    // PROTECTED METHODS.
    //

    /**
     * Read method used for non-blocking read operations.
     * Non-blocking implementation of Scanner.next().
     * @return Next valid string token.
     * @throws InterruptedException If the command gets a kill request.
     */
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

    /**
     * Read method used for non-blocking read (entire line) operations.
     * Non-blocking implementation of Scanner.nextLine().
     * @return Text line.
     * @throws InterruptedException If the command gets a kill request.
     */
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

    /**
     * Method used to ask yes/no response from a user.
     * @return Response (true = yes, false = no)
     * @throws InterruptedException If the command gets a kill request.
     */
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

    //
    // VARIABLES.
    //

    /**
     * Writer for output.
     */
    protected PrintWriter writer;

    /**
     * Stream used to receive user input.
     */
    protected InputStream inputStream;

    /**
     * Eventual parameters for the command.
     */
    protected List<String> args;

    /**
     * Current application context.
     */
    protected ApplicationContext context;

    /**
     * Flag that indicates if the main thread requested the command to terminate.
     */
    protected boolean shouldTerminate;

    /**
     * Interval for busy waiting regarding safe input read.
     * Busy waiting is used to avoid blocking calls and allow thread termination when requested.
     */
    public static final int BUSY_WAITING_QUANTUM = 10;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();
}
