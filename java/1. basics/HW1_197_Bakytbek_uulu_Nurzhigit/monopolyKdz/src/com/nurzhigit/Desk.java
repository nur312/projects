package com.nurzhigit;

/**
 * Desk with cells
 */
public final class Desk {
    private Cell[] cells;
    final int width;
    final int height;
    final int leftUpperIndex;
    final int rightUpperIndex;
    final int leftLowerIndex;
    final int rightLowerIndex;
    final int lastIndex;
    public final double penaltyCoefficient;
    public final double debtCoefficient;
    public final double creditCoefficient;

    /**
     * Create instance of Desk
     *
     * @param width  width
     * @param height height
     */
    public Desk(int width, int height) {
        this.width = width;
        this.height = height;
        penaltyCoefficient = Helper.GetRandomFloatBetween(
                Helper.minPenaltyCoefficient, Helper.maxPenaltyCoefficient);
        creditCoefficient = Helper.GetRandomFloatBetween(
                Helper.minCreditCoefficient, Helper.maxCreditCoefficient);
        debtCoefficient = Helper.GetRandomFloatBetween(Helper.minDebtCoefficient, Helper.maxDebtCoefficient);
        leftUpperIndex = 0;
        rightUpperIndex = width - 1;
        rightLowerIndex = width + height - 2;
        leftLowerIndex = width + height + width - 3;
        lastIndex = 2 * (width + height - 2) - 1;
        GenerateDesk();
    }

    /**
     * Converts x and y to index and return cells[index]
     *
     * @param y coordinate
     * @param x coordinate
     * @return cells[index]
     * @throws IndexOutOfBoundsException IndexOutOfBoundsException
     */
    public Cell GetCellByCoordinates(int y, int x) throws IndexOutOfBoundsException {
        // ToDo: transform to index
        if (y >= height || y < 0 || x < 0 || x >= width) {
            throw new IndexOutOfBoundsException();
        }
        if (y == 0) {
            return cells[x];
        }
        if (y == (height - 1)) {
            return cells[leftLowerIndex - x];
        }
        if (x == 0) {
            return cells[lastIndex + 1 - y];
        }
        if (x == (width - 1)) {
            return cells[rightUpperIndex + y];
        }
        return null;
    }

    /**
     * Shifts player to position cells
     *
     * @param pl       player
     * @param position how many cells
     */
    public void ShiftPosition(Player pl, int position) {
        pl.position = (pl.position + position) % (lastIndex + 1);
    }

    /**
     * @param pl player
     * @return current cell of player
     */
    public Cell GetCurrentCellOf(Player pl) {
        return cells[pl.position];
    }

    /**
     * Converts current position of player to <x><y>
     *
     * @param player player
     * @return current position of player to <x><y>
     */
    public String GetCurrentCoordinatesOf(Player player) {
        return GetCoordinatesByIndex(player.position);
    }

