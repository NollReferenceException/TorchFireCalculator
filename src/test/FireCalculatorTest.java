package test;

import main.Constants;
import main.FireCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FireCalculatorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2002, 7.5",
            "2003, 9.5",
            "2004, 7.0",
            "2005, 8.0",
            "2006, 5.0",
            "2007, 3.5",
            "2008, 3.5",
            "2009, 14.0",
            "2010, 8.0",
            "2011, 7.5",
            "2012, 10.5",
            "2013, 12.0",
            "2014, 14.0",
            "2015, 20.0",
            "2016, 22.5",
            "2017, 23.0",
            "2018, 31.5",
            "2019, 40.5",
            "2020, 52.0",
            "2021, 100.0"


    }, ignoreLeadingAndTrailingWhitespace = true)
    void fullTest(int input, float output) {
        {
            FireCalculator fireCalculator = new FireCalculator(input, Constants.HIGH_LIMIT_YEAR);
            assertEquals(output, fireCalculator.getMaxWithdrawalPercent());
        }
    }
}