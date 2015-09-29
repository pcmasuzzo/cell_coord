/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.load;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;

/**
 *
 * @author Paola
 */
public class LoadSparseFilesPanel extends javax.swing.JPanel {

    /**
     * Creates new form LoadSparseFilesPanel
     */
    public LoadSparseFilesPanel() {
        initComponents();
    }

    public JTree getDirectoryTree() {
        return directoryTree;
    }

    public JComboBox getFileFormatComboBox() {
        return fileFormatComboBox;
    }

    public JButton getImportFilesButton() {
        return importFilesButton;
    }

    public JButton getLoadDirectoryButton() {
        return loadDirectoryButton;
    }

    public JTable getTracksTable() {
        return tracksTable;
    }

    public JTextArea getDirectoryTextArea() {
        return directoryTextArea;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directoryScrollPane = new javax.swing.JScrollPane();
        directoryTree = new javax.swing.JTree();
        currentDirectoryScrollPane = new javax.swing.JScrollPane();
        directoryTextArea = new javax.swing.JTextArea();
        loadDirectoryButton = new javax.swing.JButton();
        importFilesButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tracksTable = new javax.swing.JTable();
        fileFormatComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        directoryScrollPane.setBorder(null);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Root (data)");
        directoryTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        directoryScrollPane.setViewportView(directoryTree);

        currentDirectoryScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Directory Loaded"));

        directoryTextArea.setEditable(false);
        directoryTextArea.setColumns(20);
        directoryTextArea.setLineWrap(true);
        directoryTextArea.setRows(5);
        directoryTextArea.setBorder(null);
        directoryTextArea.setCaretColor(new java.awt.Color(255, 255, 255));
        directoryTextArea.setEnabled(false);
        currentDirectoryScrollPane.setViewportView(directoryTextArea);

        loadDirectoryButton.setText("Choose & Load Directory");

        importFilesButton.setText("Import Selected Files");

        tracksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tracksTable);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Choose a file format");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(loadDirectoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                .addComponent(importFilesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fileFormatComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1)
                            .addComponent(currentDirectoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(directoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loadDirectoryButton)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fileFormatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(importFilesButton)
                        .addGap(18, 18, 18)
                        .addComponent(currentDirectoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(directoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane currentDirectoryScrollPane;
    private javax.swing.JScrollPane directoryScrollPane;
    private javax.swing.JTextArea directoryTextArea;
    private javax.swing.JTree directoryTree;
    private javax.swing.JComboBox fileFormatComboBox;
    private javax.swing.JButton importFilesButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadDirectoryButton;
    private javax.swing.JTable tracksTable;
    // End of variables declaration//GEN-END:variables
}
