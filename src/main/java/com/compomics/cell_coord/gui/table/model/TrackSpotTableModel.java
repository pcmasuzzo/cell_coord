/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.table.model;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Paola
 */
public class TrackSpotTableModel extends AbstractTableModel {

    private final Track track;
    private String columnNames[];
    private Object[][] modelData;

    /**
     * Constructor.
     *
     * @param track
     */
    public TrackSpotTableModel(Track track) {
        this.track = track;
        initModel();
    }

    @Override
    public int getRowCount() {
        return track.getTrackSpots().size();
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
        columnNames = new String[4];
        columnNames[0] = "track_spot";
        columnNames[1] = "x";
        columnNames[2] = "y";
        columnNames[3] = "time";
        modelData = new Object[track.getTrackSpots().size()][columnNames.length];
        for (int i = 0; i < track.getTrackSpots().size(); i++) {
            TrackSpot trackSpot = track.getTrackSpots().get(i);
            modelData[i][0] = trackSpot.getTrackSpotid();
            modelData[i][1] = trackSpot.getX();
            modelData[i][2] = trackSpot.getY();
            modelData[i][3] = trackSpot.getTime();
        }
    }
}