    /**
     * Returns <x><y> coordinates by index
     *
     * @param index index
     * @return <x><y> coordinates
     * @throws IndexOutOfBoundsException IndexOutOfBoundsException
     */
    public String GetCoordinatesByIndex(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < width) {
            return String.format("<%d><%d>", index, 0);
        }
        if (index > rightUpperIndex && index <= rightLowerIndex) {
            return String.format("<%d><%d>", width - 1, index - rightUpperIndex);
        }
        if (index > rightLowerIndex && index <= leftLowerIndex) {
            return String.format("<%d><%d>", -(index - leftLowerIndex), height - 1);
        }
        if (index > leftLowerIndex && index <= lastIndex) {
            return String.format("<%d><%d>", 0, -(index - lastIndex) + 1);
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Generates a Desk with various cells
     */
    private void GenerateDesk() {
        cells = new Cell[lastIndex + 1];
        FillAllAsShop();
        FillCornerAsEmptyCell();
        SetBankOffices();
        SetTaxi();
        SetPenaltyCells();
    }

    /**
     * Sets Penalty Cells random to every sides
     */
    private void SetPenaltyCells() {
        SetPenaltyCellsBetween(leftUpperIndex + 1, rightUpperIndex - 1);
        SetPenaltyCellsBetween(rightUpperIndex + 1, rightLowerIndex - 1);
        SetPenaltyCellsBetween(rightLowerIndex + 1, leftLowerIndex - 1);
        SetPenaltyCellsBetween(leftLowerIndex + 1, lastIndex);
    }

    /**
     * Sets PenaltyCells Between min and max position
     *
     * @param min minimum
     * @param max maximum
     */
    private void SetPenaltyCellsBetween(int min, int max) {
        for (int i = 0; i < Helper.random.nextInt(3); ++i) {
            int pos = Helper.GetRandomIntBetween(min, max);
            if (cells[pos] instanceof Shop) {
                cells[pos] = new PenaltyCell(penaltyCoefficient);
            }
        }
    }

    /**
     * Sets Taxi Cell Between min and max position
     *
     * @param min minimum
     * @param max maximum
     */
    private void SetTaxiBetween(int min, int max) {
        for (int i = 0; i < Helper.random.nextInt(3); ++i) {
            int pos = Helper.GetRandomIntBetween(min, max);
            if (cells[pos] instanceof Shop) {
                cells[pos] = new Taxi();
            }
        }
    }

    /**
     * Sets less than 3 Taxi Cells to every side
     */
    private void SetTaxi() {
        SetTaxiBetween(leftUpperIndex + 1, rightUpperIndex - 1);
        SetTaxiBetween(rightUpperIndex + 1, rightLowerIndex - 1);
        SetTaxiBetween(rightLowerIndex + 1, leftLowerIndex - 1);
        SetTaxiBetween(leftLowerIndex + 1, lastIndex);
    }

    /**
     * Sets Bank Offices to every side
     */
    private void SetBankOffices() {
        var office = new Bank(creditCoefficient, debtCoefficient);
        cells[Helper.GetRandomIntBetween(leftUpperIndex + 1, rightUpperIndex - 1)] = office;
        cells[Helper.GetRandomIntBetween(rightUpperIndex + 1, rightLowerIndex - 1)] = office;
        cells[Helper.GetRandomIntBetween(rightLowerIndex + 1, leftLowerIndex - 1)] = office;
        cells[Helper.GetRandomIntBetween(leftLowerIndex + 1, lastIndex)] = office;

    }

    /**
     * Fills Corner As EmptyCell
     */
    private void FillCornerAsEmptyCell() {
        var emptyCell = new EmptyCell();
        cells[leftUpperIndex] = emptyCell;
        cells[rightUpperIndex] = emptyCell;
        cells[rightLowerIndex] = emptyCell;
        cells[leftLowerIndex] = emptyCell;
    }

    /**
     * Fills All As Shop
     */
    private void FillAllAsShop() {
        for (int i = 0; i < cells.length; ++i) {
            int shopPrice = Helper.GetRandomIntBetween(Helper.minShopPrice, Helper.maxShopPrice);
            int compensation = Helper.GetRandomIntBetween(
                    (int) Math.round(shopPrice * 0.5), (int) Math.round(shopPrice * 0.9));
            cells[i] = new Shop(shopPrice, compensation,
                    Helper.GetRandomFloatBetween(
                            Helper.minCompensationCoefficient, Helper.maxCompensationCoefficient),
                    Helper.GetRandomFloatBetween(
                            Helper.minImprovementCoefficient, Helper.maxImprovementCoefficient));
        }
    }

    /**
     * Converts desk to sting field
     *
     * @param player human
     * @return string
     */
    public String DeskToString(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                var cell = GetCellByCoordinates(i, j);
                if (cell == null) {
                    stringBuilder.append("  ");
                } else if (cell instanceof Shop) {
                    var shop = (Shop) cell;
                    if (shop.GetOwner() == null) {
                        stringBuilder.append("S ");
                    } else if (shop.GetOwner() == player) {
                        stringBuilder.append("M ");
                    } else {
                        stringBuilder.append("O ");
                    }
                } else if (cell instanceof EmptyCell) {
                    stringBuilder.append("E ");
                } else if (cell instanceof Bank) {
                    stringBuilder.append("$ ");
                } else if (cell instanceof Taxi) {
                    stringBuilder.append("T ");
                } else if (cell instanceof PenaltyCell) {
                    stringBuilder.append("% ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
