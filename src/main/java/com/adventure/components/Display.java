package com.adventure.components;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.adventure.CommandParser;
import com.adventure.StringPropertyWriter;
import com.adventure.commands.Command;
import com.adventure.controllers.BaseController;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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


    /**
     * Handles console input
     * @param event Input event.
     * @throws IOException
     */
    public void onKeyPressed(KeyEvent event) throws IOException {

        // Checks if the command is complete.
        if( event.getCode() == KeyCode.ENTER )
        {
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
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                return null;
            }
        };

        this.task.setOnSucceeded(workerStateEvent -> {
            this.currentCommand = null;
            this.task = null;
            this.cmdInput = null;
        });

        this.task.setOnCancelled(evtCancelled -> {
            this.currentCommand.kill();
        });
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