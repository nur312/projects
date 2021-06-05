package com.nurzhigit;

/**
 * Player
 */
public final class Player {
    public final String name;
    private int money;
    private int shopInvestment;
    public int position;

    /**
     * Create instance of Player
     *
     * @param name         name
     * @param startBalance startBalance
     */
    public Player(String name, int startBalance) {
        money = startBalance;
        this.name = name;
    }

    /**
     * Get account
     *
     * @return account
     */
    int GetBalance() {
        return money;
    }

    /**
     * Charge money
     *
     * @param sum sum
     */
    void ChargeMoney(int sum) {
        money -= sum;
    }

    /**
     * Calculate shop investment
     *
     * @param sum sum
     */
    void AddShopInvestment(int sum) {
        shopInvestment += sum;
    }

    /**
     * Add money to account
     *
     * @param sum sum
     */
    void AddMoney(int sum) {
        money += sum;
    }

    /**
     * returns ShopInvestment amount
     *
     * @return ShopInvestment amount
     */
    int GetShopInvestment() {
        return shopInvestment;
    }

    /**
     * Is have player money
     *
     * @return player money
     */
    boolean IsHaveMoney() {
        return money > 0;
    }
}
