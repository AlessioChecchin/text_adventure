package com.adventure.components;

import com.adventure.commands.CommandParser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton that predicts text when pressing tab
 */
public class AutoCompleter
{
    /**
     * Default constructor
     * @param list List with all the possible commands' name
     */
    private AutoCompleter(List<String> list)
    {
        this.loadCompleter("","");
    }

    /**
     * Instance getter for the singleton
     * @return current instance
     */
    public static AutoCompleter getInstance()
    {
        if(instance == null)
            instance = new AutoCompleter(new LinkedList<String>());
        return instance;
    }

    /**
     * partialCommand field setter
     * @param partialCommand String with the partialCommand to set
     */
    public void setPartialCommand(String partialCommand)
    {
        this.partialCommand = partialCommand;
        this.cycleCounter = 0;
    }

    /**
     * Possible commands list setter
     * @param list List of all the names of possible commands
     * @apiNote First element of the list is always the initial uncompleted command
     */
    public void setList(List<String> list)
    {
        this.list = list;
        this.allCommandsString = String.join("\n", list);
        list.add(0, this.partialCommand);
    }

    /**
     * Checks if the autoCompleter was never loaded before
     * @return False if it was never loaded, true otherwise
     */
    public boolean isLoaded()
    {
        return this.loaded;
    }

    /**
     * Gets all possible commands from the partial command inserted by the user
     * @param previousInput String The partial command found in the input field
     * @param newInput String The input just inserted
     */
    public void loadCompleter(String previousInput, String newInput)
    {
        this.loaded = true;
        this.cycleCounter = 0;

        //  Get input text (partial command = previous input + new character inserted)
        this.partialCommand = newInput + previousInput;

        CommandParser parser = CommandParser.getInstance();

        //  List all enabled commands for the current room
        ArrayList<String> possibleCommands = new ArrayList<>();
        for (String command : parser.getCommands())
            if (command.startsWith(partialCommand))
                possibleCommands.add(command);

        //  Set the command list for the autoCompleter
        setList(possibleCommands);
    }

    /**
     * Cycles through all list of commands
     * @param output Label with outputPrompt
     * @param input TextField with inputPrompt
     */
    public void operate(Label output, TextField input )
    {
        //  Checks if there are multiple possible commands (> 2 and not > 1 because one element of the list is partialCommand
        if(this.list.size() > 2)
        {
            //  At first cycle it prints to the output all possible commands and
            //  places the partialCommand in the input field
            if (this.cycleCounter == 0)
            {
                output.setText(this.allCommandsString);
                input.setText(this.list.get(0));
            }
            //  For all other cycles it completes the input field with the next possible command
            else
            {
                input.setText(this.list.get(this.cycleCounter));
            }
        }
        //  If there's only one possible command it automatically completes it in the input field
        else if(this.list.size() == 2)
        {
            output.setText(this.allCommandsString);
            input.setText(this.list.get(1));
        }


        //  Put selection bar ( | ) to the end of the text
        input.end();
        this.incrementCounter();
    }

    /**
     * Increment the number of times the 'tab' key has been pressed
     * @apiNote The range of the counter is always within the list size
     */
    public void incrementCounter()
    {
        this.cycleCounter++;
        if(this.cycleCounter == this.list.size())
            this.cycleCounter = 0;
    }

    /**
     * partialCommand field getter
     * @return String partialCommand memorized
     */
    public String getPartialCommand()
    {
        return this.partialCommand;
    }

    /**
     * Instance of the singleton
     */
    private static AutoCompleter instance;
    /**
     * List of all possible commands. First element is always the uncompleted command inserted by the user
     */
    private List<String> list;
    /**
     * partialCommand initially inserted by the user
     */
    private String partialCommand;
    /**
     * Counter of the number of calls of the method "operate"
     */
    private int cycleCounter;
    /**
     * String with all possible commands that has to be printed on the output prompt
     */
    private String allCommandsString;
    /**
     * Keep record if this autoCompleter was never loaded before
     */
    private boolean loaded = false;
}
