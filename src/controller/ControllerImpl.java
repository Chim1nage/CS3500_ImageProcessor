package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import controller.commands.BlurAndSharpenCommand;
import controller.commands.BrightenCommand;
import controller.commands.ColorTransformationCommand;
import controller.commands.DownScaleCommand;
import controller.commands.FlipCommand;
import controller.commands.GreyscaleCommand;
import controller.commands.IPhotoCommand;
import model.IFinalProcessingModel;
import model.util.GeneralImage;
import model.util.Pixel;
import view.IView;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Represents the implementation of an image processor controller, which takes in data from the
 * user, delegates commands to the model, and transmits messages to the user via the view. Note:
 * minor changes from previous controller: added support for the new filtering and color
 * transformation commands to the command pattern, and added them to the image processor menu.
 *
 * <p>Invariant: model, view, input, and knownCommands are not null.
 */
public class ControllerImpl implements IController {
  private final IFinalProcessingModel model;
  private final IView view;
  private final Readable input;
  private final Map<String, Function<String[], IPhotoCommand>> knownCommands = new HashMap<>();
  private int width = 0;
  private int height = 0;
  private int red = 0;
  private int green = 0;
  private int blue = 0;
  private int maxValue = 255;

  /**
   * Initialize the controller with the model, view, and readable.
   *
   * @param model    the model with which to process and manipulate images
   * @param view     the view for transmitting messages to the user
   * @param readable the readable which reads in user input
   * @throws IllegalArgumentException if any parameter is null
   */
  public ControllerImpl(IFinalProcessingModel model, IView view, Readable readable)
          throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("Supplied model, view, or readable is null.");
    }
    this.model = model;
    this.view = view;
    this.input = readable;

    // initialize all the known commands
    this.knownCommands.put("brighten", s -> new BrightenCommand(s, this.view));
    this.knownCommands.put("vertical-flip", s -> new FlipCommand(s, this.view));
    this.knownCommands.put("horizontal-flip", s -> new FlipCommand(s, this.view));
    this.knownCommands.put("red-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("blue-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("green-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("value-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("luma-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("intensity-component", s -> new GreyscaleCommand(s, this.view));
    this.knownCommands.put("blur", s -> new BlurAndSharpenCommand(s, this.view));
    this.knownCommands.put("sharpen", s -> new BlurAndSharpenCommand(s, this.view));
    this.knownCommands.put("sepia", s -> new ColorTransformationCommand(s, this.view));
    this.knownCommands.put("greyscale", s -> new ColorTransformationCommand(s, this.view));
    this.knownCommands.put("downscale", s -> new DownScaleCommand(s, this.view));
  }

  /**
   * Runs the controller. Accepts input from the user, passes it to the model, informing
   * the view what to output to the supplied Appendable.
   *
   * @throws IllegalStateException when the program is not quit, and the Readable has run out
   *                               of inputs. Also, when the view is unable to append output
   *                               from the view to the supplied Appendable.
   */
  @Override
  public void startProcessor() throws IllegalStateException {
    Scanner scan = new Scanner(this.input);

    String message = "Welcome to Photo Editor:\n"
            + "Syntax for Commands:\n"
            + "    - Load a photo to be edited:         load imagePath imageName\n"
            + "    - Flip vertically:                   vertical-flip imageName destImageName\n"
            + "    - Flip horizontally:                 horizontal-flip imageName destImageName\n"
            + "    - Brighten:                          brighten #(increment scale number) "
            + "imageName destImageName\n"
            + "    - Red-Component Greyscale:           red-component imageName " +
            "destImageName\n"
            + "    - Green-Component Greyscale:         green-component imageName " +
            "destImageName\n"
            + "    - Blue-Component Greyscale:          blue-component imageName " +
            "destImageName\n"
            + "    - Value-Component Greyscale:         value-component imageName " +
            "destImageName\n"
            + "    - Intensity-Component Greyscale:     intensity-component imageName "
            + "destImageName\n"
            + "    - Luma-Component Greyscale:          luma-component imageName " +
            "destImageName\n"
            + "    - Sepia:                             sepia imageName destImageName\n"
            + "    - General Greyscale:                 greyscale imageName destImageName\n"
            + "    - Sharpen:                           sharpen imageName destImageName\n"
            + "    - blur:                              blur imageName destImageName\n"
            + "    - Save:                              save imagePath imageName\n"
            + "    - Downscale:                         downscale imageName destImageName width" +
            "ScaleFactor heightScaleFactor\n" +
            "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale after you" +
            " supply name of image, you can supply name of mask image (black and white version) of"
            + "that image where partial image manipulation will be performed on that mask image.)*"
            + "*To quit: Press 'q' or 'Q'. Any position in your command (of the singular letter) "
            + "will cause the Photo Editor to quit.*\nEnter input below:";

    // transmit welcome message
    tryTransmit(message);

    while (scan.hasNext()) {
      // get entire line from scanner
      String input = scan.nextLine();
      // split line into array of Strings
      String[] inputs = input.split(" ");
      // determine if 'q' or "q" appears anywhere in the inputs, if so quit
      if (checkQEntered(inputs)) {
        tryTransmit("Photo Editor Quit.");
        return;
      } else if (inputs.length == 3 && inputs[0].equals("save")) {
        tryTransmit("Image saving. Please wait...");
        try {
          this.save(inputs[1], inputs[2]);
          tryTransmit("Image saved. Continue:");
        } catch (IllegalArgumentException e) {
          tryTransmit(e.getMessage());
        }
      } else if (inputs.length == 3 && inputs[0].equals("load")) {
        tryTransmit("File is loading. Please wait...");
        try {
          this.load(inputs[1], inputs[2]);
          tryTransmit("File loaded. Continue:");
        } catch (IllegalArgumentException e) {
          tryTransmit(e.getMessage());
        }
      } else {
        // try to get command from Map of command
        Function<String[], IPhotoCommand> cmd = this.knownCommands.getOrDefault(inputs[0],
                null);
        if (cmd == null) {
          tryTransmit("Invalid Inputs. Try Again.");
        } else {
          try {
            cmd.apply(inputs).execute(this.model);
          } catch (IllegalArgumentException e) {
            tryTransmit(e.getMessage());
          }
        }
      }
    }

    // if the program is not quit, the program will throw an exception when the supplied
    // readable has run out of inputs.
    throw new IllegalStateException("Readable has run out of inputs.");
  }

  // Tries to transmit a message to the view
  private void tryTransmit(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Unable to transmit message.");
    }
  }

  // Determines if q or Q appears in the list at least once
  private boolean checkQEntered(String[] arr) {
    for (String s : arr) {
      if (s.compareToIgnoreCase("q") == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves an image file to be operated on.
   *
   * @param imagePath path to the image file
   * @param imageName name of the image file
   * @throws IllegalArgumentException if either parameter is null, the imagePath cannot be found,
   *                                  or the image type is unknown
   */
  @Override
  public void load(String imagePath, String imageName) throws IllegalArgumentException {
    checkNull(imagePath, imageName);
    int alpha = 255;

    // First, try to load PPM. If it does, return.
    if (imagePath.endsWith(".ppm")) {
      try {
        this.loadPPM(imagePath, imageName);
        return;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }

    BufferedImage loadImage = null;

    try {
      loadImage = ImageIO.read(new FileInputStream(imagePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Cant read file.");
    }

    try {
      this.width = loadImage.getWidth();
      this.height = loadImage.getHeight();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image to load is blank.");
    }

    Pixel[][] pixels = new Pixel[this.height][this.width];

    for (int col = 0; col < this.width; col++) {
      for (int row = 0; row < this.height; row++) {
        Color color = new Color(loadImage.getRGB(col, row));
        alpha = color.getAlpha();
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        int localMax = Math.max(this.green, Math.max(this.red, this.blue));
        if (localMax > this.maxValue) {
          this.maxValue = localMax;
        }
        pixels[row][col] = new Pixel(this.red, this.green, this.blue, alpha);
      }
    }

    this.model.addImage(imageName, new GeneralImage(pixels, this.width, this.height,
            this.maxValue));
  }

  // loads PPM file
  private void loadPPM(String imagePath, String imageName) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid supplied file.");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.equals("") && s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator()); //add all non-comment lines
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;

    if (sc.hasNext()) {
      token = sc.next();
    } else {
      throw new IllegalArgumentException("Image to load is blank.");
    }
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    if (sc.hasNextInt()) {
      this.width = sc.nextInt();
    }
    if (sc.hasNextInt()) {
      this.height = sc.nextInt();
    }
    if (sc.hasNextInt()) {
      this.maxValue = sc.nextInt();
    }

    Pixel[][] pixels = new Pixel[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        if (sc.hasNextInt()) {
          this.red = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          this.green = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          this.blue = sc.nextInt();
        }
        pixels[row][col] = new Pixel(this.red, this.green, this.blue);
      }
    }

    this.model.addImage(imageName, new GeneralImage(pixels, width, height, maxValue));
  }

  /**
   * Saves the current state of the image to the file directory.
   *
   * @param imagePath path to the image file
   * @param imageName name of the image file
   * @throws IllegalArgumentException if either parameter is null, the imageName cannot be found,
   *                                  or if the file save failed
   */
  @Override
  public void save(String imagePath, String imageName) throws IllegalArgumentException {
    List<String> acceptableTypes = new ArrayList();
    String fileType = imagePath.substring(imagePath.length() - 4);
    acceptableTypes.add(".png");
    acceptableTypes.add(".bmp");
    acceptableTypes.add(".jpg");
    acceptableTypes.add(".ppm");
    if (!acceptableTypes.contains(fileType)) {
      throw new IllegalArgumentException("Invalid supplied file type.");
    }
    checkNull(imageName, imageName);

    GeneralImage toSave = this.model.getImage(imageName);
    if (toSave == null) {
      throw new IllegalArgumentException("Image to save does not exist.");
    }

    File fileOutput = new File(imagePath);

    if (imagePath.endsWith(".ppm")) {
      this.savePPM(toSave, fileOutput);
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
      this.saveImagesHelper(fileType, src, fileOutput);
      return;
    } catch (Exception e) {
      // do nothing, continue to next throws statement below
    }

    throw new IllegalArgumentException("File destination to save to invalid.");
  }

  // assists in saving images of different file types (".jpg", ".png", and ".bmp")
  private void saveImagesHelper(String fileType, BufferedImage src, File fileOutput)
          throws IllegalArgumentException {
    try {
      ImageIO.write(src,
              fileType.substring(1), new FileOutputStream(fileOutput));
      return;
    } catch (IOException e) {
      throw new IllegalArgumentException("File to save to invalid.");
    }
  }

  // save PPM file
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

  /**
   * Checks if either of the two parameters are null.
   *
   * @param s1 the first String to check for null
   * @param s2 the second String to check for null
   * @throws IllegalArgumentException if either String is null
   */
  protected void checkNull(String s1, String s2) throws IllegalArgumentException {
    try {
      int i1 = s1.charAt(0);
      int i2 = s2.charAt(0);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Null values are illegal");
    }
  }

}

