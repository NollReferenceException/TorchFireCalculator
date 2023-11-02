package main;

import java.util.ArrayList;

public class ArgumentsParser {
    private final int lowLimitYear = Constants.LOW_LIMIT_YEAR;
    private final int highLimitYear = Constants.HIGH_LIMIT_YEAR;
    private final String[] srcArgs;

    public ArgumentsParser(String[] args) {
        srcArgs = args;
    }

    private ArrayList<Integer> validateArguments(String[] args) {
        ArrayList<Integer> validatedArgs = new ArrayList<>();

        for (String arg : args) {
            int year = Integer.valueOf(arg);

            if (lowLimitYear <= year && year <= highLimitYear) {
                validatedArgs.add(year);
            } else {
                throw new RuntimeException(
                        String.format("Значение должно находиться в пределах: от %d до %d", lowLimitYear, highLimitYear));
            }
        }

        return validatedArgs;
    }

    public ArrayList<Integer> getValidatedArguments() {
        return validateArguments(srcArgs);
    }
}
