package com.adventure.models.items;

public class Sword extends AttackItem {
    public Sword() {
        super("sword");
        setAdder(10);
        setMultiplier(4);
    }

    @Override
    public String toString()
    {
        return String.format("%s (atk: %d, %f)", this.getName(), this.getAdder(), this.getMultiplier());
    }

}
