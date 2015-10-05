/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.exception.LoadDirectoryException;
import com.compomics.cell_coord.factory.TrackFileParserFactory;
import com.compomics.cell_coord.gui.load.LoadSparseFilesPanel;
import com.compomics.cell_coord.parser.TrackFileParser;
import com.compomics.cell_coord.utils.GuiUtils;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A controller component to load sparse files, and assign them to conditions.
 *
 * @author Paola
 */
@Component("loadSparseFilesController")
public class LoadSparseFilesController {

    private static final Logger LOG = Logger.getLogger(LoadSparseFilesController.class);
    // model
    private BindingGroup bindingGroup;
    private ObservableList<TrackSpot> trackSpotsBindingList;
    private JTableBinding trackSpotsTableBinding;
    private boolean directoryIsLoaded;
    private File directory;
    // view
    private LoadSparseFilesPanel loadSparseFilesPanel;
    // parent controller
    @Autowired
    private LoadTracksController loadTracksController;
    // child controllers
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        bindingGroup = new BindingGroup();
        trackSpotsBindingList = ObservableCollections.observableList(new ArrayList<TrackSpot>());
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        // init main view
        initLoadSparseFilesPanel();
    }

    /**
     * Show loaded tracks in the table.
     */
    public void showTracksInTable() {
        // table binding
        trackSpotsTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, trackSpotsBindingList, loadSparseFilesPanel.getTracksTable());
    }

    /**
     * On loading sparse files.
     */
    public void onLoadingSparseFilesGui() {
        loadTracksController.getCardLayout().show(loadTracksController.getLoadTracksPanel().getTopPanel(),
                  loadTracksController.getLoadTracksPanel().getSparseParentPanel().getName());
    }

    /**
     * Choose and return the directory to load into the JTree.
     *
     * @return
     */
    private void chooseDirectoryAndLoadData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a root folder");
        // allow for directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // removing "All Files" option from FileType
        fileChooser.setAcceptAllFileFilterUsed(false);
        int returnVal = fileChooser.showOpenDialog(loadTracksController.getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // the directory for the data
            directory = fileChooser.getSelectedFile();
            try {
                loadDataIntoTree();
            } catch (LoadDirectoryException ex) {
                LOG.error(ex.getMessage());
                loadTracksController.showMessage(ex.getMessage(), "wrong directory structure error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            loadTracksController.showMessage("Open command cancelled by user", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Load the data files into the tree.
     *
     * @throws LoadDirectoryException
     */
    private void loadDataIntoTree() throws LoadDirectoryException {
        DefaultTreeModel model = (DefaultTreeModel) loadSparseFilesPanel.getDirectoryTree().getModel();
        DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
        // change name (user object) of root node
        rootNote.setUserObject(directory.getName());
        File[] listFiles = directory.listFiles();
        if (listFiles.length != 0) {
            for (File file : listFiles) {
                DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName(), Boolean.FALSE);
                rootNote.add(fileNode);
            }
        } else {
            throw new LoadDirectoryException("This directory seems to be empty!");
        }
        model.reload();
        directoryIsLoaded = true;
        loadSparseFilesPanel.getDirectoryTextArea().setText(directory.getAbsolutePath());
        loadTracksController.showMessage("Directory successful loaded!\nYou can select the files to import!", "", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Parse a specific track file.
     *
     * @param trackFile
     * @return list of tracks
     */
    private List<Track> parseTrackFile(File trackFile) throws FileParserException {
        // get the selected file format -- call the correspondent file parser
        String parserName = (String) loadSparseFilesPanel.getFileFormatComboBox().getSelectedItem();
        TrackFileParser parser = TrackFileParserFactory.getInstance().getParser(parserName);
        return parser.parseTrackFile(trackFile);
    }

    /**
     * Initialize main view.
     */
    private void initLoadSparseFilesPanel() {
        // make new view
        loadSparseFilesPanel = new LoadSparseFilesPanel();

        // fill in combo box: get all parser methods from the factory
        Set<String> parsers = TrackFileParserFactory.getInstance().getParserBeanNames();
        for (String parser : parsers) {
            loadSparseFilesPanel.getFileFormatComboBox().addItem(parser);
        }

        /**
         * Add action listeners.
         */
        // load directory:
        loadSparseFilesPanel.getLoadDirectoryButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // we check if the directory was already loaded before
                if (!directoryIsLoaded) {
                    chooseDirectoryAndLoadData();
                } else {
                    // otherwise we ask the user if they want to reload the directory
                    Object[] options = {"Load a different directory", "Cancel"};
                    int showOptionDialog = JOptionPane.showOptionDialog(null, "It seems a directory was already loaded.\nWhat do you want to do?", "", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    switch (showOptionDialog) {
                        case 0: // load a different directory:
                            // reset the model of the directory tree
                            DefaultTreeModel model = (DefaultTreeModel) loadSparseFilesPanel.getDirectoryTree().getModel();
                            DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
                            rootNote.removeAllChildren();
                            model.reload();
                            chooseDirectoryAndLoadData();
                            break;  // cancel: do nothing
                    }
                }
            }
        });

        // import the selected files
        loadSparseFilesPanel.getImportFilesButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected file(s)
                TreePath[] selectionPaths = loadSparseFilesPanel.getDirectoryTree().getSelectionPaths();
                if (selectionPaths.length != 0) {
                    for (TreePath path : selectionPaths) {
                        String fileName = (String) path.getLastPathComponent().toString();
                        File trackFile = new File(directory.getAbsolutePath() + File.separator + fileName);
                        try {
                            List<Track> currentTracks = parseTrackFile(trackFile);
                            
                        } catch (FileParserException ex) {
                            LOG.error("Could not parse the file: " + trackFile);
                            loadTracksController.showMessage((String) loadSparseFilesPanel.getFileFormatComboBox().getSelectedItem() + " expected!",
                                      "Error parsing file", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // inform the user that no file was selected!
                    loadTracksController.showMessage("You have to select at least one file!", "no files selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // add panel to parent container
        loadTracksController.getLoadTracksPanel().getSparseParentPanel().add(loadSparseFilesPanel, gridBagConstraints);
    }
}
