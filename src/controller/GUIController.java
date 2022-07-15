package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IFinalProcessingModel;
import model.IProcessingModel;
import model.util.GeneralImage;
import view.BlankView;
import view.IGUIView;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Represents the implementation of a GUI-based image processor controller, which takes in data
 * from the user, delegates commands to the model, and transmits data to the user via the GUI.
 */
public class GUIController implements IFeaturesController {
  private final IFinalProcessingModel model;
  private IGUIView view;
  private IController delegate;

  /**
   * Initialize this GUI controller by passing in model to delegate operations to.
   *
   * @param model used to perform operations, and transfer/store information that
   *              will be passed to the view.
   */
  public GUIController(IFinalProcessingModel model) {
    this.model = model;
    this.delegate = new ControllerImpl(this.model, new BlankView(), new StringReader(""));
  }

  @Override
  public void setView(IGUIView view) {
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void loadImage() {
    JFileChooser fchooser = new JFileChooser("res");
    fchooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "ppm", "bpm", "png"));

    if (fchooser.showOpenDialog((Component) this.view) == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String destName = JOptionPane.showInputDialog("Open as: ");
      if (destName.equals("")) {
        this.view.renderMessage("Need to name file to Open as.");
        return;
      }
      String path = f.getAbsolutePath();

      this.delegate.load(path, destName);
      GeneralImage toSave = this.model.getImage(destName);
      this.view.addImage(destName, this.model.generateFrequencies(destName), toSave);
    }
  }

  /**
   * Displays the image on when it is selected in the image list.
   *
   * @param imageName the image to display once selected
   */
  @Override
  public void selectAndDisplayImage(String imageName) {
    GeneralImage toSave = this.model.getImage(imageName);
    this.view.updateImage(imageName, this.model.generateFrequencies(imageName), toSave);
  }

  @Override
  public void filter(String commandName, String srcImageName) {
    if (commandName == null) {
      this.view.renderMessage("Filter command not selected.");
      return;
    }
    if (srcImageName == null) {
      this.view.renderMessage("Image to filter not selected.");
      return;
    }

    String destImageName = JOptionPane.showInputDialog("Save as: ");

    if (destImageName == null) {
      this.view.renderMessage("Image name not entered.");
      return;
    }

    int parsedIncrement = 0;
    if (commandName.equals("brighten")) {
      String increment = JOptionPane.showInputDialog("Increment By: ");
      try {
        parsedIncrement = Integer.valueOf(increment);
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid increment.");
        return;
      }
    }

    double parsedWidthFactor = 1.0;
    double parsedHeightFactor = 1.0;

    if (commandName.equals("downscale")) {
      String widthDownScaleFactor =
              JOptionPane.showInputDialog(
                      "Width Downscale Factor: ");
      if (widthDownScaleFactor == null) {
        this.view.renderMessage("Width downscale factor not entered.");
        return;
      }

      String heightDownScaleFactor =
              JOptionPane.showInputDialog(
                      "Height Downscale Factor: ");
      if (heightDownScaleFactor == null) {
        this.view.renderMessage("Height downscale factor not entered.");
        return;
      }

      try {
        parsedWidthFactor = Double.valueOf(widthDownScaleFactor);
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid factor");
        return;
      }

      if (parsedWidthFactor > 1.00) {
        this.view.renderMessage("Invalid factor.");
        return;
      }

      try {
        parsedHeightFactor = Double.valueOf(heightDownScaleFactor);
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid factor");
        return;
      }

      if (parsedHeightFactor > 1.00) {
        this.view.renderMessage("Invalid Height downscale factor.");
        return;
      }
    }

    switch (commandName) {
      case "vertical-flip":
        this.model.flip(false, srcImageName, destImageName);
        break;
      case "horizontal-flip":
        this.model.flip(true, srcImageName, destImageName);
        break;
      case "sepia":
        this.model.sepia(srcImageName, destImageName);
        break;
      case "greyscale":
        this.model.greyscale(srcImageName, destImageName);
        break;
      case "blur":
        this.model.blur(srcImageName, destImageName);
        break;
      case "sharpen":
        this.model.sharpen(srcImageName, destImageName);
        break;
      case "red-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Red, srcImageName,
                destImageName);
        break;
      case "green-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Green, srcImageName,
                destImageName);
        break;
      case "blue-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Blue, srcImageName,
                destImageName);
        break;
      case "value-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Value, srcImageName,
                destImageName);
        break;
      case "intensity-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Intensity, srcImageName,
                destImageName);
        break;
      case "luma-component":
        this.model.componentGreyScale(IProcessingModel.GreyscaleType.Luma, srcImageName,
                destImageName);
        break;
      case "brighten":
        this.model.brighten(parsedIncrement, srcImageName, destImageName);
        break;
      case "downscale":
        this.model.downScale(srcImageName, destImageName, parsedWidthFactor, parsedHeightFactor);
        break;
      default:
        return;
    }

    GeneralImage toSave = this.model.getImage(destImageName);
    this.view.addImage(destImageName, this.model.generateFrequencies(destImageName), toSave);
  }

  @Override
  public void saveImage(String selectedImageName) {
    DefaultListModel<String> values = this.view.getDataForImageList();
    if (values.getSize() == 0) {
      this.view.renderMessage("No images to save.");
      return;
    }

    if (selectedImageName == null) {
      this.view.renderMessage("No image selected to save.");
      return;
    }

    this.view.renderMessage("In the \"Save As\" box on the next screen, enter the name you would"
            + " like to save"
            + " the file as, followed by one of the following:\n"
            + ".ppm\n"
            + ".png\n"
            + ".bmp\n"
            + ".jpg");

    JFileChooser fileChooser = new JFileChooser("res");
    int response = fileChooser.showSaveDialog((Component) this.view);
    fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Images", "jpg", "ppm", "bmp", "png"));
    if (response == JFileChooser.CANCEL_OPTION) {
      return;
    }
    if (response == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      if (f == null) {
        this.view.renderMessage("Invalid supplied file type. Only .ppm," +
                " .png, .jpg, and .bmp are allowed.");
        return;
      }
      String destinationPath = f.getAbsolutePath();

      GeneralImage toSave = this.model.getImage(selectedImageName);

      if (toSave == null) {
        throw new IllegalArgumentException("Image to save does not exist.");
      }

      File fileOutput = new File(destinationPath);

      if (f.getAbsolutePath().endsWith(".ppm")) {
        this.view.renderMessage("PPM File is saving. This will take a few seconds." +
                " Please wait...");
        this.savePPM(toSave, fileOutput);
        this.view.renderMessage("PPM Image file saved.");
        return;
      }

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

      try {
        ImageIO.write(src,
                destinationPath.substring(destinationPath.length() - 3),
                new FileOutputStream(fileOutput));
      } catch (IOException e) {
        this.view.renderMessage("File to save to invalid.");
        return;
      }

    }

    this.view.renderMessage("File saved.");
  }

  // saves image as a PPM file
  private void savePPM(GeneralImage toSave, File fileOutput)
          throws IllegalArgumentException {
    DataOutputStream out;
    try {
      out = new DataOutputStream(new FileOutputStream(fileOutput));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File save failed");
    }
    try {
      out.writeBytes(toSave.toString());
      out.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("File save failed");
    }
  }

}








