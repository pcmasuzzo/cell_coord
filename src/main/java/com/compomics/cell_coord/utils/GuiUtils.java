/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.utils;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

/**
 * An utilities class for GUI elements.
 *
 * @author Paola
 */
public class GuiUtils {

    // Available colors (for set-up the experiment)
    private static final Color[] availableColors = {new Color(0, 0, 139), new Color(255, 0, 0), new Color(34, 139,
        34), new Color(148, 0, 211), new Color(255, 140, 0), new Color(30, 144, 255), new Color(255, 0, 255), new Color(0, 140, 140), new Color(128, 0, 0), new Color(128, 128, 0)};

    private static final Color headerColor = new Color(204, 255, 153);

    /**
     * Getters.
     *
     * @return
     */
    public static Color[] getAvailableColors() {
        return availableColors;
    }

    public static Color getHeaderColor() {
        return headerColor;
    }

    /**
     * Get Default Grid Bag Constraints used for Grid Bag Layout GUI Structures
     *
     * @return GridBagConstraints
     */
    public static GridBagConstraints getDefaultGridBagConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        return gridBagConstraints;
    }

    /**
     * Gets the name of the component currently visible in the card layout.
     *
     * @param parentContainer the parent container
     * @return the component name
     */
    public static String getCurrentCardName(Container parentContainer) {
        CardLayout cardLayout = (CardLayout) parentContainer.getLayout();
        if (cardLayout == null) {
            throw new IllegalArgumentException("The layout of the parent container is no card layout.");
        }
        JPanel card = null;
        for (Component component : parentContainer.getComponents()) {
            if (component.isVisible()) {
                card = (JPanel) component;
                break;
            }
        }
        return card.getName();
    }

}
