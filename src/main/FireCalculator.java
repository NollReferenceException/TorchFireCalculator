package main;

public class FireCalculator {
    private final float STEP_VALUE = 0.5F;
    private final double STARTING_CAPITAL_AMOUNT = Constants.MAX_PERCENT_VALUE;
    private final int retirementYear;
    private final CalculatorStatistic calculatorStatistic;

    public FireCalculator(int retirementYear) {
        this.retirementYear = retirementYear;
        calculatorStatistic = new CalculatorStatistic();
    }

    public double getMaxWithdrawalPercent() {
        return calculateMaxWithdrawalPercent();
    }

    private double calculateMaxWithdrawalPercent() {
        CoefficientsData coefficientsData = new CoefficientsData();
        coefficientsData.buildRatesData();

        double calculatedPercent = calculateApproximatePercent(coefficientsData);

        boolean found;
        while (true) {
            found = checkPercentForMaximality(calculatedPercent, coefficientsData);

            if (found) {
                break;
            }

            calculatedPercent -= STEP_VALUE;
        }

        return calculatedPercent;
    }

    private boolean checkPercentForMaximality(double maxPercentCandidate, CoefficientsData coefficientsData) {
        calculatorStatistic.incrementChecksCount();

        double capital = STARTING_CAPITAL_AMOUNT;

        for (int currYear = retirementYear; currYear < Constants.HIGH_LIMIT_YEAR; currYear++) {
            capital -= maxPercentCandidate;

            double inflationCoef = coefficientsData.inflationCoefficients().get(currYear + 1);
            maxPercentCandidate *= (1 + inflationCoef);

            double moexCoef = coefficientsData.moexCoefficients().get(currYear);
            capital *= moexCoef;

            if (capital < maxPercentCandidate) {
                return false;
            }
        }

        return true;
    }

    private double calculateApproximatePercent(CoefficientsData coefficientsData) {
        double capital = STARTING_CAPITAL_AMOUNT;

        for (int currYear = retirementYear; currYear < Constants.HIGH_LIMIT_YEAR; currYear++) {
            double moexCoef = coefficientsData.moexCoefficients().get(currYear);
            double inflationCoef = coefficientsData.inflationCoefficients().get(currYear);

            capital *= (moexCoef * (1 - inflationCoef));
        }

        double approximatePercent = (capital / ((Constants.HIGH_LIMIT_YEAR + 1) - retirementYear));
        approximatePercent = (int) approximatePercent;

        return approximatePercent;
    }

    public CalculatorStatistic getStatistic() {
        return calculatorStatistic;
    }
}
