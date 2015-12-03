/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.computation;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Paola
 */
public class ComputationDataPanel extends javax.swing.JPanel {

    /**
     * Creates new form ComputationGraphPanel
     */
    public ComputationDataPanel() {
        initComponents();
    }

    public JPanel getConvexHullParentPanel() {
        return convexHullParentPanel;
    }

    public JTable getTrackDataTable() {
        return trackDataTable;
    }

    public JTable getStepDataTable() {
        return stepDataTable;
    }

    public JPanel getDisplParentPanel() {
        return displParentPanel;
    }

    public JPanel getxYParentPanel() {
        return xYParentPanel;
    }

    public JTable getSampleDataTable() {
        return sampleDataTable;
    }

    public JPanel getAnglePlotPanel() {
        return anglePlotPanel;
    }

    public JPanel getDeltaxPlotPanel() {
        return deltaxPlotPanel;
    }

    public JPanel getDeltayPlotPanel() {
        return deltayPlotPanel;
    }

    public JPanel getDisplPlotPanel() {
        return displPlotPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dataTabbedPane = new javax.swing.JTabbedPane();
        stepDataPanel = new javax.swing.JPanel();
        stepDataSplitPane = new javax.swing.JSplitPane();
        stepDataTablePanel = new javax.swing.JPanel();
        stepDataScrollPane = new javax.swing.JScrollPane();
        stepDataTable = new javax.swing.JTable();
        stepDataPlotPanel = new javax.swing.JPanel();
        deltaxPlotPanel = new javax.swing.JPanel();
        deltayPlotPanel = new javax.swing.JPanel();
        displPlotPanel = new javax.swing.JPanel();
        anglePlotPanel = new javax.swing.JPanel();
        trackDataPanel = new javax.swing.JPanel();
        trackDataSplitPane = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        xYParentPanel = new javax.swing.JPanel();
        displParentPanel = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        trackDataScrollPane = new javax.swing.JScrollPane();
        trackDataTable = new javax.swing.JTable();
        convexHullParentPanel = new javax.swing.JPanel();
        sampleDataPanel = new javax.swing.JPanel();
        sampleDataSplitPane = new javax.swing.JSplitPane();
        sampleDataTablePanel = new javax.swing.JPanel();
        sampleDataScrollPane = new javax.swing.JScrollPane();
        sampleDataTable = new javax.swing.JTable();
        sampleDataPlotPanel = new javax.swing.JPanel();

        stepDataSplitPane.setDividerLocation(200);
        stepDataSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        stepDataTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));

        stepDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        stepDataScrollPane.setViewportView(stepDataTable);

        javax.swing.GroupLayout stepDataTablePanelLayout = new javax.swing.GroupLayout(stepDataTablePanel);
        stepDataTablePanel.setLayout(stepDataTablePanelLayout);
        stepDataTablePanelLayout.setHorizontalGroup(
            stepDataTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stepDataTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stepDataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
        );
        stepDataTablePanelLayout.setVerticalGroup(
            stepDataTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stepDataTablePanelLayout.createSequentialGroup()
                .addComponent(stepDataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        stepDataSplitPane.setLeftComponent(stepDataTablePanel);

        stepDataPlotPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Plot"));
        stepDataPlotPanel.setLayout(new java.awt.GridBagLayout());

        deltaxPlotPanel.setPreferredSize(new java.awt.Dimension(20, 20));
        deltaxPlotPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        stepDataPlotPanel.add(deltaxPlotPanel, gridBagConstraints);

        deltayPlotPanel.setPreferredSize(new java.awt.Dimension(20, 20));
        deltayPlotPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        stepDataPlotPanel.add(deltayPlotPanel, gridBagConstraints);

        displPlotPanel.setPreferredSize(new java.awt.Dimension(20, 20));
        displPlotPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        stepDataPlotPanel.add(displPlotPanel, gridBagConstraints);

        anglePlotPanel.setPreferredSize(new java.awt.Dimension(20, 20));
        anglePlotPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        stepDataPlotPanel.add(anglePlotPanel, gridBagConstraints);

        stepDataSplitPane.setRightComponent(stepDataPlotPanel);

        javax.swing.GroupLayout stepDataPanelLayout = new javax.swing.GroupLayout(stepDataPanel);
        stepDataPanel.setLayout(stepDataPanelLayout);
        stepDataPanelLayout.setHorizontalGroup(
            stepDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stepDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stepDataSplitPane)
                .addContainerGap())
        );
        stepDataPanelLayout.setVerticalGroup(
            stepDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stepDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stepDataSplitPane)
                .addContainerGap())
        );

        dataTabbedPane.addTab("step data", stepDataPanel);

        trackDataSplitPane.setDividerLocation(300);

        xYParentPanel.setLayout(new java.awt.GridBagLayout());
        jTabbedPane1.addTab("(x, y)", xYParentPanel);

        displParentPanel.setLayout(new java.awt.GridBagLayout());
        jTabbedPane1.addTab("displ", displParentPanel);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        trackDataSplitPane.setLeftComponent(leftPanel);

        trackDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        trackDataScrollPane.setViewportView(trackDataTable);

        convexHullParentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Convex Hull"));
        convexHullParentPanel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(convexHullParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(trackDataScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trackDataScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(convexHullParentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        trackDataSplitPane.setRightComponent(rightPanel);

        javax.swing.GroupLayout trackDataPanelLayout = new javax.swing.GroupLayout(trackDataPanel);
        trackDataPanel.setLayout(trackDataPanelLayout);
        trackDataPanelLayout.setHorizontalGroup(
            trackDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 721, Short.MAX_VALUE)
            .addGroup(trackDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(trackDataPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(trackDataSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        trackDataPanelLayout.setVerticalGroup(
            trackDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
            .addGroup(trackDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(trackDataPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(trackDataSplitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 453, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        dataTabbedPane.addTab("track data", trackDataPanel);

        sampleDataSplitPane.setDividerLocation(200);
        sampleDataSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        sampleDataTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));

        sampleDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        sampleDataScrollPane.setViewportView(sampleDataTable);

        javax.swing.GroupLayout sampleDataTablePanelLayout = new javax.swing.GroupLayout(sampleDataTablePanel);
        sampleDataTablePanel.setLayout(sampleDataTablePanelLayout);
        sampleDataTablePanelLayout.setHorizontalGroup(
            sampleDataTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sampleDataTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sampleDataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
        );
        sampleDataTablePanelLayout.setVerticalGroup(
            sampleDataTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sampleDataTablePanelLayout.createSequentialGroup()
                .addComponent(sampleDataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        sampleDataSplitPane.setLeftComponent(sampleDataTablePanel);

        sampleDataPlotPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Plot"));

        javax.swing.GroupLayout sampleDataPlotPanelLayout = new javax.swing.GroupLayout(sampleDataPlotPanel);
        sampleDataPlotPanel.setLayout(sampleDataPlotPanelLayout);
        sampleDataPlotPanelLayout.setHorizontalGroup(
            sampleDataPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
        );
        sampleDataPlotPanelLayout.setVerticalGroup(
            sampleDataPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        sampleDataSplitPane.setRightComponent(sampleDataPlotPanel);

        javax.swing.GroupLayout sampleDataPanelLayout = new javax.swing.GroupLayout(sampleDataPanel);
        sampleDataPanel.setLayout(sampleDataPanelLayout);
        sampleDataPanelLayout.setHorizontalGroup(
            sampleDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sampleDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sampleDataSplitPane)
                .addContainerGap())
        );
        sampleDataPanelLayout.setVerticalGroup(
            sampleDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sampleDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sampleDataSplitPane))
        );

        dataTabbedPane.addTab("sample data", sampleDataPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(dataTabbedPane)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(dataTabbedPane)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel anglePlotPanel;
    private javax.swing.JPanel convexHullParentPanel;
    private javax.swing.JTabbedPane dataTabbedPane;
    private javax.swing.JPanel deltaxPlotPanel;
    private javax.swing.JPanel deltayPlotPanel;
    private javax.swing.JPanel displParentPanel;
    private javax.swing.JPanel displPlotPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel sampleDataPanel;
    private javax.swing.JPanel sampleDataPlotPanel;
    private javax.swing.JScrollPane sampleDataScrollPane;
    private javax.swing.JSplitPane sampleDataSplitPane;
    private javax.swing.JTable sampleDataTable;
    private javax.swing.JPanel sampleDataTablePanel;
    private javax.swing.JPanel stepDataPanel;
    private javax.swing.JPanel stepDataPlotPanel;
    private javax.swing.JScrollPane stepDataScrollPane;
    private javax.swing.JSplitPane stepDataSplitPane;
    private javax.swing.JTable stepDataTable;
    private javax.swing.JPanel stepDataTablePanel;
    private javax.swing.JPanel trackDataPanel;
    private javax.swing.JScrollPane trackDataScrollPane;
    private javax.swing.JSplitPane trackDataSplitPane;
    private javax.swing.JTable trackDataTable;
    private javax.swing.JPanel xYParentPanel;
    // End of variables declaration//GEN-END:variables
}
