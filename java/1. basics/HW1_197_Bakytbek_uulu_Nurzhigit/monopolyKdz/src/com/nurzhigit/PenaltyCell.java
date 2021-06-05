package com.nurzhigit;

/**
 * Penalty Cell
 */
public final class PenaltyCell extends Cell {
    public final double penaltyCoefficient;

    /**
     * Create instance of PenaltyCell
     *
     * @param penaltyCoefficient penalty coefficient
     */
    public PenaltyCell(double penaltyCoefficient) {
        this.penaltyCoefficient = penaltyCoefficient;
    }

    /**
     * charges money from player
     *
     * @param player player
     * @return penalty amount
     */
    public int ChargeFrom(Player player) {
        int sum = (int) Math.round(penaltyCoefficient * player.GetBalance());
        player.ChargeMoney(sum);
        return sum;
    }
}
