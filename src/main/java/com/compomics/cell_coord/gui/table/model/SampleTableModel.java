/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Sample;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * A table model to show loaded samples.
 *
 * @author Paola
 */
public class SampleTableModel extends AbstractTableModel {

    private final List<Sample> samples;
    private String columnNames[];
    private Object[][] modelData;

    /**
     * Constructor.
     *
     * @param samples
     */
    public SampleTableModel(List<Sample> samples) {
        this.samples = samples;
        initModel();
    }

    @Override
    public int getRowCount() {
        return samples.size();
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
        columnNames[0] = "Sample";
        columnNames[1] = "Nr Cell Tracks";
        modelData = new Object[samples.size()][columnNames.length];
        for (int i = 0; i < samples.size(); i++) {
            modelData[i][0] = "Sample " + (i + 1);
            modelData[i][1] = samples.get(i).getTracks().size();
        }
    }
}
