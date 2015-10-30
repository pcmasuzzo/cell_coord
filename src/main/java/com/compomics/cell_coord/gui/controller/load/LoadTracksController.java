/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.exception.LoadDirectoryException;
import com.compomics.cell_coord.factory.TrackFileParserFactory;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.controller.summary.SummaryTracksController;
import com.compomics.cell_coord.gui.load.LoadTracksPanel;
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
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * A controller to load single cell tracks: downstream of this loading, all
 * computations are performed and rendered to the user.
 *
 * @author Paola
 */
@Controller("loadTracksController")
public class LoadTracksController {

    private static final Logger LOG = Logger.getLogger(LoadTracksController.class);
    // model
    private BindingGroup bindingGroup;
    private ObservableList<TrackSpot> trackSpotsBindingList;
    private JTableBinding trackSpotsTableBinding;
    private File directory;
    private List<Sample> samples;
    // view
    private LoadTracksPanel loadTracksPanel;
    // parent controller
    @Autowired
    private CellCoordController cellCoordController;
    // child controllers
    @Autowired
    private SummaryTracksController summaryTracksController;
    // services
    private GridBagConstraints gridBagConstraints;

    /**
     * Initialize controller.
     */
    public void init() {
        bindingGroup = new BindingGroup();
        gridBagConstraints = GuiUtils.getDefaultGridBagConstraints();
        trackSpotsBindingList = ObservableCollections.observableList(new ArrayList<TrackSpot>());
        samples = new ArrayList<>();
        initView();
        // init child controllers
        summaryTracksController.init();
    }

    /**
     * Getters
     *
     * @return
     */
    public List<Sample> getSamples() {
        return samples;
    }

    public CellCoordFrame getMainFrame() {
        return cellCoordController.getCellCoordFrame();
    }

    /**
     * Initialize the view components.
     */
    private void initView() {
        // create new view
        loadTracksPanel = new LoadTracksPanel();

        // populate the combobox with available file formats
        // note: these are annoatated as spring beans
        Set<String> parsers = TrackFileParserFactory.getInstance().getParserBeanNames();
        for (String parser : parsers) {
            loadTracksPanel.getFileFormatComboBox().addItem(parser);
        }

        // format the table
        JTableHeader tracksTableHeader = loadTracksPanel.getTracksTable().getTableHeader();
        tracksTableHeader.setBackground(GuiUtils.getHeaderColor());
        tracksTableHeader.setReorderingAllowed(false);

        /**
         * Action Listeners.
         */
        // load directory
        loadTracksPanel.getLoadDirectoryButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (directory == null) {
                    chooseDirectoryAndLoadData();
                    // if loading the directory was successful (i.e. the directory is still not null), set the ttext in the JComponent
                    if (directory != null) {
                        loadTracksPanel.getChosenDirectoryTextArea().setText(directory.getAbsolutePath());
                    }
                } else {
                    // otherwise we ask the user if they want to reload the directory
                    Object[] options = {"Load a different directory", "Cancel"};
                    int showOptionDialog = JOptionPane.showOptionDialog(null, "It seems a directory was already loaded.\nWhat do you want to do?", "", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    switch (showOptionDialog) {
                        case 0: // load a different directory:
                            // reset the model of the directory tree
                            DefaultTreeModel model = (DefaultTreeModel) loadTracksPanel.getDirectoryTree().getModel();
                            DefaultMutableTreeNode rootNote = (DefaultMutableTreeNode) model.getRoot();
                            rootNote.removeAllChildren();
                            model.reload();
                            chooseDirectoryAndLoadData();
                            loadTracksPanel.getChosenDirectoryTextArea().setText(directory.getAbsolutePath());
                            break;  // cancel: do nothing
                    }
                }
            }
        });

        // import the selected files in the tree
        loadTracksPanel.getImportFilesButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected file(s)
                TreePath[] selectionPaths = loadTracksPanel.getDirectoryTree().getSelectionPaths();
                if (selectionPaths != null && selectionPaths.length != 0) {
                    for (TreePath path : selectionPaths) {
                        String fileName = (String) path.getLastPathComponent().toString();
                        File trackFile = new File(directory.getAbsolutePath() + File.separator + fileName);
                        try {
                            // get the tracks and add all the spots to the binding list
                            List<Track> currentTracks = parseTrackFile(trackFile);
                            for (Track track : currentTracks) {
                                trackSpotsBindingList.addAll(track.getTrackSpots());
                            }
                            if (trackSpotsTableBinding == null) {
                                trackSpotsTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, trackSpotsBindingList, loadTracksPanel.getTracksTable());
                                showTracksInTable();
                            }
                            // for each file imported, create a new sample
                            Sample sample = new Sample(currentTracks);
                            samples.add(sample);
                        } catch (FileParserException ex) {
                            LOG.error("Could not parse the file: " + trackFile, ex);
                            cellCoordController.showMessage((String) loadTracksPanel.getFileFormatComboBox().getSelectedItem() + " expected!",
                                      "Error parsing file", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    cellCoordController.showMessage(selectionPaths.length + " file(s) successfully imported!", "success loading", JOptionPane.INFORMATION_MESSAGE);
                    // go to child controller and show samples in the table
                    summaryTracksController.showSamplesInTable();
                    // proceed with next step in the plugin
                    cellCoordController.getCellCoordFrame().getNextButton().setEnabled(true);
                } else {
                    // inform the user that no file was selected!
                    cellCoordController.showMessage("You have to select at least one file!", "no files selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // add view to parent component
        cellCoordController.getCellCoordFrame().getLoadTracksParentPanel().add(loadTracksPanel, gridBagConstraints);
    }

    /**
     * Parse a specific track file.
     *
     * @param trackFile
     * @return list of tracks
     */
    private List<Track> parseTrackFile(File trackFile) throws FileParserException {
        // get the selected file format -- call the correspondent file parser
        String parserName = (String) loadTracksPanel.getFileFormatComboBox().getSelectedItem();
        TrackFileParser parser = TrackFileParserFactory.getInstance().getParser(parserName);
        return parser.parseTrackFile(trackFile);
    }

    /**
     * Given the right table binding, show imported tracks in a table.
     *
     * @param trackSpotsTableBinding
     */
    private void showTracksInTable() {
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
    private void chooseDirectoryAndLoadData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a root folder");
        // allow for directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // removing "All Files" option from FileType
        fileChooser.setAcceptAllFileFilterUsed(false);
        int returnVal = fileChooser.showOpenDialog(cellCoordController.getCellCoordFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // the directory for the data
            directory = fileChooser.getSelectedFile();
            try {
                loadDataIntoTree();
            } catch (LoadDirectoryException ex) {
                LOG.error(ex.getMessage());
                cellCoordController.showMessage(ex.getMessage(), "wrong directory structure error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cellCoordController.showMessage("Open command cancelled by user", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Load the data files into the tree.
     *
     * @throws LoadDirectoryException - when the directory is empty!
     */
    private void loadDataIntoTree() throws LoadDirectoryException {
        DefaultTreeModel model = (DefaultTreeModel) loadTracksPanel.getDirectoryTree().getModel();
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
        cellCoordController.showMessage("Directory successful loaded!\nYou can select the files to import!", "", JOptionPane.INFORMATION_MESSAGE);
    }
}
