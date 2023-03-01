package com.FlooringMastery.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class FlooringMasteryViewImpl implements FlooringMasteryView{

    /**Declaration of the UserIO*/
    private UserIO io;

    /**Constructor
     * @param io UserIOConsoleImpl */
    @Autowired
    public FlooringMasteryViewImpl(UserIO io) {
        this.io = io;
    }
}
