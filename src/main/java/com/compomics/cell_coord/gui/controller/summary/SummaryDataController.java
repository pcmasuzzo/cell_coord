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
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.JTableHeader;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
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
    private BindingGroup bindingGroup;
    private ObservableList<Sample> samplesBindingList;
    private ObservableList<Track> tracksBindingList;
    private ObservableList<TrackSpot> trackSpotsBindingList;
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
        bindingGroup = new BindingGroup();
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

        samplesBindingList = ObservableCollections.observableList(summaryTracksController.getSamples());
        tracksBindingList = ObservableCollections.observableList(new ArrayList<Track>());
        trackSpotsBindingList = ObservableCollections.observableList(new ArrayList<TrackSpot>());

        // add view to parent controller
        summaryTracksController.getMainFrame().getSummaryDataParentPanel().add(summaryDataPanel, gridBagConstraints);
    }

    /**
     * Show loaded samples in correspondent table. When the user clicks on a
     * sample, the tracks are shown in another table.
     */
    // MOST LIKELY I HAVE TO POPULATE THIS TABLE MANUALLY AND THE OTHER 2 WITH AN AUTOMATIC SWING BINDING.
    public void showSamplesInTable() {
        // table binding
        JTableBinding samplesTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, samplesBindingList, summaryDataPanel.getSamplesTable());
        // add column bindings
        JTableBinding.ColumnBinding columnBinding = samplesTableBinding.addColumnBinding(ELProperty.create("${tracks}"));
        columnBinding.setColumnName("nr tracks");
        columnBinding.setEditable(false);
//        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(samplesTableBinding);
        bindingGroup.bind();
    }

    public void showTracksInTable() {

    }

    public void showSpotsInTable() {

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
