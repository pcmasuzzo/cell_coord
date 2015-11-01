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
        columnNames = new String[2];
        columnNames[0] = "Track";
        columnNames[1] = "Nr Track Spots";
        modelData = new Object[sample.getTracks().size()][columnNames.length];
        for (int i = 0; i < sample.getTracks().size(); i++) {
            modelData[i][0] = "Track " + (i + 1);
            modelData[i][1] = sample.getTracks().get(i).getTrackSpots().size();
        }
    }
}
