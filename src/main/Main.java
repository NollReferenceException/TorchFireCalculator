package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Введите год выхода на пенсию в пределах: от 2002 до 2021");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();

            ArgumentsParser argumentsParser = new ArgumentsParser(new String[]{s});
            ArrayList<Integer> validatedYears = argumentsParser.getValidatedArguments();

            FireCalculator fireCalculator = new FireCalculator(validatedYears.get(0));

            System.out.println(fireCalculator.getMaxWithdrawalPercent());
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }
}