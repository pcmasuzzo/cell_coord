/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord;

import ij.IJ;
import ij.plugin.PlugIn;

/**
 *
 * @author Paola
 */
public class Cell_Coord implements PlugIn {

    @Override
    public void run(String string) {
        IJ.showMessage("Here should be!");
    }

    public static void main(final String... args) {
        new ij.ImageJ();
        new Cell_Coord().run("");
    }
}
