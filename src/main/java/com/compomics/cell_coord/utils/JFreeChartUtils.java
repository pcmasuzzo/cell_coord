/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.utils;

import org.apache.commons.lang.ArrayUtils;
import org.jfree.data.xy.XYSeries;

/**
 * An utilities class for JFreeChart.
 *
 * @author Paola
 */
public class JFreeChartUtils {

    /**
     * Generate a series for a (x, y) plot, given two arrays of double values.
     *
     * @param xValues
     * @param yValues
     * @return the series
     */
    public static XYSeries generateXYSeries(double[] xValues, double[] yValues) {
        // autosort False
        XYSeries series = new XYSeries("", false);
        for (int i = 0; i < yValues.length; i++) {
            double x = xValues[i];
            double y = yValues[i];
            series.add(x, y);
        }
        return series;
    }

    /**
     * Generate a series for a (x, y) plot, given a single x double value, and
     * an array of y double values.
     *
     * @param xValue
     * @param yValues
     * @return the series
     */
    public static XYSeries generateXYSeries(double xValue, double[] yValues) {
        // autosort False
        XYSeries series = new XYSeries("", false);
        for (double y : yValues) {
            series.add(xValue, y);
        }
        return series;
    }

    /**
     * Generate a series for a (x, y) plot, given single x and y double values.
     *
     * @param xValue
     * @param yValue
     * @return the series
     */
    public static XYSeries generateXYSeries(double xValue, double yValue) {
        // autosort False
        XYSeries series = new XYSeries("", false);
        series.add(xValue, yValue);
        return series;
    }

    /**
     * Generate a series for a (x, y) plot, a 2D array containing both x and y
     * double values.
     *
     * @param coordinatesToPlot
     * @return the series
     */
    public static XYSeries generateXYSeries(Double[][] coordinatesToPlot) {
        // transpose the matrix
        Double[][] transposed = ComputationUtils.transpose2DArray(coordinatesToPlot);
        // take first row: x coordinates
        double[] xCoordinates = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(transposed[0]));
        // take second row: y coordinates
        double[] yCoordinates = ArrayUtils.toPrimitive(ComputationUtils.excludeNullValues(transposed[1]));
        // generate xy series for the plot
        return JFreeChartUtils.generateXYSeries(xCoordinates, yCoordinates);
    }
}
