package view;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.OverlayLayout;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.IFeaturesController;
import model.util.GeneralImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * GUI view implementation, which uses Java Swing to display the image processor graphically.
 */
public final class JFrameView extends JFrame implements IGUIView {

  private JPanel loadSavePanel;
  private JPanel selectionArea;
  private JPanel pageEnd;
  private JLabel imageLabel;
  private JScrollPane imagePane;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton enteredButton;
  private JPanel histogramPanel;
  private JPanel histogramGraph;
  private DefaultListModel<String> dataForImageList;
  private JList<String> listOfImage;
  private JList<String> listOfCommands;

  /**
   * Initialize this GUI View.
   */
  public JFrameView() {
    super("Image Processor");
    setPreferredSize(new Dimension(900, 800));
    setLayout(new BorderLayout());
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsToPane(getContentPane());
    pack();
    setVisible(true);
    this.histogramGraph = new JPanel();
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this, message,
            "Message", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Initializes action listener functionality of the GUI.
   *
   * @param features of the controller
   */
  @Override
  public void addFeatures(IFeaturesController features) {
    fileOpenButton.addActionListener(evt -> features.loadImage());
    listOfImage.addListSelectionListener(evt ->
            features.selectAndDisplayImage(listOfImage.getSelectedValue()));
    enteredButton.addActionListener(evt -> features.filter(listOfCommands.getSelectedValue(),
            listOfImage.getSelectedValue()));
    fileSaveButton.addActionListener(evt -> features.saveImage(listOfImage.getSelectedValue()));
  }

  /**
   * Adds an image to this view so that it can display it through the GUI.
   *
   * @param destName    the name of the image
   * @param frequencies the (value,frequency) table used to create a histrogram
   */
  @Override
  public void addImage(String destName, Map<Integer, int[]> frequencies, GeneralImage toSave) {
    this.updateImage(destName, frequencies, toSave);

    if (this.dataForImageList.contains(destName)) {
      for (int i = 0; i < this.dataForImageList.size(); i++) {
        if (destName.equals(this.dataForImageList.get(i))) {
          this.listOfImage.setSelectedIndex(i);
        }
      }
      return;
    }

    this.dataForImageList.add(0, destName);
    this.listOfImage.setSelectedIndex(0);
  }

  /**
   * Updates the view to display the current image.
   *
   * @param imageName   the image to display
   * @param frequencies the (value,frequency) table used to display the histogram
   */
  @Override
  public void updateImage(String imageName, Map<Integer, int[]> frequencies, GeneralImage toSave) {
    BufferedImage src = new BufferedImage(toSave.getWidth(), toSave.getHeight(), TYPE_INT_RGB);

    for (int col = 0; col < src.getHeight(); col++) {
      for (int row = 0; row < src.getWidth(); row++) {
        Color color = new Color(
                toSave.getPixelAt(col, row).getRed(),
                toSave.getPixelAt(col, row).getGreen(),
                toSave.getPixelAt(col, row).getBlue(),
                toSave.getPixelAt(col, row).getAlpha());
        src.setRGB(row, col, color.getRGB());
      }
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ImageIO.write(src, "jpg", baos);
    } catch (IOException e) {
      this.renderMessage("Unable to render image.");
      return;
    }

    byte[] bytes = baos.toByteArray();

    ImageIcon imageIcon = new ImageIcon(bytes);

    ImageIcon scaledIcon = imageIcon;

    if (imageIcon.getIconWidth() < 400 || imageIcon.getIconHeight() < 400) {
      double widthRatio = (double) imagePane.getWidth() / (double) imageIcon.getIconWidth();
      double heightRatio = (double) imagePane.getHeight() / (double) imageIcon.getIconHeight();
      double ratio = Math.min(widthRatio, heightRatio);

      Dimension dimension = new Dimension((int) (imageIcon.getIconWidth() * ratio),
              (int) (imageIcon.getIconHeight() * ratio));

      Image image = imageIcon.getImage();
      scaledIcon = new ImageIcon(image.getScaledInstance(dimension.width, dimension.height,
              Image.SCALE_SMOOTH));
    }

    this.imageLabel.setIcon(scaledIcon);
    this.updateHistogram(frequencies);
  }

  /**
   * Returns deep copy of list of images loaded in the program.
   */
  @Override
  public DefaultListModel<String> getDataForImageList() {
    DefaultListModel<String> copy = new DefaultListModel<>();
    for (int i = 0; i < this.dataForImageList.getSize(); i++) {
      copy.add(i, this.dataForImageList.get(i));
    }
    return copy;
  }


  // Adds all components to a master pane
  private void addComponentsToPane(Container pane) {
    JPanel mainPanel;
    JPanel imagePanel;
    JPanel bottomArea;
    JPanel userInputPanel;

    // MAIN PANEL
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // IMAGE PANEL
    imagePanel = new JPanel();
    imagePanel.setBackground(new Color(199, 190, 177));
    imagePanel.setOpaque(true);
    // ADDS: IMAGE VIEW
    this.createImageView();
    imagePanel.add(imagePane);
    // ADD IMAGE PANEL --> MAIN PANEL
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    // BOTTOM PANEL
    // (1) Open and Save Buttons
    // (2) List of Images and Commands
    // (3) Text Field && Enter Button
    // (4) Histogram
    bottomArea = new JPanel();
    bottomArea.setLayout(new BorderLayout());

    // SUB-BOTTOM PANEL
    userInputPanel = new JPanel();
    userInputPanel.setLayout(new BorderLayout());
    // ADDS - TOP: Load Button && Save Button
    this.createLoadSavePanel();
    userInputPanel.add(loadSavePanel, BorderLayout.PAGE_START);
    // ADDS - MIDDLE: List of Loaded Image Names && List of Commands
    this.createSelectionArea();
    userInputPanel.add(selectionArea, BorderLayout.CENTER);
    // ADDS - BOTTOM: Text Field && Enter Button
    this.createTextFieldAndEnterButton();
    userInputPanel.add(pageEnd, BorderLayout.PAGE_END);

    // ADD SUB-BOTTOM PANEL --> BOTTOM PANEL
    bottomArea.add(userInputPanel, BorderLayout.CENTER);
    // ADDS: Histogram
    this.createHistogram();
    bottomArea.add(histogramPanel, BorderLayout.EAST);

    // ADD BOTTOM PANEL --> MAIN PANEL
    mainPanel.add(bottomArea, BorderLayout.PAGE_END);

    // ADD MAIN PANEL --> JFrame
    pane.add(mainPanel, BorderLayout.CENTER);
  }

  // VISUALIZE IMAGES - create the scrollable panel to do so
  private void createImageView() {
    imageLabel = new JLabel();
    imagePane = new JScrollPane(imageLabel);
    imagePane.setPreferredSize(new Dimension(900, 470));
  }

  // LOAD AND SAVE BUTTONS PANEL
  private void createLoadSavePanel() {
    loadSavePanel = new JPanel();
    loadSavePanel.setLayout(new FlowLayout());
    loadSavePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // OPEN BUTTON:
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    loadSavePanel.add(fileOpenButton);

    // SAVE BUTTON:
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save a file");
    loadSavePanel.add(fileSaveButton);
  }

  // LIST OF IMAGES AND COMMANDS
  private void createSelectionArea() {
    JPanel selectionListImages;
    JPanel selectionListCommandPanel;

    selectionArea = new JPanel();
    selectionArea.setLayout(new FlowLayout());
    List<String> commands = new ArrayList<>();
    commands.add("sepia");
    commands.add("greyscale");
    commands.add("sharpen");
    commands.add("blur");
    commands.add("red-component");
    commands.add("green-component");
    commands.add("blue-component");
    commands.add("value-component");
    commands.add("intensity-component");
    commands.add("luma-component");
    commands.add("vertical-flip");
    commands.add("horizontal-flip");
    commands.add("brighten");
    commands.add("downscale");

    // ** List of Images that are Loaded
    selectionListImages = new JPanel();
    selectionListImages.setPreferredSize(new Dimension(200, 200));
    selectionListImages.setBorder(BorderFactory.createTitledBorder("Choose an image:"));
    selectionListImages.setLayout(new BoxLayout(selectionListImages, BoxLayout.X_AXIS));
    selectionArea.add(selectionListImages, BorderLayout.PAGE_START);

    dataForImageList = new DefaultListModel<>();
    listOfImage = new JList<>(dataForImageList);
    listOfImage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListImages.add(new JScrollPane(listOfImage));

    // ** List of Commands that are Loaded
    selectionListCommandPanel = new JPanel();
    selectionListCommandPanel.setPreferredSize(new Dimension(200, 200));
    selectionListCommandPanel.setBorder(BorderFactory.createTitledBorder("Choose a command:"));
    selectionListCommandPanel.setLayout(new BoxLayout(selectionListCommandPanel, BoxLayout.X_AXIS));
    selectionArea.add(selectionListCommandPanel, BorderLayout.PAGE_START);
    DefaultListModel<String> dataForCommandList = new DefaultListModel<>();
    for (String s : commands) {
      dataForCommandList.addElement(s);
    }
    listOfCommands = new JList<>(dataForCommandList);
    listOfCommands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    selectionListCommandPanel.add(new JScrollPane(listOfCommands));
  }

  // ENTER BUTTON
  private void createTextFieldAndEnterButton() {
    pageEnd = new JPanel();
    pageEnd.setLayout(new FlowLayout());

    // ENTER BUTTON:
    enteredButton = new JButton("Enter");
    enteredButton.setActionCommand("Enter Button");
    enteredButton.setPreferredSize(new Dimension(250, 40));
    pageEnd.add(enteredButton);
  }

  // Creates histogram visualization
  private void createHistogram() {
    JTextField histogramTitle;

    histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(400, 250));
    histogramPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    histogramPanel.setLayout(new BorderLayout());

    histogramTitle = new JTextField("                        Image Histogram Visualization");
    histogramTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    histogramPanel.add(histogramTitle, BorderLayout.PAGE_START);
  }

