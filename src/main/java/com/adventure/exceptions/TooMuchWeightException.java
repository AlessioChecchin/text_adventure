package com.adventure.exceptions;

public class TooMuchWeightException extends RuntimeException
{
    public TooMuchWeightException()
    {
        super();
    }

    public TooMuchWeightException(String message)
    {
        super(message);
    }
}
