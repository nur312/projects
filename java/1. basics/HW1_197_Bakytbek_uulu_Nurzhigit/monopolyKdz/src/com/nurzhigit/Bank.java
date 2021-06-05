package com.nurzhigit;

import java.util.ArrayList;

/**
 * Bank offers custody accounts services
 */
public final class Bank extends Cell {
    private final double creditCoefficient;
    private final double debtCoefficient;
    final private ArrayList<Debtor> debtors = new ArrayList<>();

    /**
     * @param creditCoefficient creditCoefficient
     * @param debtCoefficient   debtCoefficient
     */
    public Bank(double creditCoefficient, double debtCoefficient) {
        this.creditCoefficient = creditCoefficient;
        this.debtCoefficient = debtCoefficient;
    }

    /**
     * @param player give debt to the player
     * @param sum    amount of money
     * @return result of operation
     */
    boolean GiveDebt(Player player, int sum) {
        if (sum <= (int) Math.round(creditCoefficient * player.GetShopInvestment())) {
            player.AddMoney(sum);
            var newD = new Debtor(player);
            newD.debt = (int) Math.round(sum * debtCoefficient);
            debtors.add(newD);
            return true;
        }
        return false;
    }

    /**
     * calculates Player Credit Limit
     *
     * @param player player
     * @return Credit Limit
     */
    public int PlayerCreditLimit(Player player) {
        return (int) Math.round(creditCoefficient * player.GetShopInvestment());
    }

    /**
     * Bank charge debt from player
     *
     * @param player player
     * @return amount of charged money
     */
    int ChargeFrom(Player player) {
        var d = GetDebtor(player);
        int sum = 0;
        if (d != null) {
            player.ChargeMoney(d.debt);
            sum = d.debt;
            d.debt = 0;
            debtors.remove(d);
        }
        return sum;
    }

    /**
     * Checks is player a debtor
     *
     * @param player player
     * @return result of check
     */
    boolean IsDebtor(Player player) {
        for (var el : debtors) {
            if (el.debtor == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns debtor if player owe to Bank
     *
     * @param player player
     * @return debtor if player owe to Bank
     */
    Debtor GetDebtor(Player player) {
        for (var el : debtors) {
            if (el.debtor == player) {
                return el;
            }
        }
        return null;
    }

    /**
     * Player with debt
     */
    static class Debtor {
        Debtor(Player d) {
            debtor = d;
        }

        Player debtor;
        int debt = 0;
    }
}