  // Updates histogram visualization with the desired image
  private void updateHistogram(Map<Integer, int[]> frequencies) {
    int[] mm = findLocalMaxMin(frequencies);
    Color[] c = new Color[4];
    c[0] = new Color(255, 0, 0, 40);
    c[1] = new Color(0, 255, 0, 40);
    c[2] = new Color(0, 0, 255, 40);
    c[3] = new Color(0, 0, 0, 40);

    histogramGraph.removeAll();

    histogramGraph.setLayout(new BoxLayout(histogramGraph, BoxLayout.LINE_AXIS));
    JPanel blank = new JPanel();
    blank.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    blank.setPreferredSize(new Dimension(80, 0));
    blank.setMaximumSize(new Dimension(80, 0));
    blank.setVisible(true);
    histogramGraph.add(blank);

    for (int i = 0; i < 256; i++) {
      JPanel line = new JPanel();
      line.setLayout(new OverlayLayout(line));
      JPanel newLine;

      for (int k = 0; k < 4; k++) {
        newLine = new JPanel();
        newLine.setBackground(c[k]);
        int original = frequencies.get(i)[k];
        int size = (int) (((original - mm[2 * k]) / (double) (mm[2 * k + 1] - mm[2 * k])) * 250);
        newLine.setPreferredSize(new Dimension(1, size));
        newLine.setMaximumSize(new Dimension(1, size));
        newLine.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        newLine.setVisible(true);
        line.add(newLine);
      }
      histogramGraph.add(line);
    }

    histogramGraph.revalidate();
    histogramGraph.repaint();
    histogramPanel.add(histogramGraph, BorderLayout.CENTER);
    JPanel endPage = new JPanel();
    endPage.setSize(new Dimension(400, 10));
    endPage.setVisible(true);
    histogramPanel.add(endPage, BorderLayout.PAGE_END);

    JTextField xAxis;
    xAxis = new JTextField("Value:         0                                                   " +
            "     255");
    histogramPanel.add(xAxis, BorderLayout.PAGE_END);

    histogramPanel.repaint();
  }

  // Finds the max/min of each channel in the (value,frequency) table, used for data normalization
  private int[] findLocalMaxMin(Map<Integer, int[]> frequencies) {
    int[] mm = new int[]{frequencies.get(1)[0], frequencies.get(1)[0],
            frequencies.get(1)[1], frequencies.get(1)[1],
            frequencies.get(1)[2], frequencies.get(1)[2],
            frequencies.get(1)[3], frequencies.get(1)[3]};

    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 8; j += 2) {
        mm[j] = Math.min(mm[j], frequencies.get(i)[j / 2]);
        mm[j] = Math.min(mm[j], frequencies.get(i)[j / 2]);
        mm[j + 1] = Math.max(mm[j + 1], frequencies.get(i)[j / 2]);
      }
    }
    return mm;
  }

}