package com.adventure.models.items;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Key extends Item
{
    @JsonIgnore
    private final String value;

    public Key(@JsonProperty("value") String key)
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
