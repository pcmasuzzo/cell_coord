/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Track;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Paola
 */
public class TrackDataTableModel extends AbstractTableModel {

    private final Track track;
    private String columnNames[];
    private Object[][] modelData;

    /**
     * Constructor.
     *
     * @param track
     */
    public TrackDataTableModel(Track track) {
        this.track = track;
        initModel();
    }

    @Override
    public int getRowCount() {
        return 4;
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
        columnNames[0] = "property";
        columnNames[1] = "value";
        modelData = new Object[track.getSteps().length][columnNames.length];
        modelData[0][0] = "CD";
        modelData[1][0] = "ED";
        modelData[2][0] = "DR";
        modelData[3][0] = "CH";

        modelData[0][1] = track.getCumulativeDistance();
        modelData[1][1] = track.getEuclideanDistance();
        modelData[2][1] = track.getEndPointDirectionality();
        modelData[3][1] = track.getConvexHull().getHullSize();
    }
}
