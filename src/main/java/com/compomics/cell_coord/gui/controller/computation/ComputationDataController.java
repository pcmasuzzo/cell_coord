/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.computation.SampleOperator;
import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.computation.ComputationDataPanel;
import com.compomics.cell_coord.gui.table.model.SampleDataTableModel;
import com.compomics.cell_coord.gui.table.model.TrackDataTableModel;
import com.compomics.cell_coord.utils.ComputationUtils;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.JTableHeader;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller to do the actual computations.
 *
 * @author Paola
 */
@Controller("computationDataController")
public class ComputationDataController {

    private static final Logger LOG = Logger.getLogger(ComputationDataController.class);
    // model
    private List<Sample> computedSamples;
    // view
    private ComputationDataPanel computationDataPanel;
    // parent controller
    @Autowired
    private ComputationMainController computationMainController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;
    @Autowired
    private SampleOperator sampleOperator;

    /**
     * Initialize controller
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        computedSamples = new ArrayList<>();
        // init main view
        initComputationDataPanel();
    }

    /**
     * Do computations for a certain sample.
     *
     * @param sample
     */
    public void computeSample(Sample sample) {
        // check if the sample has already gone through computations
        if (!computedSamples.contains(sample)) {
            // we need to do the computations
            // use here a Swing Worker
            SampleOperatorSW sampleOperatorSW = new SampleOperatorSW(sample);
            sampleOperatorSW.execute();
        }
    }

    /**
     * Show data for the selected sample.
     *
     * @param sample
     */
    public void showSampleData(Sample sample) {
        computationDataPanel.getSampleDataTable().setModel(new SampleDataTableModel(sample));
    }

    /**
     * Show data for selected track.
     *
     * @param track
     */
    public void showTrackData(Track track) {
        computationDataPanel.getTrackDataTable().setModel(new TrackDataTableModel(track));
    }

    /**
     * Plot data for a track.
     *
     * @param track
     */
    public void plotTrackData(Track track) {
        plotDeltaX(track);
        plotDeltaY(track);
        plotDisplacements(track);
        plotAngles(track);
    }

    /**
     *
     * @param track
     */
    private void plotDeltaX(Track track) {
        Double[] deltaXValues = ComputationUtils.transpose2DArray(track.getSteps())[0];
        double[] values = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(deltaXValues));
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", values, 2);
        String title = "delta_x_values for track: " + track.getTrackid();
        JFreeChart jFreeChart = ChartFactory.createHistogram(title, "delta_x", "count", histogramDataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        computationDataPanel.getDeltaxPlotPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getDeltaxPlotPanel().revalidate();
        computationDataPanel.getDeltaxPlotPanel().repaint();
    }

    /**
     *
     * @param track
     */
    private void plotDeltaY(Track track) {
        Double[] deltaYValues = ComputationUtils.transpose2DArray(track.getSteps())[1];
        double[] values = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(deltaYValues));
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", values, 2);
        String title = "delta_y_values for track: " + track.getTrackid();
        JFreeChart jFreeChart = ChartFactory.createHistogram(title, "delta_y", "count", histogramDataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        computationDataPanel.getDeltayPlotPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getDeltayPlotPanel().revalidate();
        computationDataPanel.getDeltayPlotPanel().repaint();
    }

    /**
     *
     * @param track
     */
    private void plotDisplacements(Track track) {
        double[] values = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(track.getStepDisplacements()));
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", values, 5);
        String title = "displacements for track: " + track.getTrackid();
        JFreeChart jFreeChart = ChartFactory.createHistogram(title, "displ", "count", histogramDataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        computationDataPanel.getDisplPlotPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getDisplPlotPanel().revalidate();
        computationDataPanel.getDisplPlotPanel().repaint();
    }

    /**
     *
     * @param track
     */
    private void plotAngles(Track track) {
        double[] values = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(track.getAngles()));
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", values, 5);
        String title = "angles for track: " + track.getTrackid();
        JFreeChart jFreeChart = ChartFactory.createHistogram(title, "angle", "count", histogramDataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        computationDataPanel.getAnglePlotPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getAnglePlotPanel().revalidate();
        computationDataPanel.getAnglePlotPanel().repaint();
    }

    /**
     * A class extending a SwingWorker to do computations on a sample.
     */
    private class SampleOperatorSW extends SwingWorker<Void, Void> {

        private final Sample sample;

        /**
         * Constructor
         *
         * @param sample
         */
        public SampleOperatorSW(Sample sample) {
            this.sample = sample;
        }

        @Override
        protected Void doInBackground() throws Exception {
            // set a waiting cursor
            computationMainController.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // start with the computations
            sampleOperator.computeSteps(sample);
            sampleOperator.computeStepDisplacements(sample);
            sampleOperator.computeAngles(sample);
            return null;
        }

        @Override
        protected void done() {
            try {
                get();
                // add sample to the computed list
                computedSamples.add(sample);
                computationMainController.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (InterruptedException | ExecutionException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Initialize main view.
     */
    private void initComputationDataPanel() {
        // make a new object
        computationDataPanel = new ComputationDataPanel();

        // format the tables
        JTableHeader tracksHeader = computationDataPanel.getTrackDataTable().getTableHeader();
        tracksHeader.setBackground(GuiUtils.getHeaderColor());
        tracksHeader.setFont(GuiUtils.getHeaderFont());
        tracksHeader.setReorderingAllowed(false);

        JTableHeader samplesHeader = computationDataPanel.getSampleDataTable().getTableHeader();
        samplesHeader.setBackground(GuiUtils.getHeaderColor());
        samplesHeader.setFont(GuiUtils.getHeaderFont());
        samplesHeader.setReorderingAllowed(false);

        computationDataPanel.getTrackDataTable().setRowSelectionAllowed(true);
        computationDataPanel.getTrackDataTable().setColumnSelectionAllowed(false);
        computationDataPanel.getTrackDataTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        computationDataPanel.getTrackDataTable().setRowSelectionAllowed(true);
        computationDataPanel.getTrackDataTable().setColumnSelectionAllowed(false);
        computationDataPanel.getTrackDataTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add component to main container
        computationMainController.getMainFrame().getComputationDataParentPanel().add(computationDataPanel, gridBagConstraints);
    }
}
