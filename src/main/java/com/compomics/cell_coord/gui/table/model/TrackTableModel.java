/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Sample;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Paola
 */
public class TrackTableModel extends AbstractTableModel {

    private final Sample sample;
    private String columnNames[];
    private Object[][] modelData;

    /**
     * Constructor
     *
     * @param sample
     */
    public TrackTableModel(Sample sample) {
        this.sample = sample;
        initModel();
    }

    @Override
    public int getRowCount() {
        return sample.getTracks().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return modelData[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Initialize the model.
     */
    private void initModel() {
        columnNames = new String[8];
        columnNames[0] = "track";
        columnNames[1] = "nr_track_spots";
        columnNames[2] = "x_min";
        columnNames[3] = "x_max";
        columnNames[4] = "y_min";
        columnNames[5] = "y_max";
        columnNames[6] = "x_net";
        columnNames[7] = "y_net";
        modelData = new Object[sample.getTracks().size()][columnNames.length];
        for (int i = 0; i < sample.getTracks().size(); i++) {
            modelData[i][0] = sample.getTracks().get(i).getTrackid();
            modelData[i][1] = sample.getTracks().get(i).getTrackSpots().size();
            modelData[i][2] = sample.getTracks().get(i).getCoordinateRanges()[0][0];
            modelData[i][3] = sample.getTracks().get(i).getCoordinateRanges()[0][1];
            modelData[i][4] = sample.getTracks().get(i).getCoordinateRanges()[1][0];
            modelData[i][5] = sample.getTracks().get(i).getCoordinateRanges()[1][1];
            modelData[i][6] = sample.getTracks().get(i).getxNetDisplacement();
            modelData[i][7] = sample.getTracks().get(i).getyNetDisplacement();
        }
    }
}
