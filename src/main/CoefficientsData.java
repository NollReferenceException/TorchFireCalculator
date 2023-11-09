package main;

import java.util.HashMap;

public class CoefficientsData {
    private final HashMap<Integer, Double> moexCoefficients;
    private final HashMap<Integer, Double> inflationCoefficients;

    public CoefficientsData() {
        moexCoefficients = new HashMap<>();
        inflationCoefficients = new HashMap<>();

        buildRatesData();
    }

    public void buildRatesData() {
        int yearCount = Constants.HIGH_LIMIT_YEAR - Constants.LOW_LIMIT_YEAR;

        double prevousMoexRate = Constants.MOEX_RATE[0];

        for (int i = 0; i <= yearCount; i++) {
            double currentInflationRate = (Constants.INFLATION_RATE[(i)]
                    / Constants.MAX_PERCENT_VALUE);
            inflationCoefficients.put(Constants.LOW_LIMIT_YEAR + i, currentInflationRate);

            double currentMoexRate = Constants.MOEX_RATE[(i)];
            double moexImpact = (currentMoexRate / prevousMoexRate);
            prevousMoexRate = currentMoexRate;
            moexCoefficients.put((Constants.LOW_LIMIT_YEAR + i) - 1, moexImpact);
        }
    }

    public HashMap<Integer, Double> moexCoefficients() {
        return moexCoefficients;
    }

    public HashMap<Integer, Double> inflationCoefficients() {
        return inflationCoefficients;
    }
}
