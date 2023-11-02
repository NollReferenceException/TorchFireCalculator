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
            "2003, 6.0",
            "2004, 6.5",
            "2005, 4.5",
            "2006, 3.0",
            "2007, 3.0",
            "2008, 11.0",
            "2009, 6.5",
            "2010, 6.5",
            "2011, 9.0",
            "2012, 10.0",
            "2013, 11.5",
            "2014, 15.0",
            "2015, 16.5",
            "2016, 18.0",
            "2017, 23.5",
            "2018, 28.0",
            "2019, 33.5",
            "2020, 48.5",
            "2021, 100.0"


    }, ignoreLeadingAndTrailingWhitespace = true)
    void fullTest(int input, float output) {
        {
            FireCalculator fireCalculator = new FireCalculator(input, Constants.HIGH_LIMIT_YEAR);
            assertEquals(output, fireCalculator.getMaxWithdrawalPercent());
        }
    }
}