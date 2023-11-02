package main;

import java.util.ArrayList;

public class FireCalculator {

    private final Double startingCapitalAmount = 100.0;
    private final float accuracyFactor = 3.0F;
    private final int lifeYears;
    private final int startingIndexInflationRate;
    private final int startingIndexMoexRate;

    private int cyclesCounter;
    private float maxWithdrawalPercent;

    ArrayList<Double> moexImpacts;
    ArrayList<Double> inflationRates;


    public FireCalculator(int chillYear, int highLimitYear) {
        lifeYears = (highLimitYear + 1) - chillYear;
        startingIndexInflationRate = Constants.INFLATION_RATE.length - 1 - lifeYears;
        startingIndexMoexRate = Constants.MOEX_RATE.length - 1 - lifeYears;

        moexImpacts = new ArrayList<>();
        inflationRates = new ArrayList<>();

        cyclesCounter = 0;
    }

    private float calculateMaxWithdrawalPercent() {
        if (lifeYears == 1) {
            return 100.0F;
        }

        double calculatedPercent = calculateApproximatePercent();

        boolean found;
        while (true) {
            cyclesCounter++;

            found = checkPercentForMaximality(calculatedPercent);

            if (found) {
                break;
            }

            calculatedPercent -= 0.5f;
        }

        maxWithdrawalPercent = (float) calculatedPercent;
        return maxWithdrawalPercent;
    }

    private double calculateApproximatePercent() {
        double capital = startingCapitalAmount;
        double moexImpact;
        double approximatePercent = 100.0;
        double currentInflationRate;
        double currentMoexRate;

        if (startingIndexMoexRate == 0) {
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate];
        } else {
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate - 1];
        }

        for (int i = 0; i < lifeYears; i++) {
            if (i == lifeYears - 1) {
                approximatePercent = capital / (lifeYears);
            }

            double prevousMoexRate = currentMoexRate;

            currentInflationRate = Constants.INFLATION_RATE[startingIndexInflationRate + i] / 100;
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate + i];

            moexImpact = currentMoexRate / prevousMoexRate;
            capital -= capital * currentInflationRate;
            capital *= moexImpact;

            inflationRates.add(currentInflationRate);
            moexImpacts.add(moexImpact);
        }

        approximatePercent = Math.floor((approximatePercent) + accuracyFactor / lifeYears);

        return approximatePercent;
    }

    private boolean checkPercentForMaximality(double maxPercentCandidate) {
        double capital = startingCapitalAmount;

        for (int i = 0; i < lifeYears; i++) {
            capital -= maxPercentCandidate;

            if (capital < 0) {
                return false;
            }

            maxPercentCandidate += maxPercentCandidate * inflationRates.get(i);
            capital *= moexImpacts.get(i);
        }

        return true;
    }

    public float getMaxWithdrawalPercent() {
        return calculateMaxWithdrawalPercent();
    }

    public int getCyclesCount() {
        return cyclesCounter;
    }
}
