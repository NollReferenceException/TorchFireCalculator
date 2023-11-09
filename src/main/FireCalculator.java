package main;

public class FireCalculator {
    private final float STEP_VALUE = 0.5F;
    private final double STARTING_CAPITAL_AMOUNT = Constants.MAX_PERCENT_VALUE;
    private final int retirementYear;
    private final CalculatorStatistic calculatorStatistic;
    private final CoefficientsData coefficientsData;

    public FireCalculator(int retirementYear) {
        this.retirementYear = retirementYear;
        calculatorStatistic = new CalculatorStatistic();
        coefficientsData = new CoefficientsData();
    }

    public double getMaxWithdrawalPercent() {
        return calculateMaxWithdrawalPercent();
    }

    private double calculateMaxWithdrawalPercent() {
        double calculatedPercent = calculateApproximatePercent();

        int maxIterationCount = (int) (Constants.MAX_PERCENT_VALUE / STEP_VALUE);
        int i = 0;
        boolean found = false;

        while (!found && (i < maxIterationCount)) {
            if (checkPercentForMaximality(calculatedPercent)) {
                found = true;
            } else {
                calculatedPercent -= STEP_VALUE;
            }

            i++;
        }

        calculatorStatistic.setChecksCount(i);

        return calculatedPercent;
    }

    private boolean checkPercentForMaximality(double maxPercentCandidate) {

        double capital = STARTING_CAPITAL_AMOUNT;

        int currYear = retirementYear;
        boolean valid = true;

        while (valid && (currYear < Constants.HIGH_LIMIT_YEAR)) {
            capital -= maxPercentCandidate;

            double inflationCoef = coefficientsData.inflationCoefficients().get(currYear + 1);
            maxPercentCandidate *= (1 + inflationCoef);

            double moexCoef = coefficientsData.moexCoefficients().get(currYear);
            capital *= moexCoef;

            if (capital < maxPercentCandidate) {
                valid = false;
            }

            currYear++;
        }

        return valid;
    }

    private double calculateApproximatePercent() {
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
