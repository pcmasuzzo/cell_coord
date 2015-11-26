/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.gui.computation.ComputationGraphPanel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller to visualize the computations (render plots to the user).
 *
 * @author Paola
 */
@Controller("computationGraphController")
public class ComputationGraphController {

    // model
    // view
    private ComputationGraphPanel computationGraphPanel;
    // parent controller
    @Autowired
    private ComputationMainController computationMainController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initComputationGraphPanel();
    }

    /**
     * Initialize main view.
     */
    private void initComputationGraphPanel() {
        // make new object
        computationGraphPanel = new ComputationGraphPanel();

        // add view to parent container
        computationMainController.getMainFrame().getComputationGraphParentPanel().add(computationGraphPanel, gridBagConstraints);
    }
}
