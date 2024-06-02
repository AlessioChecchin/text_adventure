package com.adventure.models.items;


import java.util.Objects;

public class Key extends Item
{
    private final String value;

    public Key(String key)
    {
        super("key");
        Objects.requireNonNull(key, "key cannot be null");
        this.value = key;
    }

    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%s)", this.name, this.value);
    }

}
