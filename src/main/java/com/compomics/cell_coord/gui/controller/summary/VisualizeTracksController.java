/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.summary;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.gui.summary.VisualizeTracksPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller component to visualize the cell tracks.
 *
 * @author Paola
 */
@Controller("visualizeTracksController")
public class VisualizeTracksController {

    // model
    private Double[][] rawTrackCoordRange;
    private Double[][] shiftedTrackCoordRange;
    // view
    private VisualizeTracksPanel visualizeTracksPanel;
    private List<ChartPanel> tracksChartPanels;
    // controller
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;

    /**
     * Initialize controller
     */
    public void init() {
        tracksChartPanels = new ArrayList<>();
        // init main view
        initVisualizeTracksPanel();
    }

    /**
     * Initialize main view
     */
    private void initVisualizeTracksPanel() {
        // create new view object
        visualizeTracksPanel = new VisualizeTracksPanel();
        // add radiobuttons to a button group
        ButtonGroup scaleAxesButtonGroup = new ButtonGroup();
        scaleAxesButtonGroup.add(visualizeTracksPanel.getDoNotScaleAxesRadioButton());
        scaleAxesButtonGroup.add(visualizeTracksPanel.getScaleAxesRadioButton());
        visualizeTracksPanel.getDoNotScaleAxesRadioButton().setSelected(true);
        // another button group for the shifted/unshifted coordinates
        ButtonGroup shiftedCoordinatesButtonGroup = new ButtonGroup();
        shiftedCoordinatesButtonGroup.add(visualizeTracksPanel.getRawCoordRadioButton());
        shiftedCoordinatesButtonGroup.add(visualizeTracksPanel.getRosePlotRadioButton());
        visualizeTracksPanel.getRawCoordRadioButton().setSelected(true);

        /**
         * Action Listeners.
         */
        // do not scale the axes
        visualizeTracksPanel.getDoNotScaleAxesRadioButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // scale the axes (use a common scale for all the samples)
        visualizeTracksPanel.getScaleAxesRadioButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // plot the raw tracks
        visualizeTracksPanel.getRawCoordRadioButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // make the Rose plot: shift coordinates to origin (zero, zero)
        visualizeTracksPanel.getRosePlotRadioButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    /**
     * Compute both the raw and the shifted-to-zero coordinates ranges.
     */
    private void computeRanges() {
        List<Sample> samples = loadTracksController.getSamples();
        for (Sample sample : samples) {
            List<Track> tracks = sample.getTracks();
            for (Track track : tracks) {
                List<TrackSpot> trackSpots = track.getTrackSpots();
                for (TrackSpot trackSpot : trackSpots) {
                    double x = trackSpot.getX();
                    double y = trackSpot.getY();
                }
            }
        }
    }
}
