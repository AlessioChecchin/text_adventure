package com.adventure.exceptions;

/**
 * Exception thrown when the item that the player is trying to use is not usable.
 */
public class NotUsableItemException extends Exception
{
    /**
     * Default constructor.
     */
    public NotUsableItemException()
    {
        super();
    }

    /**
     * Message constructor.
     * @param message Message to show in the exception.
     */
    public NotUsableItemException(String message)
    {
        super(message);
    }
}

