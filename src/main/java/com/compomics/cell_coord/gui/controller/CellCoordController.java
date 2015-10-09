/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller;

import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A controller for the main logic of the software. (This class is the main
 * controller).
 *
 * @author Paola
 */
@Controller("cellCoordController")
public class CellCoordController {

    private static final Logger LOG = Logger.getLogger(CellCoordController.class);

    // model 
    // view
    // main view
    private CellCoordFrame cellCoordFrame;
    // subviews
    // child controllers
    @Autowired
    private LoadTracksController loadTracksController;

    /**
     * Initialize main controller.
     */
    public void init() {
        //set uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOG.error(e.getMessage(), e);
                showMessage("Unexpected error: " + e.getMessage() + ", application will exit", "unexpected error", JOptionPane.ERROR_MESSAGE);
                // exit the application
                System.exit(1);
            }
        });

        // new frame instance
        cellCoordFrame = new CellCoordFrame();
        cellCoordFrame.setTitle("Cell_Coord");
        cellCoordFrame.setVisible(true);
        // at starter, show main panel with logo    
        getCardLayout().first(cellCoordFrame.getBackgroundPanel());
        // init child controllers
        loadTracksController.init();
        initMainFrame();
    }

    /**
     * Get the main frame
     *
     * @return
     */
    public CellCoordFrame getCellCoordFrame() {
        return cellCoordFrame;
    }

    /**
     * Show a message to the user.
     *
     * @param message
     * @param title
     * @param messageType
     */
    public void showMessage(String message, String title, Integer messageType) {
        JOptionPane.showMessageDialog(cellCoordFrame.getContentPane(), message, title, messageType);
    }

    /**
     * Initialize main frame.
     */
    private void initMainFrame() {

        /**
         * Add action listeners.
         */
        // On start: show next page: load tracks.
        cellCoordFrame.getStartButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // first action: loading tracks
                getCardLayout().show(cellCoordFrame.getBackgroundPanel(), cellCoordFrame.getLoadTracksParentPanel().getName());
            }
        });
    }

    /**
     * Get the card layout from the background panel of CellMissy frame
     *
     * @return
     */
    private CardLayout getCardLayout() {
        return (CardLayout) cellCoordFrame.getBackgroundPanel().getLayout();
    }

}
