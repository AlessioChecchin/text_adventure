package com.adventure.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Item used to unlock game graph locked edges.
 */
public class Key extends Item
{
    /**
     * Constructor.
     * @param key Key value.
     */
    public Key(@JsonProperty("value") String key)
    {
        super("key");
        Objects.requireNonNull(key, "key cannot be null");
        this.value = key;
    }

    //
    // GETTERS.
    //

    /**
     * Value getter.
     * @return Key value.
     */
    public String getValue()
    {
        return this.value;
    }

    //
    // OTHERS.
    //

    @Override
    public String toString()
    {
        return String.format("%s (%s)", this.name, this.value);
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o) && this.getValue().equals(((Key) o).getValue());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), value);
    }

    //
    // VARIABLES.
    //

    /**
     * Key value.
     */
    private final String value;
}
