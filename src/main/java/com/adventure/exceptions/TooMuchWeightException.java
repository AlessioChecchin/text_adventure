package com.adventure.exceptions;

public class TooMuchWeightException extends RuntimeException
{
    public TooMuchWeightException() {}
    public TooMuchWeightException(String message)
    {
        super(message);
    }
}
