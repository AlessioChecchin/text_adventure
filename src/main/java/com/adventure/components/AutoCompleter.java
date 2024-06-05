package com.adventure.components;

import com.adventure.commands.AbstractCommand;
import com.adventure.commands.*;
import com.adventure.commands.CommandParser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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

//    /**
//     * Possible commands list setter
//     * @param list List of all the names of possible commands
//     * @apiNote First element of the list is always the initial uncompleted command
//     */
//    public void setList(List<String> list)
//    {
//        this.list = list;
//        this.allCommandsString = String.join("\n", list);
//        list.add(0, this.allWords[allWords.length - 1]);
//    }

    private String getPreviousCommand()
    {
        //  "test1 " -> previous command = "test1"
        if(this.partialCommand.endsWith(" "))
            return this.allWords[allWords.length - 1];
        //  "test1 tes" -> previous command = "test1"
        else
            return this.allWords[allWords.length - 2];
    }

    private void buildPredictionList()
    {
        CommandParser parser = CommandParser.getInstance();
        prediction = new ArrayList<>();

        //  Is base command
        if(allWords.length == 1 && ! partialCommand.contains(" "))
        {
            for (String command : parser.getCommands())
                if (command.startsWith(partialCommand))
                    prediction.add(command);
        }
        //  Is argument
        else if(allWords.length > 1 || partialCommand.endsWith(" "))
        {
            String prevCommand = getPreviousCommand();
            prediction = parser.argsFromCommand(prevCommand);
        }

        //  Create string of this list
        allCommandsString = String.join("\n", prediction);

    }

    /**
     * Gets all possible commands from the partial command inserted by the user
     * @param previousInput String The partial command found in the input field
     * @param newInput String The input just inserted
     */
    public void loadCompleter(String newInput, String previousInput)
    {
        //  Get input text (partial command = previous input + new character inserted)
        this.partialCommand = previousInput + newInput;
        this.cycleCounter = 0;
        CommandParser parser = CommandParser.getInstance();
        allWords = partialCommand.split(" ");

        buildPredictionList();


//        //  List all enabled commands for the current room
//        ArrayList<String> possibleCommands = new ArrayList<>();
//
//        //  Is just one word AND it doesn't end with white space -> it's a base command
//        if(allWords.length == 1 && ! partialCommand.contains(" "))
//        {
//            for (String command : parser.getCommands())
//                if (command.startsWith(partialCommand))
//                    possibleCommands.add(command);
//        }
//        //  There's more than 1 word OR there's a white space at the end -> it's an argument
//        else if(allWords.length > 1 || partialCommand.contains(" "))
//        {
//
//            String baseCommand = "";
//            if(partialCommand.endsWith(" "))
//                baseCommand = allWords[allWords.length - 1];
//            else
//                baseCommand = allWords[allWords.length - 2];
//
//            ArrayList<String> possibleArguments = parser.argsFromCommand(baseCommand);
//            for(String possibleWord : possibleArguments)
//            {
//                StringBuilder temp = new StringBuilder();
//                for(int i=0; i<allWords.length - 1; i++)
//                    temp.append(allWords[i]).append(" ");
//                temp.append(possibleWord);
//                possibleCommands.add(temp.toString());
//            }
//        }
        //  Set the command list for the autoCompleter
        //setList(possibleCommands);
    }

    private String buildInputText()
    {
        if (cycleCounter == 0)
            return partialCommand;

        if (allWords.length == 1 && partialCommand.endsWith(" "))
            return allWords[0] + " " + prediction.get(cycleCounter - 1);

        if (allWords.length == 1 && !partialCommand.endsWith(" "))
            return prediction.get(cycleCounter - 1);

        if (allWords.length == 2 && partialCommand.endsWith(" "))
            return allWords[0] + " " + allWords[1] + " " + prediction.get(cycleCounter - 1);

        if (allWords.length == 2 && !partialCommand.endsWith(" "))
            return allWords[0] + " " + prediction.get(cycleCounter - 1);

        return partialCommand;
    }

//        // It's just one word
//        if(this.allWords.length == 1)
//            return this.list.get(index);
//        StringBuilder out = new StringBuilder();
//        for(int i=0; i<allWords.length - 1; i++)
//            out.append(allWords[i]).append(" ");
//        out.append(list.get(index));
//        return out.toString();
//    }

    /**
     * Cycles through all list of commands
     * @param output Label with outputPrompt
     * @param input TextField with inputPrompt
     */
    public void operate(Label output, TextField input )
    {
        output.setText(this.allCommandsString);
        input.setText(buildInputText());

//        //  Checks if there are multiple possible commands (> 2 and not > 1 because one element of the list is partialCommand
//        if(this.list.size() > 2)
//        {
//            //  At first cycle it prints to the output all possible commands and
//            //  places the partialCommand in the input field
//            if (this.cycleCounter == 0)
//                output.setText(this.allCommandsString);
//            input.setText(buildInputText());
//
//        }
//        //  If there's only one possible command it automatically completes it in the input field
//        else if(this.list.size() == 2)
//        {
//            output.setText(this.allCommandsString);
//        }
//        input.setText(buildInputText());


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
        if(this.cycleCounter > prediction.size())
            this.cycleCounter = 0;
    }

    /**
     * Instance of the singleton
     */
    private static AutoCompleter instance;
    /**
     * List of all possible commands. First element is always the uncompleted command inserted by the user
     */
    //private List<String> list;
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
     *
     */
    private String[] allWords;
    private ArrayList<String> prediction;
}
