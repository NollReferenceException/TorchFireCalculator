package main;

import java.util.ArrayList;

public class ArgumentsParser {
    private final int LOW_LIMIT_YEAR = Constants.LOW_LIMIT_YEAR;
    private final int HIGH_LIMIT_YEAR = Constants.HIGH_LIMIT_YEAR;
    private final String[] sourceArgs;

    public ArgumentsParser(String[] args) {
        sourceArgs = args;
    }

    private ArrayList<Integer> validateArguments(String[] args) {
        ArrayList<Integer> validatedArgs = new ArrayList<>();

        for (String arg : args) {
            int year = Integer.valueOf(arg);

            if ((LOW_LIMIT_YEAR <= year) && (year <= HIGH_LIMIT_YEAR)) {
                validatedArgs.add(year);
            } else {
                throw new RuntimeException(
                        String.format("Значение должно находиться в пределах: от %d до %d",
                                LOW_LIMIT_YEAR, HIGH_LIMIT_YEAR));
            }
        }

        return validatedArgs;
    }

    public ArrayList<Integer> getValidatedArguments() {
        return validateArguments(sourceArgs);
    }
}
