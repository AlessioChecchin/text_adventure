package com.adventure.models.nodes;

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

    public int getNext()
    {
        return IDcounter++;
    }

    public void resetCounter()
    {
        IDcounter = 0;
    }

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
