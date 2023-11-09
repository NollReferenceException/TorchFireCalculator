package main;

public class CalculatorStatistic {
    private int checksCount;

    public void incrementChecksCount() {
        checksCount++;
    }

    public void setChecksCount(int checksCount) {
        this.checksCount = checksCount;
    }

    public int getChecksCount() {
        return checksCount;
    }
}
