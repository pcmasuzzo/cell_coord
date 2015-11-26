/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Sample;
import javax.swing.table.AbstractTableModel;

/**
 * A table model to show data computed for a sample.
 *
 * @author Paola
 */
public class SampleDataTableModel extends AbstractTableModel {

    private final Sample sample;
    private String columnNames[];
    private Object[][] modelData;

    public SampleDataTableModel(Sample sample) {
        this.sample = sample;
        initModel();
    }

    @Override
    public int getRowCount() {
        return 2;
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
        columnNames[0] = "sample_name";
        columnNames[1] = "nr_cell_tracks";
        modelData = new Object[2][columnNames.length];
//        for (int i = 0; i < samples.size(); i++) {
//            modelData[i][0] = samples.get(i).getName();
//            modelData[i][1] = samples.get(i).getTracks().size();
//        }
    }

}
