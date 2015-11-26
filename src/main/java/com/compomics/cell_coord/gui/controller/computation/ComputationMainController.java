/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.gui.table.model.SampleTableModel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller class to do computations for tracks (and samples),and show them
 * to the user. The logic for the real computations and the visualization are
 * kept in two separate child controllers.
 *
 * @author Paola
 */
@Controller("computationMainController")
public class ComputationMainController {

    private static final Logger LOG = Logger.getLogger(ComputationMainController.class);
    // model
    // view
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    @Autowired
    private ComputationDataController computationDataController;
    @Autowired
    private ComputationGraphController computationGraphController;
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init child controllers
        computationDataController.init();
        computationGraphController.init();
    }

    /**
     * Get main frame through parent controller.
     *
     * @return
     */
    public CellCoordFrame getMainFrame() {
        return loadTracksController.getMainFrame();
    }

    /**
     * Get loaded samples through parent controller.
     *
     * @return
     */
    public List<Sample> getSamples() {
        return loadTracksController.getSamples();
    }

    /**
     * Show loaded samples in correspondent table. When the user clicks on a
     * sample, the tracks are shown in another table.
     */
    public void showSamplesInTable() {
        // get the table and set its model
        computationDataController.getComputationDataPanel().getSamplesTable().setModel(new SampleTableModel(loadTracksController.getSamples()));
        computationDataController.getComputationDataPanel().getSamplesTable().setRowSelectionInterval(0, 0);
    }
}
