package com.adventure.components;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.adventure.CommandParser;
import com.adventure.commands.Command;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class Display extends VBox
{
    @FXML
    private Label consoleOutput;

    @FXML
    private TextField consolePrompt;

    @FXML
    private VBox graphics;

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
        this.consolePrompt.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER )
            {
                String command = consolePrompt.getText();
                consolePrompt.clear();

                CommandParser parser = CommandParser.getInstance();
                Command cmd = parser.parseCommand(command);

                if(cmd == null)
                {
                    this.consoleOutput.setText("Unknown command");
                }
                else
                {
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    final String utf8 = StandardCharsets.UTF_8.name();

                    try (PrintStream ps = new PrintStream(baos, true, utf8))
                    {
                        cmd.setOutputPipe(ps);
                        cmd.execute();

                        String data = baos.toString(utf8);

                        this.consoleOutput.setText(data);
                    }
                    catch(Exception e)
                    {
                        this.consoleOutput.setText("Oops an error occurred: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } );
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

}