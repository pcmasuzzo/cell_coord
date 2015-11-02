/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller;

import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.utils.GuiUtils;
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
        cellCoordFrame.setVisible(true);
        // at starter, show main panel with logo    
        getCardLayout().first(cellCoordFrame.getTopPanel());
        onCardSwitch();
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
     * Update the label with the info for the user.
     *
     * @param info
     */
    public void updateInfoLabel(String info) {
        cellCoordFrame.getInfoLabel().setText(info);
    }

    /**
     * Initialize main frame.
     */
    private void initMainFrame() {

        /**
         * Add action listeners.
         */
        // On 'Start' Action: show next page, i.e. load cell tracking file(s).
        cellCoordFrame.getStartButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // first action: loading tracks
                getCardLayout().show(cellCoordFrame.getTopPanel(), cellCoordFrame.getLoadTracksParentPanel().getName());
                onCardSwitch();
            }
        });

        // On 'Next' Action: use the card switch
        cellCoordFrame.getNextButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getCardLayout().next(cellCoordFrame.getTopPanel());
                onCardSwitch();
            }
        });

        // On 'Previous' Action: use the card switch
        cellCoordFrame.getPreviousButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getCardLayout().previous(cellCoordFrame.getTopPanel());
                onCardSwitch();
            }
        });

        // On 'Cancel' Action
    }

    /**
     * Get the card layout from the background panel of CellMissy frame
     *
     * @return
     */
    private CardLayout getCardLayout() {
        return (CardLayout) cellCoordFrame.getTopPanel().getLayout();
    }

    /**
     * On switching card: control components and render the right view.
     */
    private void onCardSwitch() {
        String currentCardName = GuiUtils.getCurrentCardName(cellCoordFrame.getTopPanel());
        switch (currentCardName) {
            case "homePanel":
                cellCoordFrame.getStartButton().setEnabled(true);
                cellCoordFrame.getNextButton().setEnabled(false);
                cellCoordFrame.getPreviousButton().setEnabled(false);
                cellCoordFrame.getCancelButton().setEnabled(false);
                updateInfoLabel("Please click the 'Start' button.");
                break;
            case "loadTracksParentPanel":
                cellCoordFrame.getStartButton().setEnabled(false);
                updateInfoLabel("Load your cell tracking file(s) here.");
                break;
            case "summaryTracksPanel":
                cellCoordFrame.getPreviousButton().setEnabled(true);
                updateInfoLabel("A quick summary and visualization of the loaded cell tracks.");
                // WARNING HERE!!!!
                loadTracksController.preprocessSamples();
                break;
        }
    }
}
