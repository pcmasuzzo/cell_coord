/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.summary;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.gui.summary.SummaryDataPanel;
import com.compomics.cell_coord.gui.table.model.SampleTableModel;
import com.compomics.cell_coord.gui.table.model.TrackSpotTableModel;
import com.compomics.cell_coord.gui.table.model.TrackTableModel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller class to compute first, quick data for cell tracks, and present
 * them to the user.
 *
 * @author Paola
 */
@Controller("summaryDataController")
public class SummaryDataController {

    private static final Logger LOG = Logger.getLogger(SummaryDataController.class);
    // model
    // view
    private SummaryDataPanel summaryDataPanel;
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initSummaryDataPanel();
    }

    /**
     * Initialize main view.
     */
    private void initSummaryDataPanel() {
        // create new object
        summaryDataPanel = new SummaryDataPanel();
        // format the tables
        JTableHeader samplesHeader = summaryDataPanel.getSamplesTable().getTableHeader();
        samplesHeader.setBackground(GuiUtils.getHeaderColor());
        samplesHeader.setFont(GuiUtils.getHeaderFont());
        samplesHeader.setReorderingAllowed(false);

        JTableHeader tracksHeader = summaryDataPanel.getTracksTable().getTableHeader();
        tracksHeader.setBackground(GuiUtils.getHeaderColor());
        tracksHeader.setFont(GuiUtils.getHeaderFont());
        tracksHeader.setReorderingAllowed(false);

        JTableHeader trackSpotsHeader = summaryDataPanel.getTrackSpotsTable().getTableHeader();
        trackSpotsHeader.setBackground(GuiUtils.getHeaderColor());
        trackSpotsHeader.setFont(GuiUtils.getHeaderFont());
        trackSpotsHeader.setReorderingAllowed(false);

        summaryDataPanel.getSamplesTable().setRowSelectionAllowed(true);
        summaryDataPanel.getSamplesTable().setColumnSelectionAllowed(false);
        summaryDataPanel.getSamplesTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        summaryDataPanel.getTracksTable().setRowSelectionAllowed(true);
        summaryDataPanel.getTracksTable().setColumnSelectionAllowed(false);
        summaryDataPanel.getTracksTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // if you click on a sample, the relative tracks are shown in another table
        summaryDataPanel.getSamplesTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = summaryDataPanel.getSamplesTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Sample selectedSample = loadTracksController.getSamples().get(selectedRow);
                        showTracksInTable(selectedSample);
                    }
                }
            }
        });

        // if you click on a track, the relative spots are shown in another table
        summaryDataPanel.getTracksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Sample selectedSample = loadTracksController.getSamples().get(summaryDataPanel.getSamplesTable().getSelectedRow());
                    int selectedRow = summaryDataPanel.getTracksTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Track selectedTrack = selectedSample.getTracks().get(selectedRow);
                        showSpotsInTable(selectedTrack);
                    }
                }
            }
        });
        
        // add view to parent controller
        loadTracksController.getMainFrame().getSummaryDataParentPanel().add(summaryDataPanel, gridBagConstraints);
    }

    /**
     * Show loaded samples in correspondent table. When the user clicks on a
     * sample, the tracks are shown in another table.
     */
    public void showSamplesInTable() {
        // get the table and set its model
        summaryDataPanel.getSamplesTable().setModel(new SampleTableModel(loadTracksController.getSamples()));
        summaryDataPanel.getSamplesTable().setRowSelectionInterval(0, 0);
    }

    /**
     * For a selected sample, show the tracks in the correspondent table.
     *
     * @param sample
     */
    private void showTracksInTable(Sample sample) {
        // get the table and set its model
        summaryDataPanel.getTracksTable().setModel(new TrackTableModel(sample));
        summaryDataPanel.getTracksTable().setRowSelectionInterval(0, 0);
    }

    /**
     * For a selected track, show track spots in the correspondent table.
     *
     * @param track
     */
    private void showSpotsInTable(Track track) {
        // get the table and set the model
        summaryDataPanel.getTrackSpotsTable().setModel(new TrackSpotTableModel(track));
    }
}
