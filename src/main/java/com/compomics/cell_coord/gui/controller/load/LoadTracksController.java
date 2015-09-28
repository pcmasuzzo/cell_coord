/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.load.LoadTracksPanel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
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
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initLoadTracksPanel();
    }

    /**
     * Initialize main view
     */
    private void initLoadTracksPanel() {
        // new object
        loadTracksPanel = new LoadTracksPanel();
        // add radio buttons to a group: only one selection allowed!
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(loadTracksPanel.getLoadSparseFilesRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadPlateRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadTrackMateRadioButton());

        cellCoordController.getCellCoordFrame().getLoadTracksParentPanel().add(loadTracksPanel, gridBagConstraints);

        /**
         * Next action: read the selection and call the correspondent child
         * controller.
         */
        loadTracksPanel.getNextButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadTracksPanel.getLoadSparseFilesRadioButton().isSelected()) {
                // call the sparse files controller
                } else if (loadTracksPanel.getLoadPlateRadioButton().isSelected()) {
                    // call the plate view controller
                } else {
                    // call the TrackMate controller
                }
            }
        });
    }
}
