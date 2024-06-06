package com.adventure.components;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.adventure.commands.CmdUse;
import com.adventure.commands.CommandParser;
import com.adventure.utils.StringPropertyWriter;
import com.adventure.commands.Command;
import com.adventure.controllers.BaseController;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Display extends GridPane implements BaseController
{
    @FXML
    private Label consoleOutput;

    @FXML
    private TextField consolePrompt;

    @FXML
    private VBox graphics;

    private Command currentCommand;
    private Task<Void> task;
    private PipedOutputStream cmdInput;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();

    public Display()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try
        {
            fxmlLoader.load();
        }
        catch (IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize()
    {
        this.currentCommand = null;
        this.cmdInput = null;
    }


    public void onPromptClick()
    {
        this.consolePrompt.requestFocus();
    }

    /**
     * Handles console input
     * @param event Input event.
     * @throws IOException IOException
     */
    public void onKeyPressed(KeyEvent event) throws IOException
    {
        AutoCompleter autoCompl = AutoCompleter.getInstance();

        if(event.getCode() == KeyCode.SPACE)
        {
            //for(String s : CmdUse.args())
                //System.out.println(s);
        }

        //  Loads the completer only when is detects a change in the input
        //  A change occurs when
        //  1) input character is non-empty (e.g. arrows, shift...) AND it's not TAB
        //  2) input character is 'delete' key (back_space)
        if((! event.getText().isEmpty() && ! event.getText().equals("\t")) || event.getCode() == KeyCode.BACK_SPACE )
        {
            autoCompl.loadCompleter(event.getText(), this.consolePrompt.getText());
        }

        if( event.getCode() == KeyCode.TAB)
        {
            //  Execute autoCompleter
            autoCompl.operate(this.consoleOutput, this.consolePrompt);
        }

        // Checks if the command is complete.
        if( event.getCode() == KeyCode.ENTER )
        {
            autoCompl.loadCompleter("","");
            String command = consolePrompt.getText();

            // If there aren't command currently executing, then a new command is spawned.
            if(this.currentCommand == null)
            {

                // The command is parsed.
                CommandParser parser = CommandParser.getInstance();
                Command cmd = parser.parseCommand(command);

                // cmd is null then it's an unknown command.
                if(cmd == null)
                {
                    this.consoleOutput.setText("Unknown command");
                }
                else
                {
                    // Clears previous command.
                    this.consoleOutput.setText("");

                    // Generating streams.
                    PrintWriter writer = new PrintWriter(new StringPropertyWriter(this.consoleOutput.textProperty()));
                    cmd.setWriter(writer);
                    this.currentCommand = cmd;

                    this.cmdInput = new PipedOutputStream();
                    PipedInputStream input = new PipedInputStream();
                    input.connect(this.cmdInput);

                    cmd.setInputStream(input);

                    this.cmdInput.write("".getBytes(StandardCharsets.UTF_8));
                    this.cmdInput.flush();

                    createTask(cmd);

                    Thread t = new Thread(task);
                    t.setDaemon(false);
                    t.start();
                }
            }
            else
            {
                this.cmdInput.write((command + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
                this.cmdInput.flush();
            }

            consolePrompt.clear();
        }
    }

    /**
     * Runs the command in a separated task (thread).
     * @param cmd Command to execute.
     */
    private void createTask(Command cmd) {
        this.task = new Task<>() {
            @Override
            protected Void call()
            {
                try
                {
                    cmd.execute();
                }
                catch(InterruptedException exception)
                {
                    logger.debug("Command '{}' was interrupted...", cmd.getClass().getSimpleName());
                }
                catch (Exception exception)
                {
                    logger.error("Error while executing command: ", exception);
                }
                return null;
            }
        };

        this.task.setOnSucceeded(workerStateEvent -> {
            this.currentCommand = null;
            this.task = null;
            this.cmdInput = null;
        });

        this.task.setOnCancelled(evtCancelled -> this.currentCommand.kill());
    }

    public String getText()
    {
        return textProperty().get();
    }

    public void setText(String value)
    {
        textProperty().set(value);
    }

    public StringProperty textProperty()
    {
        return consolePrompt.textProperty();
    }

    public String getStdOut()
    {
        return this.consoleOutput.getText();
    }

    public void setStdOut(String value)
    {
        this.consoleOutput.setText(value);
    }

    public VBox getGraphics()
    {
        return this.graphics;
    }
    
    public void shutdown()
    {
        if(this.task != null && this.task.isRunning())
        {
            this.task.cancel();
        }
    }
}