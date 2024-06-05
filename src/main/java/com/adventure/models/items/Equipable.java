package com.adventure.models.items;

/**
 * Equipable interface.
 */
public interface Equipable
{
    /**
     * Get the adder value
     * @return additional attack or defence value
     */
    int getAdder();

    /**
     * Get the multiplier value
     * @return attack or defence multiplier
     */
    double getMultiplier();

    /**
     * Set adder value. This value will be added to the attack or defence value of the player
     * @param adder additional attack or defence value
     */
    void setAdder(int adder);

    /**
     * Set multiplier value. This value will be multiplied with the current attack or defence value of the player
     * @param multiplier additional attack or defence multiplier
     */
    void setMultiplier(double multiplier);

}
