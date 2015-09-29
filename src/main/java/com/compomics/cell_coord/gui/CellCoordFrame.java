/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The main frame of the CellCoord plugin.
 *
 * @author Paola
 */
public class CellCoordFrame extends javax.swing.JFrame {

    /**
     * Creates new form CellCoordFrame
     */
    public CellCoordFrame() {
        initComponents();
        // set background to white
        this.getContentPane().setBackground(new Color(255, 255, 255));
        UIManager.put("nimbusBase", Color.lightGray);      // Base color
        UIManager.put("nimbusBlueGrey", Color.lightGray);  // BlueGrey
        UIManager.put("control", Color.white);         // Control
        UIManager.put("OptionPane.background", Color.white); // Background for option pane
        UIManager.put("info", Color.white); // Background for tooltip texts (info class)
    }

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public JPanel getLoadTracksParentPanel() {
        return loadTracksParentPanel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        homeParentPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        infoScrollPane = new javax.swing.JScrollPane();
        infoEditorPane = new javax.swing.JEditorPane();
        startButton = new javax.swing.JButton();
        loadTracksParentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundPanel.setLayout(new java.awt.CardLayout());

        homeParentPanel.setBackground(new java.awt.Color(204, 204, 204));

        infoPanel.setOpaque(false);

        infoScrollPane.setBorder(null);

        infoEditorPane.setEditable(false);
        infoEditorPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        infoEditorPane.setContentType("text/html"); // NOI18N
        infoEditorPane.setText("<html>\n  <head>\n<TITLE></TITLE>\n  </head>\n  <body>\n<a name=\"#top\"/>\n        <H2>Welcome to Cell_Coord</H2>\n        <i>A Fiji/ImageJ plugin to study and visualize coordinated cell migration</i>\n  </body>\n</html>");
        infoScrollPane.setViewportView(infoEditorPane);

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        startButton.setText("Start");

        javax.swing.GroupLayout homeParentPanelLayout = new javax.swing.GroupLayout(homeParentPanel);
        homeParentPanel.setLayout(homeParentPanelLayout);
        homeParentPanelLayout.setHorizontalGroup(
            homeParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeParentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homeParentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton)
                .addGap(19, 19, 19))
        );
        homeParentPanelLayout.setVerticalGroup(
            homeParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeParentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addContainerGap())
        );

        backgroundPanel.add(homeParentPanel, "card2");

        loadTracksParentPanel.setMinimumSize(new java.awt.Dimension(20, 20));
        loadTracksParentPanel.setName("loadTracksParentPanel"); // NOI18N
        loadTracksParentPanel.setLayout(new java.awt.GridBagLayout());
        backgroundPanel.add(loadTracksParentPanel, "loadTracksParentPanel");
        loadTracksParentPanel.getAccessibleContext().setAccessibleName("loadTracksParentPanel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CellCoordFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CellCoordFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CellCoordFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CellCoordFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JPanel homeParentPanel;
    private javax.swing.JEditorPane infoEditorPane;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JScrollPane infoScrollPane;
    private javax.swing.JPanel loadTracksParentPanel;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
