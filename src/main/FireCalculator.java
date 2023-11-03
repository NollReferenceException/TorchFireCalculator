package main;

import java.util.HashMap;

public class FireCalculator {
    private final float STEP_VALUE = 0.5F;
    private final double STARTING_CAPITAL_AMOUNT = Constants.MAX_PERCENT_VALUE;
    private final int retirementYear;
    private final int lifeYears;
    private final CalculatorStatistic calculatorStatistic;

    public FireCalculator(int retirementYear) {
        this.retirementYear = retirementYear;
        lifeYears = (Constants.HIGH_LIMIT_YEAR + 1) - retirementYear;
        calculatorStatistic = new CalculatorStatistic();
    }

    public double getMaxWithdrawalPercent() {
        return calculateMaxWithdrawalPercent();
    }

    private double calculateMaxWithdrawalPercent() {
        if (lifeYears == 1) {
            return Constants.MAX_PERCENT_VALUE;
        }

        RatesData ratesData = new RatesData(retirementYear, lifeYears);
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

    private boolean checkPercentForMaximality(double maxPercentCandidate, RatesData ratesData) {
        calculatorStatistic.incrementChecksCount();

        double capital = STARTING_CAPITAL_AMOUNT;

        for (int i = 0; i < lifeYears; i++) {
            capital -= maxPercentCandidate;

            if (capital < 0) {
                return false;
            }

            double currentinflationRate = ratesData.inflationRates().get(retirementYear + i + 1);
            maxPercentCandidate *= (1 + currentinflationRate);

            double currentmoexImpacts = ratesData.moexImpacts().get(retirementYear + i);
            capital *= currentmoexImpacts;
        }

        return true;
    }

    private double calculateApproximatePercent(RatesData ratesData) {
        double capital = STARTING_CAPITAL_AMOUNT;
        double approximatePercent = Constants.MAX_PERCENT_VALUE;

        for (int i = 0; i < lifeYears; i++) {
            if (i == (lifeYears - 1)) {
                approximatePercent = (capital / (lifeYears));
            }

            double currentinflationRate = ratesData.inflationRates().get(retirementYear + i);
            capital *= (1 - currentinflationRate);

            double currentmoexImpacts = ratesData.moexImpacts().get(retirementYear + i);
            capital *= currentmoexImpacts;
        }

        approximatePercent = Math.ceil(approximatePercent);

        return approximatePercent;
    }

    public CalculatorStatistic getStatistic() {
        return calculatorStatistic;
    }
}
