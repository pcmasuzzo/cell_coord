/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.load.LoadTracksPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A controller to load single cell tracks: downstream of this loading, all
 * computations are performed and rendered to the user.
 *
 * @author Paola
 */
@Component("loadTracksController")
public class LoadTracksController {

    // model
    // view
    private LoadTracksPanel loadTracksPanel;
    // child controllers
    // parent controller
    @Autowired
    private CellCoordController cellCoordController;

    /**
     * Initialize controller
     */
    public void init() {
        // init main view
    }

    /**
     * Initialize main view
     */
    private void initLoadTracksPane() {
        loadTracksPanel = new LoadTracksPanel();
    }
}
