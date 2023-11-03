package main;

import java.util.HashMap;

public final class CoefficientsData {
    private final HashMap<Integer, Double> moexImpacts;
    private final HashMap<Integer, Double> inflationRates;

    public CoefficientsData(int retirementYear, int lifeYears) {
        moexImpacts = new HashMap<>();
        inflationRates = new HashMap<>();

        buildRatesData(retirementYear, lifeYears);
    }

    private void buildRatesData(int retirementYear, int lifeYears) {
        int startingIndexInflationRate = (Constants.INFLATION_RATE.length - 1) - lifeYears;
        int startingIndexMoexRate = (Constants.MOEX_RATE.length - 1) - lifeYears;

        double prevousMoexRate = Constants.MOEX_RATE[startingIndexMoexRate];

        for (int i = 0; i <= lifeYears; i++) {
            double currentInflationRate = (Constants.INFLATION_RATE[startingIndexInflationRate + (i)]
                    / Constants.MAX_PERCENT_VALUE);
            inflationRates.put((retirementYear + i), currentInflationRate);

            double currentMoexRate = Constants.MOEX_RATE[startingIndexMoexRate + (i)];
            double moexImpact = (currentMoexRate / prevousMoexRate);
            prevousMoexRate = currentMoexRate;
            moexImpacts.put(((retirementYear + i) - 1), moexImpact);
        }
    }

    public HashMap<Integer, Double> moexCoefficients() {
        return moexImpacts;
    }

    public HashMap<Integer, Double> inflationCoefficients() {
        return inflationRates;
    }
}
