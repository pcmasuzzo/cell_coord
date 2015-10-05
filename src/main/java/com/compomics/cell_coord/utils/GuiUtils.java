/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.utils;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * An utilities class for GUI elements.
 *
 * @author Paola
 */
public class GuiUtils {

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
     * Get a scaled icon starting from an icon. The rescaling is performed with
     * the specified integer scale. This is done through a buffered image.
     *
     * @param icon
     * @return
     */
    public static ImageIcon getScaledIcon(Icon icon) {
        // get icon  size
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();
        // get the scaled sizes
        int scale = 2;
        int scaledIconWidth = iconWidth / scale;
        int scaledIconHeight = iconHeight / scale;
        // create the buffered image - 8-bit RGBA color components packed into integer pixels
        BufferedImage bufferedImage = new BufferedImage(scaledIconWidth, scaledIconHeight, BufferedImage.TYPE_INT_ARGB);
        // create graphics from the image and scale it
        Graphics2D graphics2D = bufferedImage.createGraphics();
        setGraphics(graphics2D);
        graphics2D.scale(0.5, 0.5);
        // draw the icon
        icon.paintIcon(null, graphics2D, 0, 0);
        // dispose of the graphics
        graphics2D.dispose();
        // create the actual image icon
        return new ImageIcon(bufferedImage);
    }

    /**
     * set graphics: implementing rendering process for a Graphics2D object
     *
     * @param g2d
     */
    public static void setGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BasicStroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(stroke);
    }
}
