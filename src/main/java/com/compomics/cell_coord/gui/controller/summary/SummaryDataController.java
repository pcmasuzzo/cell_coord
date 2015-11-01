/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.summary;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.gui.summary.SummaryDataPanel;
import com.compomics.cell_coord.gui.table.model.SampleTableModel;
import com.compomics.cell_coord.gui.table.model.TrackSpotTableModel;
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
    private SummaryTracksController summaryTracksController;
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
        samplesHeader.setReorderingAllowed(false);

        JTableHeader tracksHeader = summaryDataPanel.getTracksTable().getTableHeader();
        tracksHeader.setBackground(GuiUtils.getHeaderColor());
        tracksHeader.setReorderingAllowed(false);

        JTableHeader trackSpotsHeader = summaryDataPanel.getTrackSpotsTable().getTableHeader();
        trackSpotsHeader.setBackground(GuiUtils.getHeaderColor());
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
                        Sample selectedSample = summaryTracksController.getSamples().get(selectedRow);
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
                    Sample selectedSample = summaryTracksController.getSamples().get(summaryDataPanel.getSamplesTable().getSelectedRow());
                    int selectedRow = summaryDataPanel.getTracksTable().getSelectedRow();
                    if (selectedRow != -1) {
                        Track selectedTrack = selectedSample.getTracks().get(selectedRow);
                        showSpotsInTable(selectedTrack);
                    }
                }
            }
        });

        // add view to parent controller
        summaryTracksController.getMainFrame().getSummaryDataParentPanel().add(summaryDataPanel, gridBagConstraints);
    }

    /**
     * Show loaded samples in correspondent table. When the user clicks on a
     * sample, the tracks are shown in another table.
     */
    public void showSamplesInTable() {
        // get the table and set its model
        summaryDataPanel.getSamplesTable().setModel(new SampleTableModel(summaryTracksController.getSamples()));
    }

    /**
     * For a selected sample, show the tracks in the correspondent table.
     *
     * @param sample
     */
    private void showTracksInTable(Sample sample) {
        // get the table and set its model
        summaryDataPanel.getTracksTable().setModel(new TrackTableModel(sample));
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

    /**
     *
     * @param track
     */
    private void prepareCoordinates(Track track) {
        List<TrackSpot> trackSpots = track.getTrackSpots();
        double[][] coordinates = new double[trackSpots.size()][2];
        for (int i = 0; i < coordinates.length; i++) {
            TrackSpot trackSpot = trackSpots.get(i);
            double x = trackSpot.getX();
            double y = trackSpot.getY();
            coordinates[i] = new double[]{x, y};
        }
        track.setCoordinates(coordinates);
    }

    /**
     *
     * @param track
     */
    private void prepareShiftedCoordinates(Track track) {
        double[][] coordinates = track.getCoordinates();
        double[][] shiftedCoordinates = new double[coordinates.length][2];
        double[] firstCoordinates = coordinates[0];
        double x0 = firstCoordinates[0];
        double y0 = firstCoordinates[1];
        for (int row = 0; row < coordinates.length; row++) {
            double currentX = coordinates[row][0];
            double currentY = coordinates[row][1];
            shiftedCoordinates[row] = new double[]{currentX - x0, currentY - y0};
        }
        track.setShiftedCoordinates(shiftedCoordinates);
    }

    /**
     *
     * @param sample
     */
    private void computeRangesPerSample(Sample sample) {
        List<Track> tracks = sample.getTracks();
        double xMin;
        double yMin;
        double xMax;
        double yMax;
        for (Track track : tracks) {
            List<TrackSpot> trackSpots = track.getTrackSpots();
            for (TrackSpot trackSpot : trackSpots) {
                double x = trackSpot.getX();
                double y = trackSpot.getY();

            }
        }
    }
}
