/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.gui.controller.load;

import com.compomics.cell_coord.computation.SampleOperator;
import com.compomics.cell_coord.entity.Sample;
import com.compomics.cell_coord.entity.Track;
import com.compomics.cell_coord.entity.TrackSpot;
import com.compomics.cell_coord.exception.FileParserException;
import com.compomics.cell_coord.exception.LoadDirectoryException;
import com.compomics.cell_coord.factory.TrackFileParserFactory;
import com.compomics.cell_coord.gui.CellCoordFrame;
import com.compomics.cell_coord.gui.controller.CellCoordController;
import com.compomics.cell_coord.gui.controller.summary.SummaryDataController;
import com.compomics.cell_coord.gui.controller.summary.VisualizeTracksController;
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
    private boolean isImported;
    private List<Sample> samples;
    // view
    private LoadTracksPanel loadTracksPanel;
    // parent controller
    @Autowired
    private CellCoordController cellCoordController;
    // child controllers
    @Autowired
    private SummaryDataController summaryDataController;
    @Autowired
    private VisualizeTracksController visualizeTracksController;
    // services
    private GridBagConstraints gridBagConstraints;
    @Autowired
    private SampleOperator sampleOperator;

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
        summaryDataController.init();
        visualizeTracksController.init();
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
        // disable the IMPORT FILES button
        loadTracksPanel.getImportFilesButton().setEnabled(false);
        // initialize the flag to keep track of importing
        isImported = false;
        // populate the combobox with available file formats
        // note: these are annoatated as spring beans
        Set<String> parsers = TrackFileParserFactory.getInstance().getParserBeanNames();
        for (String parser : parsers) {
            loadTracksPanel.getFileFormatComboBox().addItem(parser);
        }

        // format the table
        JTableHeader tracksTableHeader = loadTracksPanel.getTracksTable().getTableHeader();
        tracksTableHeader.setBackground(GuiUtils.getHeaderColor());
        tracksTableHeader.setFont(GuiUtils.getHeaderFont());
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
                // check if an import already took place
                if (!isImported) {
                    // get the selected file(s) from the JTree
                    TreePath[] selectionPaths = loadTracksPanel.getDirectoryTree().getSelectionPaths();
                    if (selectionPaths != null && selectionPaths.length != 0) {
                        // at least a file was selected --  proceed with the import
                        importFiles();
                        isImported = true;
                        cellCoordController.showMessage(selectionPaths.length + " file(s) successfully imported!", "success loading", JOptionPane.INFORMATION_MESSAGE);
                        // do basic computations
                        preprocess();
                        // go to child controller and show samples in the table
                        summaryDataController.showSamplesInTable();
                        // proceed with next step in the plugin
                        cellCoordController.getCellCoordFrame().getNextButton().setEnabled(true);
                    } else {
                        // inform the user that no file was selected!
                        cellCoordController.showMessage("You have to select at least one file!", "no files selected", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // an import already took place: ask for user input
                    Object[] options = {"Load other file(s)", "Cancel"};
                    int showOptionDialog = JOptionPane.showOptionDialog(loadTracksPanel, "It seems some files were already loaded.\nWhat do you want to do?", "", JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    switch (showOptionDialog) {
                        case 0: // load other files
                            // clear the sample list
                            samples.clear();
                            // clear the track spot list
                            trackSpotsBindingList.clear();
                            // clear the selection in the JTree
                            loadTracksPanel.getDirectoryTree().clearSelection();
                            isImported = false;
                            // inform the user they need to select other files
                            JOptionPane.showMessageDialog(loadTracksPanel, "Please select other files", "", JOptionPane.INFORMATION_MESSAGE);
                            break;  // cancel: do nothing
                    }
                }

            }
        });

        // add view to parent component
        cellCoordController.getCellCoordFrame().getLoadTracksParentPanel().add(loadTracksPanel, gridBagConstraints);
    }

    /**
     * Preprocess the samples -- i.e. basic computations to render the tracks.
     */
    private void preprocess() {
        for (Sample sample : samples) {
            sampleOperator.prepareCoordinates(sample);
            sampleOperator.prepareShiftedCoordinates(sample);
            sampleOperator.computeCoordinatesRanges(sample);
            sampleOperator.computeShiftedCoordinatesRanges(sample);
        }
        visualizeTracksController.computeRanges();
    }

    /**
     * Parse a specific track file.
     *
     * @param trackFile
     * @return list of tracks
     */
    private Sample parseTrackFile(File trackFile) throws FileParserException {
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
        JTableBinding.ColumnBinding columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${track.sample.name}"));
        columnBinding.setColumnName("sample_name");
        columnBinding.setEditable(false);
        columnBinding.setColumnClass(String.class);

        columnBinding = trackSpotsTableBinding.addColumnBinding(ELProperty.create("${track.trackid}"));
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
        fileChooser.setDialogTitle("Please select a root folder containing your files");
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
     * Import the files selected in the Data Tree.
     */
    private void importFiles() {
        for (TreePath selectionPath : loadTracksPanel.getDirectoryTree().getSelectionPaths()) {
            String fileName = (String) selectionPath.getLastPathComponent().toString();
            File trackFile = new File(directory.getAbsolutePath() + File.separator + fileName);
            try {
                // get the sample
                Sample sample = parseTrackFile(trackFile);
                // get the tracks from the sample
                List<Track> currentTracks = sample.getTracks();
                // add all its spots to the binding list
                for (Track track : currentTracks) {
                    trackSpotsBindingList.addAll(track.getTrackSpots());
                }
                // add the sample to the list
                samples.add(sample);
                if (trackSpotsTableBinding == null) {
                    trackSpotsTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ, trackSpotsBindingList, loadTracksPanel.getTracksTable());
                    showTracksInTable();
                }
            } catch (FileParserException ex) {
                LOG.error("Could not parse the file: " + trackFile, ex);
                cellCoordController.showMessage((String) loadTracksPanel.getFileFormatComboBox().getSelectedItem() + " expected!",
                          "Error parsing file", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
        loadTracksPanel.getChosenDirectoryTextArea().setText(directory.getAbsolutePath());
        cellCoordController.showMessage("Directory successful loaded!\nYou can now select the files to import!", "", JOptionPane.INFORMATION_MESSAGE);
        loadTracksPanel.getImportFilesButton().setEnabled(true);
    }
}
