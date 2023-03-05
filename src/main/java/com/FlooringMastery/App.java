package com.FlooringMastery;

import com.FlooringMastery.controller.FlooringMasteryController;
import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import com.FlooringMastery.dto.Order;
import com.FlooringMastery.ui.UserIO;
import com.FlooringMastery.ui.UserIOConsoleImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FlooringMasteryPersistenceException { //undo somehow



        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.FlooringMastery");
        appContext.refresh();

        FlooringMasteryController controller = appContext.getBean("flooringMasteryController",
                                                                    FlooringMasteryController.class);


        Order order1 = new Order();
        order1.getState().setStateAbbreviation("boi");
        Order order2 = new Order();


        controller.runApp();



    }
}