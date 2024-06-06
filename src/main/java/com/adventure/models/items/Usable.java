package com.adventure.models.items;

/**
 * Usable interface.
 */
public interface Usable
{
    /**
     * Set additional attack value. This value will be added to the current attack value of the player.
     * @param atk Additional attack value.
     */
    void setAdditionalAttack(int atk);

    /**
     * Set additional defence value. This value will be added to the current defence value of the player.
     * @param def Additional defence value.
     */
    void setAdditionalDefence(int def);

    /**
     * Set additional hp value. This value will be added to the current hp value of the player.
     * @param hp Additional hp value.
     */
    void setAdditionalHp(int hp);

    /**
     * Get the additional attack value.
     * @return additional attack value.
     */
    int getAttack();

    /**
     * Get the additional defence value.
     * @return additional defence value.
     */
    int getDefence();

    /**
     * Get the additional hp value.
     * @return additional hp value.
     */
    int getHp();
}
