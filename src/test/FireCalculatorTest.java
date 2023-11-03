package test;

import main.Constants;
import main.FireCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FireCalculatorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2002, 9.0",
            "2003, 7.0",
            "2004, 8.0",
            "2005, 5.0",
            "2006, 3.5",
            "2007, 3.5",
            "2008, 13.5",
            "2009, 7.5",
            "2010, 7.0",
            "2011, 10.0",
            "2012, 11.0",
            "2013, 13.0",
            "2014, 18.5",
            "2015, 20.5",
            "2016, 20.5",
            "2017, 27.0",
            "2018, 32.5",
            "2019, 36.0",
            "2020, 52.5",
            "2021, 100.0"


    }, ignoreLeadingAndTrailingWhitespace = true)
    public void fullTest(int input, float output) {
        {
            FireCalculator fireCalculator =
                    new FireCalculator(input, Constants.HIGH_LIMIT_YEAR);

            assertEquals(output, fireCalculator.getMaxWithdrawalPercent());

            System.out.printf("%d : количество проходов - %d%n",
                    input, fireCalculator.getStatistic().getChecksCount());
        }
    }
}