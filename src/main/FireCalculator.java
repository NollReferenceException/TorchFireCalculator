package main;

import java.util.ArrayList;

public class FireCalculator {
    private final float STEP_VALUE = 0.5F;
    private final double STARTING_CAPITAL_AMOUNT = Constants.MAX_PERCENT_VALUE;

    private final int lifeYears;
    private final int startingIndexInflationRate;
    private final int startingIndexMoexRate;
    private final CalculatorStatistic calculatorStatistic;

    public FireCalculator(int chillYear, int highLimitYear) {
        lifeYears = (highLimitYear + 1) - chillYear;
        startingIndexInflationRate = (Constants.INFLATION_RATE.length - 1) - lifeYears;
        startingIndexMoexRate = (Constants.MOEX_RATE.length - 1) - lifeYears;
        calculatorStatistic = new CalculatorStatistic();
    }

    public double getMaxWithdrawalPercent() {
        return calculateMaxWithdrawalPercent();
    }

    private double calculateMaxWithdrawalPercent() {
        if (lifeYears == 1) {
            return Constants.MAX_PERCENT_VALUE;
        }

        RatesData ratesData = buildRatesData();
        double calculatedPercent = calculateApproximatePercent(ratesData);

        boolean found;
        while (true) {
            found = checkPercentForMaximality(calculatedPercent, ratesData);

            if (found) {
                break;
            }

            calculatedPercent -= STEP_VALUE;
        }

        return calculatedPercent;
    }

    private RatesData buildRatesData() {
        double currentMoexRate;
        ArrayList<Double> moexImpacts = new ArrayList<>();
        ArrayList<Double> inflationRates = new ArrayList<>();

        if (startingIndexMoexRate == 0) {
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate];
        } else {
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate - 1];
        }

        for (int i = 0; i < lifeYears; i++) {
            double currentInflationRate = (Constants.INFLATION_RATE[startingIndexInflationRate + i]
                    / Constants.MAX_PERCENT_VALUE);
            inflationRates.add(currentInflationRate);

            double prevousMoexRate = currentMoexRate;
            currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate + i];
            double moexImpact = (currentMoexRate / prevousMoexRate);
            moexImpacts.add(moexImpact);
        }

        return new RatesData(moexImpacts, inflationRates);
    }

    private double calculateApproximatePercent(RatesData ratesData) {
        double capital = STARTING_CAPITAL_AMOUNT;
        double approximatePercent = Constants.MAX_PERCENT_VALUE;

        for (int i = 0; i < lifeYears; i++) {
            if (i == (lifeYears - 1)) {
                approximatePercent = (capital / (lifeYears));
            }

            capital *= (1 - ratesData.inflationRates().get(i));
            capital *= ratesData.moexImpacts().get(i);
        }

        approximatePercent = Math.ceil(approximatePercent);

        return approximatePercent;
    }

    private boolean checkPercentForMaximality(double maxPercentCandidate, RatesData ratesData) {
        calculatorStatistic.incrementChecksCount();

        double capital = STARTING_CAPITAL_AMOUNT;

        for (int i = 0; i < lifeYears; i++) {
            capital -= maxPercentCandidate;

            if (capital < 0) {
                return false;
            }

            maxPercentCandidate *= (1 + ratesData.inflationRates().get(i));
            capital *= ratesData.moexImpacts().get(i);
        }

        return true;
    }

    public CalculatorStatistic getStatistic() {
        return calculatorStatistic;
    }
}
