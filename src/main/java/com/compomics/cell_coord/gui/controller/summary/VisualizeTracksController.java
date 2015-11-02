/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.summary;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.controller.load.LoadTracksController;
import com.compomics.cell_coord.gui.summary.VisualizeTracksPanel;
import com.compomics.cell_coord.utils.GuiUtils;
import com.compomics.cell_coord.utils.JFreeChartUtils;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ButtonGroup;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
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
    private Double[][] coordRanges;
    private Double[][] shiftedCoordRange;
    private List<XYSeriesCollection> xYSeriesCollections;
    // view
    private VisualizeTracksPanel visualizeTracksPanel;
    private List<ChartPanel> tracksChartPanels;
    // controller
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        xYSeriesCollections = new ArrayList<>();
        tracksChartPanels = new ArrayList<>();
        // init main view
        initVisualizeTracksPanel();
    }

    /**
     * Compute both the raw and the shifted-to-zero coordinates ranges.
     */
    public void computeRanges() {
        coordRanges = new Double[2][2];
        shiftedCoordRange = new Double[2][2];

        List<Double> xRawMinList = new ArrayList<>();
        List<Double> xRawMaxList = new ArrayList<>();
        List<Double> yRawMinList = new ArrayList<>();
        List<Double> yRawMaxList = new ArrayList<>();

        List<Double> xShiftMinList = new ArrayList<>();
        List<Double> xShiftMaxList = new ArrayList<>();
        List<Double> yShiftMinList = new ArrayList<>();
        List<Double> yShiftMaxList = new ArrayList<>();
        List<Sample> samples = loadTracksController.getSamples();
        for (Sample sample : samples) {
            List<Track> tracks = sample.getTracks();
            for (Track track : tracks) {
                Double[][] coordinateRanges = track.getCoordinateRanges();
                Double[][] shiftedCoordinateRanges = track.getShiftedCoordinateRanges();

                xRawMinList.add(coordinateRanges[0][0]);
                xRawMaxList.add(coordinateRanges[0][1]);
                yRawMinList.add(coordinateRanges[1][0]);
                yRawMaxList.add(coordinateRanges[1][1]);

                xShiftMinList.add(shiftedCoordinateRanges[0][0]);
                xShiftMaxList.add(shiftedCoordinateRanges[0][1]);
                yShiftMinList.add(shiftedCoordinateRanges[1][0]);
                yShiftMaxList.add(shiftedCoordinateRanges[1][1]);
            }
        }
        Double xRawMin = Collections.min(xRawMinList);
        Double xRawMax = Collections.max(xRawMaxList);
        Double yRawMin = Collections.min(yRawMinList);
        Double yRawMax = Collections.max(yRawMaxList);
        coordRanges[0] = new Double[]{xRawMin, xRawMax};
        coordRanges[1] = new Double[]{yRawMin, yRawMax};
        Double xShiftMin = Collections.min(xShiftMinList);
        Double xShiftMax = Collections.max(xShiftMaxList);
        Double yShiftMin = Collections.min(yShiftMinList);
        Double yShiftMax = Collections.max(yShiftMaxList);
        shiftedCoordRange[0] = new Double[]{xShiftMin, xShiftMax};
        shiftedCoordRange[1] = new Double[]{yShiftMin, yShiftMax};
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
                int nCols = Integer.parseInt((String) visualizeTracksPanel.getnColumnsComboBox().getSelectedItem());
                boolean useRawData = visualizeTracksPanel.getRawCoordRadioButton().isSelected();
                resetPlotLogic();
                generateDataForPlots(useRawData);
                // use the data to set the charts
                setChartsWithCollections(nCols);
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
                int nCols = Integer.parseInt((String) visualizeTracksPanel.getnColumnsComboBox().getSelectedItem());
                resetPlotLogic();
                generateDataForPlots(true);
                // use the data to set the charts
                setChartsWithCollections(nCols);
            }
        });

        // make the Rose plot: shift coordinates to origin (zero, zero)
        visualizeTracksPanel.getRosePlotRadioButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int nCols = Integer.parseInt((String) visualizeTracksPanel.getnColumnsComboBox().getSelectedItem());
                resetPlotLogic();
                generateDataForPlots(false);
                // use the data to set the charts
                setChartsWithCollections(nCols);
            }
        });

        // replot with a different number of columns
        visualizeTracksPanel.getnColumnsComboBox().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int nCols = Integer.parseInt((String) visualizeTracksPanel.getnColumnsComboBox().getSelectedItem());
                boolean useRawData = visualizeTracksPanel.getRawCoordRadioButton().isSelected();
                resetPlotLogic();
                generateDataForPlots(useRawData);
                // use the data to set the charts
                setChartsWithCollections(nCols);
            }
        });

        // add view to parent controller
        loadTracksController.getMainFrame().getVisualizeTracksParentPanel().add(visualizeTracksPanel, gridBagConstraints);
    }

    /**
     * This will reset the plot logic.
     */
    private void resetPlotLogic() {
        for (ChartPanel chartPanel : tracksChartPanels) {
            visualizeTracksPanel.getTracksGraphicsParentPanel().remove(chartPanel);
        }
        visualizeTracksPanel.getTracksGraphicsParentPanel().revalidate();
        visualizeTracksPanel.getTracksGraphicsParentPanel().repaint();
        if (!xYSeriesCollections.isEmpty()) {
            xYSeriesCollections.clear();
        }
        if (!tracksChartPanels.isEmpty()) {
            tracksChartPanels.clear();
        }
    }

    /**
     * @param useRawData
     * @return
     */
    private void generateDataForPlots(boolean useRawData) {
        for (Sample sample : loadTracksController.getSamples()) {
            XYSeriesCollection xYSeriesCollection = new XYSeriesCollection();
            // this is not the best way to fix this multiple locations issue, but for the moment fair enough !!
            int counter = 0;
            for (Track track : sample.getTracks()) {
                // the matrix to use is either the raw coordinates matrix or the shifted matrix
                Double[][] coordinatesMatrix;
                if (useRawData) {
                    coordinatesMatrix = track.getCoordinates();
                } else {
                    coordinatesMatrix = track.getShiftedCoordinates();
                }
                XYSeries xySeries = JFreeChartUtils.generateXYSeries(coordinatesMatrix);
                Long trackid = track.getTrackid();

                String key;
                key = "track " + trackid;
                // we check here if the collection already contains this key
                int seriesIndex = xYSeriesCollection.getSeriesIndex(key);
                if (seriesIndex == -1) {
                    key = "track " + trackid;
                } else {
                    // should be able to get the number of the series already present !!
                    key = "track " + trackid + ", " + (counter + 1);
                    counter++;
                }
                xySeries.setKey(key);
                xYSeriesCollection.addSeries(xySeries);
            }
            xYSeriesCollections.add(xYSeriesCollection);
        }
    }

    /**
     *
     * @param nCols
     */
    private void setChartsWithCollections(int nCols) {
        List<Sample> samples = loadTracksController.getSamples();
        int nPlots = xYSeriesCollections.size();
        for (int i = 0; i < nPlots; i++) {
            XYSeriesCollection collection = xYSeriesCollections.get(i);
            int numberTracks = collection.getSeries().size();
            String title = numberTracks + " tracks" + " - " + samples.get(i).getName();
            // create a chart for each plate condition
            JFreeChart coordinatesChart = ChartFactory.createXYLineChart(title, "x (µm)", "y (µm)", collection,
                      PlotOrientation.VERTICAL, false, true, false);
            // and a new chart panel as well
            ChartPanel coordinatesChartPanel = new ChartPanel(null);
            coordinatesChartPanel.setOpaque(false);

            // compute the constraints
            GridBagConstraints gridBagConstraints = getGridBagConstraints(nPlots, i, nCols);
            visualizeTracksPanel.getTracksGraphicsParentPanel().add(coordinatesChartPanel, gridBagConstraints);
            if (visualizeTracksPanel.getScaleAxesRadioButton().isSelected()) {

            }
            coordinatesChartPanel.setChart(coordinatesChart);

            // add the chart panels to the list
            tracksChartPanels.add(coordinatesChartPanel);
            visualizeTracksPanel.getTracksGraphicsParentPanel().revalidate();
            visualizeTracksPanel.getTracksGraphicsParentPanel().repaint();
        }
    }

    /**
     * Given the amount of plots to render, and the index of the current plot,
     * as well as the number of columns to use, get the appropriate
     * GridBagConstraints.
     *
     * @param nPlots
     * @param index
     * @param nCols
     * @return the GridBagConstraints
     */
    private GridBagConstraints getGridBagConstraints(int nPlots, int index, int nCols) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        int nRows = (int) Math.ceil(nPlots / nCols);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0 / nCols;
        gridBagConstraints.weighty = 1.0 / nRows;
        gridBagConstraints.gridy = (int) Math.floor(index / nCols);
        if (index < nCols) {
            gridBagConstraints.gridx = index;
        } else {
            gridBagConstraints.gridx = index - ((index / nCols) * nCols);
        }
        return gridBagConstraints;
    }

}
