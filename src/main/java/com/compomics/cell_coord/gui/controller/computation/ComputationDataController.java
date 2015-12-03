/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.computation;

import com.compomics.cell_coord.computation.SampleOperator;
import com.compomics.cell_coord.entity.ConvexHull;
import com.compomics.cell_coord.entity.GeometricPoint;
import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.computation.ComputationDataPanel;
import com.compomics.cell_coord.gui.table.model.SampleDataTableModel;
import com.compomics.cell_coord.gui.table.model.StepDataTableModel;
import com.compomics.cell_coord.gui.table.model.TrackDataTableModel;
import com.compomics.cell_coord.utils.ComputationUtils;
import com.compomics.cell_coord.utils.GuiUtils;
import com.compomics.cell_coord.utils.JFreeChartUtils;
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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
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
     * Show step data for selected track.
     *
     * @param track
     */
    public void showStepData(Track track) {
        computationDataPanel.getStepDataTable().setModel(new StepDataTableModel(track));
    }

    /**
     * Show track data.
     *
     * @param track
     */
    public void showTrackData(Track track) {
        computationDataPanel.getTrackDataTable().setModel(new TrackDataTableModel(track));
    }

    /**
     * Plot step data for a track.
     *
     * @param track
     */
    public void plotStepData(Track track) {
        plotDeltaX(track);
        plotDeltaY(track);
        plotDisplacements(track);
        plotAngles(track);
    }

    /**
     * Plot track data.
     *
     * @param track
     */
    public void plotTrackData(Track track) {
        plotCoordinatesTime(track);
        plotDisplInTime(track);
        plotConvexHull(track);
    }

    /**
     *
     * @param track
     */
    private void plotCoordinatesTime(Track track) {
        Double[][] coordinates = track.getCoordinates();
        Double[][] transpose2DArray = ComputationUtils.transpose2DArray(coordinates);
        double[] timeIndexes = track.getTimeIndexes();
        double[] xCoordinates = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(transpose2DArray[0]));
        XYSeries xtSeries = JFreeChartUtils.generateXYSeries(timeIndexes, xCoordinates);
        XYSeriesCollection xtSeriesCollection = new XYSeriesCollection(xtSeries);
        XYItemRenderer renderer = new StandardXYItemRenderer();
        NumberAxis xAxis = new NumberAxis("x");
        XYPlot xTPlot = new XYPlot(xtSeriesCollection, null, xAxis, renderer);
        NumberAxis yAxis = new NumberAxis("y");
        double[] yCoordinates = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(transpose2DArray[1]));
        XYSeries ytSeries = JFreeChartUtils.generateXYSeries(timeIndexes, yCoordinates);
        XYSeriesCollection ytSeriesCollection = new XYSeriesCollection(ytSeries);
        XYPlot yTPlot = new XYPlot(ytSeriesCollection, null, yAxis, renderer);
        // domain axis
        NumberAxis domainAxis = new NumberAxis("time index");
        CombinedDomainXYPlot combinedDomainXYPlot = new CombinedDomainXYPlot(domainAxis);
        combinedDomainXYPlot.setRenderer(new XYLineAndShapeRenderer());
        combinedDomainXYPlot.add(xTPlot);
        combinedDomainXYPlot.add(yTPlot);
        combinedDomainXYPlot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart combinedChart = new JFreeChart("temp. evolution", combinedDomainXYPlot);
        ChartPanel chartPanel = new ChartPanel(combinedChart);
        computationDataPanel.getxYParentPanel().removeAll();
        computationDataPanel.getxYParentPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getxYParentPanel().revalidate();
        computationDataPanel.getxYParentPanel().repaint();
    }

    /**
     *
     * @param track
     */
    private void plotDisplInTime(Track track) {
        Double[] stepDisplacements = track.getStepDisplacements();
        double[] timeIndexes = track.getTimeIndexes();
        XYSeries xYSeries = JFreeChartUtils.generateXYSeries(timeIndexes, ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(stepDisplacements)));
        XYSeriesCollection ySeriesCollection = new XYSeriesCollection(xYSeries);
        JFreeChart displInTimeChart = ChartFactory.createXYLineChart("displacements in time", "time index", "displ.", ySeriesCollection, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel chartPanel = new ChartPanel(displInTimeChart);
        computationDataPanel.getDisplParentPanel().removeAll();
        computationDataPanel.getDisplParentPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getDisplParentPanel().revalidate();
        computationDataPanel.getDisplParentPanel().repaint();
    }

    /**
     *
     * @param track
     */
    private void plotConvexHull(Track track) {
        ConvexHull convexHull = track.getConvexHull();
        Iterable<GeometricPoint> cHull = convexHull.getHull();
        int M = 0;
        for (GeometricPoint point : cHull) {
            M++;
        }
        // the hull, in counterclockwise order
        GeometricPoint[] hull = new GeometricPoint[M];
        int m = 0;
        for (GeometricPoint point : cHull) {
            hull[m++] = point;
        }
        // generate xy coordinates for the points of the hull
        double[] x = new double[m + 1];
        double[] y = new double[m + 1];
        for (int i = 0; i < m; i++) {
            GeometricPoint point = hull[i];
            x[i] = point.getX();
            y[i] = point.getY();
        }
        // repeat fisrt coordinates at the end, to close the polygon
        x[m] = hull[0].getX();
        y[m] = hull[0].getY();
        // dataset for the convex hull
        XYSeries hullSeries = JFreeChartUtils.generateXYSeries(x, y);
        XYSeriesCollection hullDataset = new XYSeriesCollection(hullSeries);
        JFreeChart convexHullChart = ChartFactory.createXYLineChart("convex hull", "x", "y", hullDataset, PlotOrientation.VERTICAL, false, true, false);
        // dataset for the coordinates
        Double[][] coordinatesMatrix = track.getCoordinates();
        XYSeries coordinatesSeries = JFreeChartUtils.generateXYSeries(coordinatesMatrix);
        XYSeriesCollection coordinatesDataset = new XYSeriesCollection(coordinatesSeries);
        // use both datasets for the plot
        XYPlot xyPlot = convexHullChart.getXYPlot();
        xyPlot.setDataset(0, coordinatesDataset);
        xyPlot.setDataset(1, hullDataset);
        ChartPanel chartPanel = new ChartPanel(convexHullChart);
        computationDataPanel.getConvexHullParentPanel().removeAll();
        computationDataPanel.getConvexHullParentPanel().add(chartPanel, gridBagConstraints);
        computationDataPanel.getConvexHullParentPanel().revalidate();
        computationDataPanel.getConvexHullParentPanel().repaint();
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
        computationDataPanel.getDeltaxPlotPanel().removeAll();
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
        computationDataPanel.getDeltayPlotPanel().removeAll();
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
        computationDataPanel.getDisplPlotPanel().removeAll();
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
        computationDataPanel.getAnglePlotPanel().removeAll();
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
            sampleOperator.computeCumulativeDistances(sample);
            sampleOperator.computeEuclideanDistances(sample);
            sampleOperator.computeEndPointDirectionalities(sample);
            sampleOperator.computeConvexHulls(sample);
            sampleOperator.computeDisplacementRatios(sample);
            sampleOperator.computeOutreachRatios(sample);
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
        JTableHeader header = computationDataPanel.getStepDataTable().getTableHeader();
        header.setBackground(GuiUtils.getHeaderColor());
        header.setFont(GuiUtils.getHeaderFont());
        header.setReorderingAllowed(false);

        header = computationDataPanel.getSampleDataTable().getTableHeader();
        header.setBackground(GuiUtils.getHeaderColor());
        header.setFont(GuiUtils.getHeaderFont());
        header.setReorderingAllowed(false);

        header = computationDataPanel.getSampleDataTable().getTableHeader();
        header.setBackground(GuiUtils.getHeaderColor());
        header.setFont(GuiUtils.getHeaderFont());
        header.setReorderingAllowed(false);

        header = computationDataPanel.getTrackDataTable().getTableHeader();
        header.setBackground(GuiUtils.getHeaderColor());
        header.setFont(GuiUtils.getHeaderFont());
        header.setReorderingAllowed(false);

        computationDataPanel.getStepDataTable().setRowSelectionAllowed(true);
        computationDataPanel.getStepDataTable().setColumnSelectionAllowed(false);
        computationDataPanel.getStepDataTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        computationDataPanel.getStepDataTable().setRowSelectionAllowed(true);
        computationDataPanel.getStepDataTable().setColumnSelectionAllowed(false);
        computationDataPanel.getStepDataTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add component to main container
        computationMainController.getMainFrame().getComputationDataParentPanel().add(computationDataPanel, gridBagConstraints);
    }
}
