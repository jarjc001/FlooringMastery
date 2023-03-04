package com.FlooringMastery;

import com.FlooringMastery.controller.FlooringMasteryController;
import com.FlooringMastery.dao.FlooringMasteryPersistenceException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws FlooringMasteryPersistenceException { //undo somehow


        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.FlooringMastery");
        appContext.refresh();

        FlooringMasteryController controller = appContext.getBean("flooringMasteryController",
                                                                    FlooringMasteryController.class);



        controller.runApp();



    }
}