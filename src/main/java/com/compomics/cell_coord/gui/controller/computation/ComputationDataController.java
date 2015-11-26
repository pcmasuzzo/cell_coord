/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.computation.ComputationDataPanel;
import com.compomics.cell_coord.gui.table.model.TrackTableModel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller to do the actual computations.
 *
 * @author Paola
 */
@Controller("computationDataController")
public class ComputationDataController {

    // model
    // view
    private ComputationDataPanel computationDataPanel;
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
        initComputationDataPanel();
    }

    public ComputationDataPanel getComputationDataPanel() {
        return computationDataPanel;
    }

    /**
     * Initialize main view.
     */
    private void initComputationDataPanel() {
        // make new object
        computationDataPanel = new ComputationDataPanel();
        // format the tables
        JTableHeader samplesHeader = computationDataPanel.getSamplesTable().getTableHeader();
        samplesHeader.setBackground(GuiUtils.getHeaderColor());
        samplesHeader.setFont(GuiUtils.getHeaderFont());
        samplesHeader.setReorderingAllowed(false);

        JTableHeader tracksHeader = computationDataPanel.getTracksTable().getTableHeader();
        tracksHeader.setBackground(GuiUtils.getHeaderColor());
        tracksHeader.setFont(GuiUtils.getHeaderFont());
        tracksHeader.setReorderingAllowed(false);

        computationDataPanel.getSamplesTable().setRowSelectionAllowed(true);
        computationDataPanel.getSamplesTable().setColumnSelectionAllowed(false);
        computationDataPanel.getSamplesTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        computationDataPanel.getTracksTable().setRowSelectionAllowed(true);
        computationDataPanel.getTracksTable().setColumnSelectionAllowed(false);
        computationDataPanel.getTracksTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // if you click on a sample, the relative tracks are shown in another table
        computationDataPanel.getSamplesTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = computationDataPanel.getSamplesTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Sample selectedSample = computationMainController.getSamples().get(selectedRow);
                        showTracksInTable(selectedSample);
                    }
                }
            }
        });

        // if you click on a track, the relative spots are shown in another table
        computationDataPanel.getTracksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Sample selectedSample = computationMainController.getSamples().get(computationDataPanel.getSamplesTable().getSelectedRow());
                    int selectedRow = computationDataPanel.getTracksTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Track selectedTrack = selectedSample.getTracks().get(selectedRow);
                        showDataInTable(selectedTrack);
                    }
                }
            }
        });

        // add view to parent container
        computationMainController.getMainFrame().getComputationDataParentPanel().add(computationDataPanel, gridBagConstraints);
    }

    /**
     * For a selected sample, show the tracks in the correspondent table.
     *
     * @param sample
     */
    private void showTracksInTable(Sample sample) {
        // get the table and set its model
        computationDataPanel.getTracksTable().setModel(new TrackTableModel(sample));
        computationDataPanel.getTracksTable().setRowSelectionInterval(0, 0);
    }

    /**
     * For a selected track, show the data in the correspondent table. If the
     * data have not been computed, start the computations, else, get them from
     * the map.
     *
     * @param track
     */
    private void showDataInTable(Track track) {

    }
}
