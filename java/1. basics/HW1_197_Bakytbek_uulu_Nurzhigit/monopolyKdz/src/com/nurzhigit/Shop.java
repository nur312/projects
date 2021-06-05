package com.nurzhigit;

/**
 * Shop
 */
public final class Shop extends Cell {
    private Player owner;

    private int price;
    private int compensation;
    private final float compensationCoefficient;
    private final float improvementCoefficient;

    /**
     * Create instance of Shop
     *
     * @param price                   price
     * @param compensation            compensation
     * @param compensationCoefficient compensationCoefficient
     * @param improvementCoefficient  improvementCoefficient
     */
    public Shop(int price, int compensation,
                float compensationCoefficient, float improvementCoefficient) {
        this.compensationCoefficient = compensationCoefficient;
        this.improvementCoefficient = improvementCoefficient;
        this.price = price;
        this.compensation = compensation;
    }

    /**
     * @return shop's price
     */
    public int GetPrice() {
        return price;
    }

    /**
     * Set owner to shop
     *
     * @param owner owner
     */
    public void SetOwner(Player owner) {
        this.owner = owner;
        owner.ChargeMoney(price);
        owner.AddShopInvestment(price);
    }

    /**
     * Gets compensation from opponent of owner
     *
     * @param otherPlayer otherPlayer
     */
    public void GetCompensationFrom(Player otherPlayer) {
        otherPlayer.ChargeMoney(compensation);
        owner.AddMoney(compensation);
    }

    /**
     * @return compensation
     */
    public int GetCompensationSum() {
        return compensation;
    }

    /**
     * Improve the shop
     */
    void Improve() {
        int improveSum = Math.round(price * improvementCoefficient);
        owner.ChargeMoney(improveSum);
        owner.AddShopInvestment(improveSum);

        price += improveSum;
        compensation += Math.round(compensationCoefficient * compensation);
    }

    /**
     * @return owner
     */
    Player GetOwner() {
        return owner;
    }
}
