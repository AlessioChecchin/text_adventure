package com.adventure.models;

import java.util.Objects;

public class Item
{
    private final int id;
    private final String name;

    public Item(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name);
    }
}
