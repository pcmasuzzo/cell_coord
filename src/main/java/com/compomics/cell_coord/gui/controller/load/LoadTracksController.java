/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.LoadDirectoryException;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.info.LoadDirectoryInfoDialog;
import com.compomics.cell_coord.gui.info.TrackMateFilesInfoDialog;
import com.compomics.cell_coord.gui.load.LoadTracksPanel;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
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

    private static final Logger LOG = Logger.getLogger(LoadTracksController.class);
    // model
    private BindingGroup bindingGroup;
    private ObservableList<TrackSpot> trackSpotsBindingList;
    private File directory;
    // view
    private LoadTracksPanel loadTracksPanel;
    private LoadDirectoryInfoDialog sparseFilesInfoDialog;
    private TrackMateFilesInfoDialog trackMateFilesInfoDialog;
    // child controllers
    @Autowired
    private LoadDirectoryController loadDirectoryController;
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

    public File getDirectory() {
        return directory;
    }

    public ObservableList<TrackSpot> getTrackSpotsBindingList() {
        return trackSpotsBindingList;
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
     * Initialize controller.
     */
    public void init() {
        bindingGroup = new BindingGroup();
        trackSpotsBindingList = ObservableCollections.observableList(new ArrayList<TrackSpot>());
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        sparseFilesInfoDialog = new LoadDirectoryInfoDialog(cellCoordController.getCellCoordFrame(), true);
        trackMateFilesInfoDialog = new TrackMateFilesInfoDialog(cellCoordController.getCellCoordFrame(), true);
        // init main view
        initLoadTracksPanel();
        // init child controllers
        loadDirectoryController.init();
        loadTrackMateFilesController.init();
    }

    /**
     * Given the right table binding, show imported tracks in a table.
     *
     * @param trackSpotsTableBinding
     */
    public void showTracksInTable(JTableBinding trackSpotsTableBinding) {
        //add column bindings
        JTableBinding.ColumnBinding columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${track.trackid}"));
        columnBinding.setColumnName("track_id");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(Long.class);

        columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${trackSpotid}"));
        columnBinding.setColumnName("spot_id");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(Long.class);

        columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${x}"));
        columnBinding.setColumnName("x");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(Double.class);

        columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${y}"));
        columnBinding.setColumnName("y");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(Double.class);

        columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${time}"));
        columnBinding.setColumnName("time");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(Double.class);

        bindingGroup.addBinding(trackSpotsTableBinding);
        bindingGroup.bind();
    }

    /**
     * Choose the directory and load its data into a given JTree.
     *
     * @param dataTree
     */
    public void chooseDirectoryAndLoadData(JTree dataTree) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a root folder");
        // allow for directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // removing "All Files" option from FileType
        fileChooser.setAcceptAllFileFilterUsed(false);
        int returnVal = fileChooser.showOpenDialog(getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // the directory for the data
            directory = fileChooser.getSelectedFile();
            try {
                loadDataIntoTree(dataTree);
            } catch (LoadDirectoryException ex) {
                LOG.error(ex.getMessage());
                showMessage(ex.getMessage(), "wrong directory structure error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showMessage("Open command cancelled by user", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Load the data files into the tree.
     *
     * @throws LoadDirectoryException - when the directory is empty!
     */
    private void loadDataIntoTree(JTree dataTree) throws LoadDirectoryException {
        DefaultTreeModel model = (DefaultTreeModel) dataTree.getModel();
        DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
        // change name (user object) of root node
        rootNote.setUserObject(directory.getName());
        File[] listFiles = directory.listFiles();
        if (listFiles.length != 0) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName(), Boolean.FALSE);
                    rootNote.add(fileNode);
                } else {
                    // reset the directory to null
                    directory = null;
                    throw new LoadDirectoryException("This directory has a wrong structure!");
                }
            }
        } else {
            // reset the directory to null
            directory = null;
            throw new LoadDirectoryException("This directory seems to be empty!");
        }
        model.reload();
        showMessage("Directory successful loaded!\nYou can select the files to import!", "", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initialize main view
     */
    private void initLoadTracksPanel() {
        // new object
        loadTracksPanel = new LoadTracksPanel();
        // add radio buttons to a group: only one selection allowed!
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(loadTracksPanel.getLoadDirectoryRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadPlateRadioButton());
        buttonGroup.add(loadTracksPanel.getLoadTrackMateRadioButton());
        // select first option by default
        loadTracksPanel.getLoadDirectoryRadioButton().setSelected(true);

        // set icon for the question button
        Icon questionIcon = UIManager.getIcon("OptionPane.questionIcon");
        loadTracksPanel.getDirectoryInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));
        loadTracksPanel.getSingleFileInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));
        loadTracksPanel.getTrackMateInfoButton().setIcon(GuiUtils.getScaledIcon(questionIcon));

        /**
         * Show appropriate info dialogs.
         */
        loadTracksPanel.getDirectoryInfoButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // simply show the dialog
                sparseFilesInfoDialog.setVisible(true);
            }
        });

        loadTracksPanel.getSingleFileInfoButton().addActionListener(new ActionListener() {

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
                if (loadTracksPanel.getLoadDirectoryRadioButton().isSelected()) {
                    // call the sparse files controller
                    loadDirectoryController.onLoadingSparseFilesGui();
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
