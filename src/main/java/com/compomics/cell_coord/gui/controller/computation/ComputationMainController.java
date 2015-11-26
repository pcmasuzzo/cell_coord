/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.gui.table.model.SampleTableModel;
import com.compomics.cell_coord.gui.table.model.TrackTableModel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
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
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init some view
        initMainView();
        // init child controllers
        computationDataController.init();
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
     * Get the currently selected sample.
     *
     * @return
     */
    public Sample getSelectedSample() {
        return getSamples().get(getMainFrame().getSamplesTable().getSelectedRow());
    }

    /**
     * Show loaded samples in correspondent table. When the user clicks on a
     * sample, the tracks are shown in another table.
     */
    public void showSamplesInTable() {
        // get the table and set its model
        getMainFrame().getSamplesTable().setModel(new SampleTableModel(loadTracksController.getSamples()));
    }

    /**
     * Initialize some GUI components.
     */
    private void initMainView() {
        // format the tables
        JTableHeader samplesHeader = getMainFrame().getSamplesTable().getTableHeader();
        samplesHeader.setBackground(GuiUtils.getHeaderColor());
        samplesHeader.setFont(GuiUtils.getHeaderFont());
        samplesHeader.setReorderingAllowed(false);

        JTableHeader tracksHeader = getMainFrame().getTracksTable().getTableHeader();
        tracksHeader.setBackground(GuiUtils.getHeaderColor());
        tracksHeader.setFont(GuiUtils.getHeaderFont());
        tracksHeader.setReorderingAllowed(false);

        getMainFrame().getSamplesTable().setRowSelectionAllowed(true);
        getMainFrame().getSamplesTable().setColumnSelectionAllowed(false);
        getMainFrame().getSamplesTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        getMainFrame().getTracksTable().setRowSelectionAllowed(true);
        getMainFrame().getTracksTable().setColumnSelectionAllowed(false);
        getMainFrame().getTracksTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // if you click on a sample, the relative tracks are shown in another table
        getMainFrame().getSamplesTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = getMainFrame().getSamplesTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Sample selectedSample = getSamples().get(selectedRow);
                        showTracksInTable(selectedSample);
                        computationDataController.computeSample(selectedSample);
                        // call child controller to show sample data in table
                        computationDataController.showSampleData(selectedSample);
                        // call child controller to plot whatever we need to plot
                    }
                }
            }
        });

        // if you click on a track, the relative spots are shown in another table
        getMainFrame().getTracksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Sample selectedSample = getSamples().get(getMainFrame().getSamplesTable().getSelectedRow());
                    int selectedRow = getMainFrame().getTracksTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Track selectedTrack = selectedSample.getTracks().get(selectedRow);
                        // call child controller to show track data in table
                        computationDataController.showTrackData(selectedTrack);
                        // call child controller to plot whatever we need to plot
                        computationDataController.plotTrackData(selectedTrack);
                    }
                }
            }
        });
    }

    /**
     * For a selected sample, show the tracks in the correspondent table.
     *
     * @param sample
     */
    public void showTracksInTable(Sample sample) {
        // get the table and set its model
        getMainFrame().getTracksTable().setModel(new TrackTableModel(sample));
    }
}
