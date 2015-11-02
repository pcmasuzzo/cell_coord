/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.utils;

/**
 * An utilities class for some computations.
 *
 * @author Paola
 */
public class ComputationUtils {

    /**
     * Transpose a 2D array of objects
     *
     * @param data
     * @return the same 2D array but transposed
     */
    public static Double[][] transpose2DArray(Double[][] data) {
        Double[][] transposed = new Double[data[0].length][data.length];
        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < data[0].length; columnIndex++) {
                transposed[columnIndex][rowIndex] = data[rowIndex][columnIndex];
            }
        }
        return transposed;
    }
}
