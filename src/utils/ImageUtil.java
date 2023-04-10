package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {
  /**
   * Reads in an image from the specific file path.
   *
   * @param filePath the file path of the image
   * @return the image in int[][]
   */
  public static int[][] readImage(String filePath) {
    File input = new File(filePath);
    BufferedImage image;
    try {
      image = ImageIO.read(input);
    } catch (IOException e) {
      image = null;
    }

    if (image == null) {
      return ImageUtil.readPPM(filePath);
    } else {
      return ImageUtil.readNormal(image);
    }
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filePath the path of the file.
   * @throws IllegalArgumentException if file path is not found
   * @throws IllegalArgumentException if file does not start with P3
   * @throws IllegalArgumentException if width, height, or maxValue < 0, or MaxValue > 255
   */
  private static int[][] readPPM(String filePath) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filePath + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    // avoid resource leak
    try {
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int inWidth = sc.nextInt();
    if (inWidth <= 0) {
      throw new IllegalArgumentException("Width cannot be smaller than 0");
    }

    int inHeight = sc.nextInt();
    if (inHeight <= 0) {
      throw new IllegalArgumentException("Height cannot be smaller 0 or larger than 255");
    }

    int inMaxValue = sc.nextInt();
    if (inMaxValue < 0 || inMaxValue > 255) {
      throw new IllegalArgumentException("maxValue cannot be smaller than 0 or larger than 255");
    }

    int[][] result = new int[inHeight + 1][3 * inWidth];
    result[0][0] = inWidth;
    result[0][1] = inHeight;
    result[0][2] = inMaxValue;
    for (int i = 1; i < inHeight + 1; i++) {
      for (int j = 0; j < inWidth; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        result[i][3 * j] = r;
        result[i][3 * j + 1] = g;
        result[i][3 * j + 2] = b;
      }
    }
    return result;
  }

  /**
   * Read files in formats that ImageIO recognizes like JPG, PNG, JPEG, etc. and will
   * convert them into int[][].
   *
   * @return the int[][] for the image
   */
  private static int[][] readNormal(BufferedImage image) {
    int height = image.getHeight();
    int width = image.getWidth();

    int[][] result = new int[height + 1][3 * width];
    result[0][0] = width;
    result[0][1] = height;

    for (int i = 1; i < height + 1; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = image.getRGB(j, i - 1);
        Color color = new Color(pixel);
        result[i][3 * j] = color.getRed();
        result[i][3 * j + 1] = color.getGreen();
        result[i][3 * j + 2] = color.getBlue();
      }
    }
    return result;
  }

  /**
   * Saves an image to the given file path.
   *
   * @param filePath file path of image
   * @param image    image to be saved
   */
  public static void saveImage(String filePath, int[][] image) {
    String formatName = filePath.substring(filePath.lastIndexOf(".") + 1);
    if (image[0][0] <= 0 || image[0][1] <= 0) {
      throw new IllegalArgumentException("width or height cannot be 0");
    }
    if (formatName.equals("ppm")) {
      savePPM(filePath, image);
    } else {
      saveNormal(filePath, image);
    }
  }

  /**
   * Saves the model to a ppm file given the file path.
   *
   * @param photo    the photo parameters
   * @param filePath the saving file path
   * @throws IllegalArgumentException if fails to create a file, or fails to write in a file
   * @throws IllegalArgumentException if width, height, or maxValue < 0, or maxValue > 255
   */
  private static void savePPM(String filePath, int[][] photo) {

    File file = new File(filePath);

    try {
      Files.deleteIfExists(file.toPath());
      file.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("something wrong happens during file creating!");
    }

    StringBuilder builder = new StringBuilder();
    helper(photo, builder);

    // file writing
    FileOutputStream fop;
    try {
      fop = new FileOutputStream(file);
      byte[] contentInBytes = builder.toString().getBytes();
      fop.write(contentInBytes);
    } catch (IOException e) {
      throw new IllegalArgumentException("something wrong happens during file writing!");
    }

    try {
      fop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves an int[][] to a image that has extensions that bufferedImage recognizes.
   *
   * @param filePath the file path to save the image
   * @param image    the image stored in the model
   */
  private static void saveNormal(String filePath, int[][] image) {
    int width = image[0][0];
    int height = image[0][1];

    File output = new File(filePath);
    BufferedImage result = ConvertToBufferImage.intListToBufferImage(image);
    String formatName = filePath.substring(filePath.lastIndexOf(".") + 1).toUpperCase();

    try {
      ImageIO.write(result, formatName, output);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file.");
    }
  }

  /**
   * Help to create the string for the final output.
   *
   * @param photo   the photo that will be output
   * @param builder the output String builder
   * @throws IllegalArgumentException if width, height, or maxValue < 0, or maxValue > 255
   */
  private static void helper(int[][] photo, StringBuilder builder) {
    builder.append("P3\n");
    builder.append(photo[0][0]).append(" ");
    builder.append(photo[0][1]).append("\n");

    for (int i = 1; i < photo[0][1] + 1; i++) {
      for (int j = 0; j < photo[0][0] * 3; j++) {
        photo[0][2] = Math.max(photo[0][2], photo[i][j]);
      }
    }

    if (photo[0][2] < 0 || photo[0][2] > 255) {
      throw new IllegalArgumentException("MaxValue cannot be over 255 or under 0");
    } else {
      builder.append(photo[0][2]).append("\n");
    }

    for (int i = 1; i < photo[0][1] + 1; i++) {
      for (int j = 0; j < photo[0][0] * 3; j++) {
        builder.append(photo[i][j]).append("\n");
      }
    }
  }

//  /**
//   * Help transfer a int[][] image to a buffered image.
//   *
//   * @param bImage buffered Image
//   * @param image  int[][] image
//   * @param width  width of image
//   * @param height height of image
//   */
//  public static void toBufferedImage(BufferedImage bImage, int[][] image, int width, int height) {
//    for (int i = 1; i < height + 1; i++) {
//      for (int j = 0; j < width; j++) {
//        int r = image[i][3 * j];
//        int g = image[i][3 * j + 1];
//        int b = image[i][3 * j + 2];
//        if (r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
//          throw new IllegalArgumentException("Invalid RGB");
//        }
//        Color color = new Color(r, g, b);
//        int pixel = color.getRGB();
//        bImage.setRGB(j, i - 1, pixel);
//      }
//    }
//  }
}

