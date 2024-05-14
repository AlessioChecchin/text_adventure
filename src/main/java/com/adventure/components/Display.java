package com.adventure.components;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Display extends VBox
{
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