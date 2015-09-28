/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord;

import com.compomics.cell_coord.gui.ApplicationValidator;
import com.compomics.cell_coord.gui.CellCoordFrame;
import ij.IJ;
import ij.plugin.PlugIn;

/**
 * Main class of cellcoord. Implements an ImageJ PlugIn interface.
 *
 * @author Paola
 */
public class Cell_Coord implements PlugIn {

    @Override
    public void run(String string) {
        new CellCoordFrame().setVisible(true);
//        IJ.showMessage("Welcome to Cell_Corr");
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(final String... args) {
        new ij.ImageJ();
        ApplicationValidator applicationValidator = new ApplicationValidator();
        applicationValidator.startApplicationContext();
        new Cell_Coord().run("");
    }
}
