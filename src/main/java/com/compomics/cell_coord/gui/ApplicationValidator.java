/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui;

import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.spring.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Paola
 */
public class ApplicationValidator {

    /**
     * Start the Application Context.
     */
    public void startApplicationContext() {
        // try to get the application context
        ApplicationContext context = ApplicationContextProvider.getInstance().getApplicationContext();
        CellCoordController cellCoordController = (CellCoordController) context.getBean("cellCoordController", CellCoordController.class);
        // init main controller, if application context was got properly
        cellCoordController.init();
    }
}
