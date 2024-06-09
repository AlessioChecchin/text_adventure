package com.adventure.models.nodes;

/**
 * Class used to generated unique ids.
 */
public class IdManager
{
    /**
     * Default constructor
     */
    private IdManager()
    {
        this.IDcounter = 0;
    }

    /**
     * Instance getter for the singleton
     * @return current instance
     */
    public static IdManager getInstance()
    {
        if(instance == null)
            instance = new IdManager();
        return instance;
    }

    /**
     * Gets counter value and automatically increments it.
     * @return Counter value
     */
    public int getNext()
    {
        return IDcounter++;
    }

    /**
     * Resets the counter.
     */
    public void resetCounter()
    {
        IDcounter = 0;
    }

    /**
     * If the ID > than ID counter sets ID counter to ID + 1
     * @param ID Value to set.
     */
    public void check(int ID)
    {
        if(ID > IDcounter)
            this.IDcounter = ID + 1;
    }


    /**
     * ID counter that keeps tracks of all IDs
     */
    private int IDcounter;
    /**
     * Instance of the singleton
     */
    private static IdManager instance;
}
