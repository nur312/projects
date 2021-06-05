package com.nurzhigit;

import java.util.Objects;

/**
 * Came
 */
final public class Game {
    final String playerName = "Player";
    final String botName = "Bot";
    final String yes = "Yes";
    final String no = "No";
    final int width;
    final int height;
    final int startBalance;
    private final Desk desk;
    private Player player, bot;

    /**
     * Create instance of Game
     *
     * @param height_       height
     * @param width_        width
     * @param startBalance_ startBalance
     */
    public Game(int height_, int width_, int startBalance_) {
        width = width_;
        height = height_;
        startBalance = startBalance_;
        desk = new Desk(width, height);
    }

    /**
     * Launch Game: create players
     */
    public void Launch() {
        try {
            player = new Player(playerName, startBalance);
            bot = new Player(botName, startBalance);
            System.out.println(desk.DeskToString(player));
            PrintStartCoefficients();

            if (Helper.random.nextBoolean()) {
                StartGame(player, bot);
            } else {
                StartGame(bot, player);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    /**
     * Repeats while player and bot have money
     *
     * @param first  fist turn
     * @param second second turn
     */
    void StartGame(Player first, Player second) {
        while (first.IsHaveMoney() && second.IsHaveMoney()) {
            StartTurn(first);
            if(!first.IsHaveMoney()){
                break;
            }
            Helper.GetChar();
            System.out.println("\n-------------------------");
            StartTurn(second);
            Helper.GetChar();
            Helper.PrintWaves();
            System.out.println(desk.DeskToString(this.player));
        }
        PrintResult();
    }

    /**
     * Prints result of game
     */
    void PrintResult() {
        if (bot.GetBalance() > 0) {
            System.out.println("You lose! Please try again...");
        } else {
            System.out.println("You win!");
        }
    }

    /**
     * Depending on the player starts the stage
     *
     * @param player player
     */
    void StartTurn(Player player) {
        int firstDice = 1 + Helper.random.nextInt(6);
        int secondDice = 1 + Helper.random.nextInt(6);
        if (player == this.player) {
            System.out.println("\nIt is your turn!\n" + "The dices sum is " + (firstDice + secondDice));
            desk.ShiftPosition(player, (firstDice + secondDice));
            PrintPlayerCoordinates1();
            PrintAndChooseCellOptions(player);

        } else {
            System.out.println("\nIt is bot's turn!\n" + "The dices sum is " + (firstDice + secondDice));
            desk.ShiftPosition(bot, (firstDice + secondDice));
            PrintBotCoordinates1();
            BotTurn(player);
        }
    }

    /**
     * Bots's turn
     *
     * @param bot bot
     */
    void BotTurn(Player bot) {
        var cell = desk.GetCurrentCellOf(bot);
        if (cell instanceof Shop) {
            BotInShop((Shop) cell);
        } else if (cell instanceof Bank) {
            System.out.println("    Bot skipped bank office's offer");
        } else if (cell instanceof PenaltyCell) {
            System.out.println("    PenaltyCell: charged $"
                    + PenaltyCellCase(bot, (PenaltyCell) cell));
            PrintBotAccount();
        } else if (cell instanceof EmptyCell) {
            System.out.println("    EmptyCell: " + EmptyCell.GetMessage());
        } else if (cell instanceof Taxi) {
            int distance = Taxi.GetDistance();
            desk.ShiftPosition(bot, distance);
            System.out.println("    Taxi: bot is shifted forward by " + distance + " cells");
            BotTurn(bot);
        } else {
            System.out.println("Error");
        }

    }

    /**
     * Defines the behavior of the bot in the shop
     *
     * @param shop shop
     */
    void BotInShop(Shop shop) {
        if (shop.GetOwner() == null) {
            if (bot.GetBalance() >= shop.GetPrice() && Helper.random.nextBoolean()) {
                shop.SetOwner(bot);
                System.out.println("    bot has bought the shop");
                PrintBotAccount();
            } else {
                System.out.println("    bot has declined shop buy offer");
            }
        } else if (shop.GetOwner() == bot) {
            if (bot.GetBalance() >= shop.GetPrice() && Helper.random.nextBoolean()) {
                shop.Improve();
                System.out.println("    bot has improved the shop");
                PrintBotAccount();
            } else {
                System.out.println("    bot has declined shop improve offer");
            }
        } else {
            System.out.println("    bot paid to you compensation $" + shop.GetCompensationSum());
            shop.GetCompensationFrom(bot);
            PrintBotAccount();
            PrintPlayerAccount();
        }
    }

    /**
     * Defines player behavior
     *
     * @param player player
     */
    void PrintAndChooseCellOptions(Player player) {
        var cell = desk.GetCurrentCellOf(player);
        if (cell instanceof Shop) {
            var shop = (Shop) cell;
            if (shop.GetOwner() == null) {
                TrySellShop(player, shop);
            } else if (shop.GetOwner() == player) {
                TryImproveShop(player, shop);
            } else {
                ChargeMoney(player, shop);
            }
        } else if (cell instanceof Bank) {
            BankServe(player, (Bank) cell);
        } else if (cell instanceof PenaltyCell) {
            System.out.println("    PenaltyCell: charged $"
                    + PenaltyCellCase(player, (PenaltyCell) cell));
            PrintPlayerAccount();
        } else if (cell instanceof EmptyCell) {
            System.out.println("    EmptyCell: " + EmptyCell.GetMessage());
        } else if (cell instanceof Taxi) {
            int distance = Taxi.GetDistance();
            desk.ShiftPosition(player, distance);
            System.out.println("    Taxi: you are shifted forward by " + distance + " cells");
            PrintAndChooseCellOptions(player);
        } else {
            System.out.println("Error");
        }
    }

    /**
     * Player in PenaltyCell
     *
     * @param player  human
     * @param penalty cell
     * @return amount of penalty
     */
    int PenaltyCellCase(Player player, PenaltyCell penalty) {
        return penalty.ChargeFrom(player);
    }

    /**
     * Player in bank behavior
     *
     * @param player human
     * @param office bank
     */
    void BankServe(Player player, Bank office) {
        if (!office.IsDebtor(player)) {
            System.out.println("You are in the bank office. Would you like to get a credit? Input how many you" +
                    "want to get or 'No'");
            int result = ReadNoOrPositiveNumber(office.PlayerCreditLimit(player));
            if (result > 0 && office.GiveDebt(player, result)) {
                System.out.println("You have got credit");
                PrintPlayerAccount();
            }
        } else {
            System.out.println("Bank charged your $" + office.ChargeFrom(player));
            PrintPlayerAccount();
        }


    }

    /**
     * Read No Or Positive Number
     *
     * @param limit limit of number
     * @return input (-1 if No)
     */
    int ReadNoOrPositiveNumber(int limit) {
        var str = Helper.scanner.nextLine();
        while (!(Objects.equals(str, no) || tryParseInt(str, limit))) {
            System.out.println("Please enter 'No' or positive integer number");
            System.out.println("Your limit is " + limit +
                    ", please enter number less than limit");
            str = Helper.scanner.nextLine();
        }

        if (Objects.equals(str, no)) {
            return -1;
        } else {
            return Integer.parseInt(str);
        }
    }

    /**
     * Try parse to int with limit
     *
     * @param value value
     * @param limit limit
     * @return result of check
     */
    boolean tryParseInt(String value, int limit) {
        try {
            var num = Integer.parseInt(value);
            return num > 0 && num <= limit;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Charges money from player
     *
     * @param player player
     * @param shop   shop cell
     */
    void ChargeMoney(Player player, Shop shop) {
        var str = String.format("   You are in opponent shop %s. You paid compensation %d$",
                desk.GetCurrentCoordinatesOf(player), shop.GetCompensationSum());
        System.out.println(str);
        shop.GetCompensationFrom(player);
        PrintPlayerAccount();
        PrintBotAccount();
    }

    /**
     * Try improve player's shop
     *
     * @param player human
     * @param shop   shop cell
     */
    void TryImproveShop(Player player, Shop shop) {
        System.out.printf("You are in your shop cell %s. Would you like to upgrade it for %d$?\n" +
                        "Input 'Yes' if you agree or 'No' otherwise%n",
                desk.GetCurrentCoordinatesOf(player),
                shop.GetPrice());
        var answer = ReadYesNo();
        if (Objects.equals(answer, yes)) {
            if (player.GetBalance() >= shop.GetPrice()) {
                shop.Improve();
                System.out.println("You improved the shop");
                PrintPlayerAccount();
            } else {
                System.out.println("You have not enough money");
            }
        }
    }

    /**
     * Reads Yes or No
     *
     * @return input
     */
    String ReadYesNo() {
        var str = Helper.scanner.nextLine();
        while (!(Objects.equals(str, yes) || Objects.equals(str, no))) {
            System.out.println("Please enter 'Yes' or 'No'");
            str = Helper.scanner.nextLine();
        }
        return str;
    }

    /**
     * Try to sell the shop
     *
     * @param player human
     * @param shop   shop cell
     */
    void TrySellShop(Player player, Shop shop) {
        System.out.printf("You are in shop cell %s. This shop has no owner. Would you like to buy it for %d$?\n" +
                        "Input 'Yes' if you agree or 'No' otherwise\n",
                desk.GetCurrentCoordinatesOf(player),
                shop.GetPrice());
        var answer = ReadYesNo();
        if (Objects.equals(answer, yes)) {
            if (player.GetBalance() >= shop.GetPrice()) {
                shop.SetOwner(player);
                System.out.println("You bought the shop");
                PrintPlayerAccount();
            } else {
                System.out.println("You have not enough money");
            }
        } else {
            System.out.println("You have declined the offer");
        }
    }

    /**
     * Print Player Coordinates
     */
    void PrintPlayerCoordinates1() {
        System.out.println("Your coordinates is " + desk.GetCurrentCoordinatesOf(player)+ "\n");
    }

    /**
     * Print Bot Coordinates
     */
    void PrintBotCoordinates1() {
        System.out.println("Bot's coordinates is " + desk.GetCurrentCoordinatesOf(bot)+ "\n");
    }

    /**
     * Print player count
     */
    void PrintPlayerAccount() {
        System.out.println("Your account is $" + player.GetBalance());
    }

    /**
     * Print bot account
     */
    void PrintBotAccount() {
        System.out.println("Bot's account is $" + bot.GetBalance());
    }

    /**
     * Prints initial coefficients
     */
    void PrintStartCoefficients() {
        System.out.printf(
                "\tcreditCoefficient is %f\n\tdebtCoefficient is %f\n\tpenaltyCoefficient is %f\n",
                desk.creditCoefficient, desk.debtCoefficient, desk.penaltyCoefficient);
        System.out.printf(
                "\theight is %d\n\twidth is %d\n\tstart money is %d\n",
                desk.height, desk.width, this.startBalance);
    }

}
