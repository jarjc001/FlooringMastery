package com.FlooringMastery.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
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
        Scanner sc = new Scanner(System.in);
        //print prompt
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        Scanner sc = new Scanner(System.in);
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
        while (true) {
            try {
                String number = readString(prompt);
                if (number.equals("")) {
                    return BigDecimal.ZERO;
                }
                BigDecimal input = new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
                return input;
            } catch (Exception e) {
                this.print("Input not a number, Try again");
            }
        }

    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        LocalDate ld =  LocalDate.now();
        while (true) {
            try {
                String inputDate = readString(prompt + " (MM/DD/YYYY):");
                ld = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                break;

            } catch (Exception e) {
                this.print("Input not in date format (MM/DD/YYYY), Try again");
                //make runtime exception
            }
        }
        return ld;

    }


    @Override
    public boolean readYesOrNo(String prompt){
        String answer = this.readString(prompt).toUpperCase();

        if (answer.equals("Y") ){
            return true;
        }
        return false;

    }


}

