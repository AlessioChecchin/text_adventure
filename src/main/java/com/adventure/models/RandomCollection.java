package com.adventure.models;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Class used to pick an item randomly from a list with a given probability.
 * @param <E>
 */
public class RandomCollection<E>
{
    public RandomCollection()
    {
        this(new Random());
    }

    public RandomCollection(Random random)
    {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result)
    {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next()
    {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;
}
