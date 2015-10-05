/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.info.SparseFilesInfoDialog;
import com.compomics.cell_coord.gui.info.TrackMateFilesInfoDialog;
import com.compomics.cell_coord.gui.load.LoadTracksPanel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A controller to load single cell tracks: downstream of this loading, all
 * computations are performed and rendered to the user.
 *
 * @author Paola
 */
@Component("loadTracksController")
public class LoadTracksController {

    // model
    private List<Track> tracks; // might not be needed!
    // view
    private LoadTracksPanel loadTracksPanel;
    private SparseFilesInfoDialog sparseFilesInfoDialog;
    private TrackMateFilesInfoDialog trackMateFilesInfoDialog;
    // child controllers
    @Autowired
    private LoadSparseFilesController loadSparseFilesController;
    @Autowired
    private LoadTrackMateFilesController loadTrackMateFilesController;
    // parent controller
    @Autowired
    private CellCoordController cellCoordController;
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Getters
     *
     * @return
     */
    public LoadTracksPanel getLoadTracksPanel() {
        return loadTracksPanel;
    }

    /**
     * Get main frame through parent controller.
     *
     * @return
     */
    public CellCoordFrame getMainFrame() {
        return cellCoordController.getCellCoordFrame();
    }

    /**
     * Show a message to the user.
     *
     * @param message
     * @param title
     * @param messageType
     */
    public void showMessage(String message, String title, Integer messageType) {
        cellCoordController.showMessage(message, title, messageType);
    }

    /**
     * The layout of the panel: needed to switch cards.
     *
     * @return
     */
    public CardLayout getCardLayout() {
        return (CardLayout) loadTracksPanel.getTopPanel().getLayout();
    }

    /**
     * Initialize controller
     */
    public void init() {
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        sparseFilesInfoDialog = new SparseFilesInfoDialog(cellCoordController.getCellCoordFrame(), true);
        trackMateFilesInfoDialog = new TrackMateFilesInfoDialog(cellCoordController.getCellCoordFrame(), true);
        // init main view
        initLoadTracksPanel();
        // init child controllers
        loadSparseFilesController.init();
        loadTrackMateFilesController.init();
    }

    /**
     * Initialize main view
     */
    private void initLoadTracksPanel() {
        // new object
        loadTracksPanel = new LoadTracksPanel();
        // add radio buttons to a group: only one selection allowed!
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(loadTracksPanel.getLoadSparseFilesRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadPlateRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadTrackMateRadioButton());
        // select first option by default
        loadTracksPanel.getLoadSparseFilesRadioButton().setSelected(true);

        // set icon for the question button
        Icon questionIcon = UIManager.getIcon("OptionPane.questionIcon");
        loadTracksPanel.getSparseInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));
        loadTracksPanel.getPlateInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));
        loadTracksPanel.getTrackMateInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));

        /**
         * Show appropriate info dialogs.
         */
        loadTracksPanel.getSparseInfoButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // simply show the dialog
                sparseFilesInfoDialog.setVisible(true);
            }
        });

        loadTracksPanel.getPlateInfoButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        loadTracksPanel.getTrackMateInfoButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // simply show the dialog
                trackMateFilesInfoDialog.setVisible(true);
            }
        });

        /**
         * Next action: read the selection and call the correspondent child
         * controller.
         */
        loadTracksPanel.getNextButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadTracksPanel.getLoadSparseFilesRadioButton().isSelected()) {
                    // call the sparse files controller
                    loadSparseFilesController.onLoadingSparseFilesGui();
                } else if (loadTracksPanel.getLoadPlateRadioButton().isSelected()) {
                    // call the plate view controller
                } else {
                    // call the TrackMate controller
                    loadTrackMateFilesController.onLoadingTrackMateFilesGui();
                }
            }
        });

        // add view to parent component
        cellCoordController.getCellCoordFrame().getLoadTracksParentPanel().add(loadTracksPanel, gridBagConstraints);
    }
}
