package com.FlooringMastery.service;

import com.FlooringMastery.dao.FlooringMasteryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryServiceImpl implements FlooringMasteryService{

    /**Declaration of the Dao*/
    private FlooringMasteryDao dao;

    /**
     * Constructor
     * @param dao FlooringMasteryDaoFileImpl
     */
    @Autowired
    public FlooringMasteryServiceImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }



}
