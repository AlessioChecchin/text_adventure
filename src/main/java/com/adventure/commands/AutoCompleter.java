package com.adventure.commands;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Singleton that predicts text when pressing tab
 */
public class AutoCompleter
{
    /**
     * Default constructor
     */
    private AutoCompleter()
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
            instance = new AutoCompleter();
        return instance;
    }

    /**
     * Given a string of words, this method detects which is the last complete word in the String
     * @return The last complete word in the String
     */
    private String getPreviousCommand()
    {
        //  "test1 " -> previous command = "test1"
        if(this.partialCommand.endsWith(" "))
            return this.allWords[allWords.length - 1];
        //  "test1 tes" -> previous command = "test1"
        else
            return this.allWords[allWords.length - 2];
    }

    /**
     * Create an ArrayList with all the possible prediction for the given partial command
     */
    private void buildPredictionList()
    {
        CommandParser parser = CommandParser.getInstance();
        prediction = new ArrayList<>();

        //  Is just white spaces
        if(allWords.length == 0)
        {
            // Do nothing
        }
        //  Is base command (e.g. newGame, move, load ...)
        else if(allWords.length == 1 && ! partialCommand.contains(" "))
        {
            for (String command : parser.getCommands())
                if (command.startsWith(partialCommand))
                    prediction.add(command);
        }
        //  Is argument (e.g. stats, inventory, name of an item, name of a gameFile)
        else if(allWords.length > 1 || partialCommand.endsWith(" "))
        {
            String partialArgument = "";
            if(allWords.length > 1)
                partialArgument = allWords[allWords.length - 1];

            String prevCommand = getPreviousCommand();
            ArrayList<String> arguments = parser.argsFromCommand(prevCommand);
            for(String arg : arguments)
                if(arg.startsWith(partialArgument))
                    prediction.add(arg);
        }

        //  Create string of this list that will be printed to Output
        allCommandsString = String.join("\n", prediction);

    }

    /**
     * Gets all possible commands from the initial command inserted by the user
     * @param previousInput String The partial command found in the input field
     * @param newInput String The input just inserted
     */
    public void loadCompleter(String newInput, String previousInput)
    {
        //  Get complete input text (newInput is the char just inserted by the user)
        this.partialCommand = previousInput + newInput;
        this.cycleCounter = 0;
        allWords = partialCommand.split(" ");

        buildPredictionList();
    }

    private String buildInputText(direction dir)
    {
        //  Checks which direction to follow
        if(dir == direction.FORWARD)
            incrementCounter();
        if(dir == direction.BACKWARD)
            decrementCounter();

        //  Get the uncompleted command
        if (cycleCounter == 0)
            return partialCommand;

        //  1 word with 1 space ::: "use " -> "use Potion"
        if (allWords.length == 1 && partialCommand.endsWith(" "))
            return allWords[0] + " " + prediction.get(cycleCounter-1);

        //  1 word with 0 space ::: "newG" -> "newGame"
        if (allWords.length == 1 && !partialCommand.endsWith(" "))
            return prediction.get(cycleCounter-1);

        //  2 words with 1 space ::: "cmd1 cmd2 " -> "cm1 cmd2 cmd3"
        if (allWords.length == 2 && partialCommand.endsWith(" "))
            return allWords[0] + " " + allWords[1] + " " + prediction.get(cycleCounter-1);

        //  2 words with 0 space ::: "use Pot" -> "use Potion"
        if (allWords.length == 2 && !partialCommand.endsWith(" "))
            return allWords[0] + " " + prediction.get(cycleCounter-1);

        return partialCommand;
    }

    /**
     * Obtain and set the input and output Strings
     * @param output Label of the output prompt
     * @param input TextFiled for the input prompt
     * @param dir Direction to follow when selecting the next prediction
     */
    public void operate(Label output, TextField input, direction dir)
    {
        if(!this.allCommandsString.isEmpty())
            output.setText(this.allCommandsString);
        input.setText(buildInputText(dir));

        //  Put selection bar ( | ) to the end of the text
        input.end();
    }

    /**
     * Increment the number of times the 'tab' key has been pressed
     * @apiNote The range of the counter reaches prediction.size() + 1
     */
    public void incrementCounter()
    {
        this.cycleCounter++;
        if(this.cycleCounter > prediction.size())
            this.cycleCounter = 0;
    }

    /**
     * Decrement the number of times the 'tab' key has been pressed
     * @apiNote The range of the counter reaches prediction.size() - 1
     */
    public void decrementCounter()
    {
        this.cycleCounter--;
        if(this.cycleCounter < 0)
            this.cycleCounter = prediction.size();
    }

    /**
     * Instance of the singleton
     */
    private static AutoCompleter instance;
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
     *  Array with all the words in the partialCommand String
     */
    private String[] allWords;
    /**
     * ArrayList with all the possible prediction for the given partialCommand
     */
    private ArrayList<String> prediction;
    /**
     * Direction to follow when selecting the next prediction
     */
    public enum direction
    {
        /**
         * Shift direction backward.
         */
        BACKWARD,
        /**
         * Shift direction forward.
         */
        FORWARD
    }
}
