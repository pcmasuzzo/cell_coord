/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Track;
import javax.swing.table.AbstractTableModel;

/**
 * A table model to show data computed for a track.
 *
 * @author Paola
 */
public class StepDataTableModel extends AbstractTableModel {

    private final Track track;
    private String columnNames[];
    private Object[][] modelData;

    /**
     * Constructor.
     *
     * @param track
     */
    public StepDataTableModel(Track track) {
        this.track = track;
        initModel();
    }

    @Override
    public int getRowCount() {
        return track.getSteps().length;
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
     * Initialize the model
     */
    private void initModel() {
        columnNames = new String[4];
        columnNames[0] = "delta_x";
        columnNames[1] = "delta_y";
        columnNames[2] = "displacement";
        columnNames[3] = "delta_teta";

        modelData = new Object[track.getSteps().length][columnNames.length];
        for (int i = 0; i < track.getSteps().length; i++) {
            modelData[i][0] = track.getSteps()[i][0];
            modelData[i][1] = track.getSteps()[i][1];
            modelData[i][2] = track.getStepDisplacements()[i];
            modelData[i][3] = track.getAngles()[i];
        }
    }
}
