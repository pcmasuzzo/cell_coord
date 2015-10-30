/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.summary;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller class to show quick summary and visual inspection of cell
 * tracks, immediately after loading of files.
 *
 * @author Paola
 */
@Controller("summaryTracksController")
public class SummaryTracksController {

    private static final Logger LOG = Logger.getLogger(SummaryTracksController.class);
    // model
    // view
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    @Autowired
    private SummaryDataController summaryDataController;
    @Autowired
    private VisualizeTracksController visualizeTracksController;
    // services

    /**
     * Initialize controller.
     */
    public void init() {
        // init main view
        // init child controllers
        summaryDataController.init();
        visualizeTracksController.init();
    }

    /**
     * Show samples in table through child controller.
     */
    public void showSamplesInTable() {
        summaryDataController.showSamplesInTable();
    }

    /**
     * Get the loaded sample through the parent controller.
     *
     * @return
     */
    public List<Sample> getSamples() {
        return loadTracksController.getSamples();
    }

    /**
     * Get main frame through the parent controller.
     *
     * @return
     */
    public CellCoordFrame getMainFrame() {
        return loadTracksController.getMainFrame();
    }
}
