package com.FlooringMastery.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {

    /**Scanner object to take in console inputs from user*/
    final private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        //print prompt
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        int num;
        while (true) {
            try {
                String stringValue = readString(prompt);
                // Get the input line, and parse into an int
                num = Integer.parseInt(stringValue);
                break;
            } catch (NumberFormatException e) {
                this.print("Input not a number, Try again");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        //the loop will keep repeating until the input int is within range
        do {
            result = readInt(prompt);
        }while(result<min || result>max);

        return result;

    }

    @Override
    public char readChar(String prompt) {
        //print prompt
        return readString(prompt).charAt(0);
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        BigDecimal input = sc.nextBigDecimal().setScale(2, RoundingMode.HALF_UP);

        return input;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        LocalDate ld =  LocalDate.now();
        try {
            String inputDate = readString(prompt + " (MM/DD/YYYY)");
            ld = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        }catch (Exception e){
            this.print("Input not in date format (MM/DD/YYYY), Try again");
        }
        return ld;

    }

    ///add ex
}

