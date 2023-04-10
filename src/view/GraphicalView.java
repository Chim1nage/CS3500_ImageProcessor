package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JCheckBox;

/**
 * The GUI view class that uses a model and demonstrates it.
 */
public class GraphicalView extends JFrame implements IView {
  private final HashMap<String, JLabel> inputDisplayMap;
  private Histogram histogramPanelR;
  private Histogram histogramPanelG;
  private Histogram histogramPanelB;
  private Histogram histogramPanelI;
  private Image image;
  private Image preview;
  private JScrollPane previewScrollPane;
  private ActionListener actionListener;

  private ItemListener itemListener;
  private MouseListener mouseListener;

  private Map<String, Integer> functionalButtonMap;

  /**
   * Constructor of the GUI view class.
   */
  public GraphicalView() {
    this.inputDisplayMap = new HashMap<>();
  }

  /**
   * Initialize the view screen after configuration.
   */
  @Override
  public void initialize(){

    this.setTitle("Photo Processor");
    this.setMinimumSize(new Dimension(800, 800));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create Left area
    JPanel centerArea = new JPanel();
    centerArea.setLayout(new BoxLayout(centerArea, BoxLayout.PAGE_AXIS));

    this.image = new Image();
    this.image.addMouseListener(this.mouseListener);
    JScrollPane photoScrollPane = new JScrollPane(this.image,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    photoScrollPane.setBorder(BorderFactory.createTitledBorder("Photo"));
    photoScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.preview = new Image();
//    this.preview.setPreferredSize(new Dimension(200, 200));
//    this.preview.setMaximumSize(new Dimension(200, 200));
    this.previewScrollPane = new JScrollPane(this.preview,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.previewScrollPane.setBorder(BorderFactory.createTitledBorder("Preview"));
    this.previewScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.previewScrollPane.setPreferredSize(new Dimension(220, 220));
    this.previewScrollPane.setMaximumSize(new Dimension(220, 220));

    centerArea.add(photoScrollPane);
    centerArea.add(this.previewScrollPane);

    JPanel lineEndArea = new JPanel();
    lineEndArea.setLayout(new BoxLayout(lineEndArea, BoxLayout.PAGE_AXIS));
    lineEndArea.setPreferredSize(new Dimension(this.getWidth()/5*2, this.getHeight()));

    JPanel loadAndSaveMenu = new JPanel();
    loadAndSaveMenu.setLayout(new GridLayout(1, 2));
    loadAndSaveMenu.setBorder(BorderFactory.createTitledBorder("BASIS"));
    loadAndSaveMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadAndSaveMenu.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/12));

    JPanel editMenu = new JPanel();
    editMenu.setLayout(new BoxLayout(editMenu, BoxLayout.PAGE_AXIS));
    editMenu.setBorder(BorderFactory.createTitledBorder("EDIT TOOL"));
    editMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    editMenu.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/3*2));

    JCheckBox previewSelection = new JCheckBox("Preview");
    previewSelection.addItemListener(this.itemListener);
    editMenu.add(previewSelection);

    JPanel greyScaleTool = new JPanel();
    greyScaleTool.setLayout(new GridLayout(0, 1));
    greyScaleTool.setBorder(BorderFactory.createTitledBorder("grey scale"));
    greyScaleTool. setAlignmentX(Component.LEFT_ALIGNMENT);
    greyScaleTool.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5*3));
    editMenu.add(greyScaleTool);

    JPanel filterTool = new JPanel();
    filterTool.setLayout(new GridLayout(0, 1));
    filterTool.setBorder(BorderFactory.createTitledBorder("filter"));
    filterTool.setAlignmentX(Component.LEFT_ALIGNMENT);
    filterTool.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5));
    editMenu.add(filterTool);

    JPanel flipTool = new JPanel();
    flipTool.setLayout(new GridLayout(0, 1));
    flipTool.setBorder(BorderFactory.createTitledBorder("flip"));
    flipTool.setAlignmentX(Component.LEFT_ALIGNMENT);
    flipTool.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5));
    editMenu.add(flipTool);

    // Create Histogram
    JTabbedPane histogramPanel = new JTabbedPane();
    histogramPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/4));
    this.histogramPanelR = new Histogram();
    this.histogramPanelG = new Histogram();
    this.histogramPanelB = new Histogram();
    this.histogramPanelI = new Histogram();
    JScrollPane scrollR = new JScrollPane(this.histogramPanelR,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    histogramPanel.addTab("red", scrollR);
    JScrollPane scrollG = new JScrollPane(this.histogramPanelG,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    histogramPanel.addTab("green", scrollG);
    JScrollPane scrollB = new JScrollPane(this.histogramPanelB,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    histogramPanel.addTab("blue", scrollB);
    JScrollPane scrollI = new JScrollPane(this.histogramPanelI,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    histogramPanel.addTab("intensity", scrollI);

    lineEndArea.add(loadAndSaveMenu);
    lineEndArea.add(editMenu);
    lineEndArea.add(histogramPanel);

    //initialize button
    for (String buttonName : new ConcurrentSkipListSet<>(this.functionalButtonMap.keySet())) {
      JButton button = new JButton(buttonName);
      button.addActionListener(this.actionListener);

      if (buttonName.equals("save") || buttonName.equals("load")) {
        loadAndSaveMenu.add(button);
      } else if (buttonName.contains("greyscale")) {
        greyScaleTool.add(button);
      } else if (buttonName.contains("flip")) {
        flipTool.add(button);
      } else {
        filterTool.add(button);
      }

      if (this.functionalButtonMap.get(buttonName) > 0) {
        this.inputDisplayMap.put(buttonName, new JLabel("Type value for" + buttonName));
      }
    }

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
    mainPanel.add(centerArea, BorderLayout.CENTER);
    mainPanel.add(lineEndArea, BorderLayout.LINE_END);
    this.add(mainPanel);

    pack();
    this.setVisible(true);
  }

  @Override
  public void addListener(IOwnListener listener) {
    this.actionListener = Objects.requireNonNull(listener);
    this.itemListener = Objects.requireNonNull(listener);
    this.mouseListener = Objects.requireNonNull(listener);
  }

  @Override
  public void addButtons(Map<String, Integer> functionalButtonMap) {
    this.functionalButtonMap = Objects.requireNonNull(functionalButtonMap);
  }

  @Override
  public String setInputDisplayVisible(String buttonName) {
    Objects.requireNonNull(buttonName);
    JLabel inputDisplay = this.inputDisplayMap.getOrDefault(buttonName, null);
    String returnString = "";
    if (inputDisplay != null) {
      returnString = JOptionPane.showInputDialog("Please enter value for " + buttonName);
    }
    return returnString;
  }

  @Override
  public String setLoadDisplayVisible() {
    String filePath = "";
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG, JPEG, BMP, PPM Images",
            "jpg", "png", "jpeg", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = (f.getAbsolutePath());
    }
    return filePath;
  }

  @Override
  public String setSaveDisplayVisible() {
    String filePath = "";
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      filePath = (f.getAbsolutePath());
    }
    return filePath;
  }

  @Override
  public void refresh(BufferedImage image, Map<String, Map<Integer, Integer>> histogramMap) {
    Objects.requireNonNull(image);
    Objects.requireNonNull(histogramMap);

    // Refresh image
    this.image.reset(image);

    // Refresh Histogram
    Map<Integer, Integer> red = histogramMap.get("red");
    this.histogramPanelR.reset(red, "red");
    Map<Integer, Integer> green = histogramMap.get("green");
    this.histogramPanelG.reset(green, "green");
    Map<Integer, Integer> blue = histogramMap.get("blue");
    this.histogramPanelB.reset(blue, "blue");
    Map<Integer, Integer> intensity = histogramMap.get("intensity");
    this.histogramPanelI.reset(intensity, "intensity");

    this.revalidate();
    this.repaint();
  }

  @Override
  public void refreshPreview(BufferedImage image) {
    Objects.requireNonNull(image);

    // Refresh image
    this.preview.reset(image);

    this.revalidate();
    this.repaint();
  }

  @Override
  public void clearPreview() {

    this.preview.clear();

    this.revalidate();
    this.repaint();
  }

  @Override
  public void setPreviewLocation(int x, int y) {
    this.previewScrollPane.getVerticalScrollBar().setValue(y);
    this.previewScrollPane.getHorizontalScrollBar().setValue(x);

    this.revalidate();
    this.repaint();
  }

  @Override
  public void alert(String s) {
    Objects.requireNonNull(s);
    JOptionPane.showMessageDialog(this, s, "Warning",
            JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void close() {
    this.dispatchEvent(new WindowEvent(this,
            WindowEvent.WINDOW_CLOSING));
  }
}
