package com.FlooringMastery;

import com.FlooringMastery.controller.FlooringMasteryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {


        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.FlooringMastery");
        appContext.refresh();

        FlooringMasteryController controller = appContext.getBean("flooringMasteryController",
                                                                    FlooringMasteryController.class);

        controller.runApp();



    }
}