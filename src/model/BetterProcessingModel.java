package model;

import model.util.GeneralImage;
import model.util.Pixel;

/**
 * Maintains the state of an improved image processing model. Loads and saves images, and allows
 * for various operations to be applied. New operation support includes sepia, greyscale, blurring,
 * and sharpening.
 */
public class BetterProcessingModel extends SimpleProcessingModel implements IBetterProcessingModel {

  /**
   * Initialize this better processing model. There are no fields to initialize.
   */
  public BetterProcessingModel() {
    super();
  }

  /**
   * Sharpens an image by applying a kernel.
   *
   * @param imageName     name of image to sharpen.
   * @param destImageName name of image after sharpened.
   * @throws IllegalArgumentException when imageName is invalid or if a parameter is null.
   */
  @Override
  public void sharpen(String imageName, String destImageName) throws IllegalArgumentException {
    super.checkNull(imageName, destImageName);
    super.checkHasImage(imageName, "sharpen");

    GeneralImage toSharpen = this.images.get(imageName);
    double[][] kernel = new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1.00, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};

    this.blurAndSharpen(kernel, toSharpen.getPixels(), destImageName, toSharpen.getWidth(),
        toSharpen.getHeight(), toSharpen.getMaxValue());
  }

  /**
   * Blurs an image by applying a kernel.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName is invalid or if a parameter is null.
   */
  @Override
  public void blur(String imageName, String destImageName) throws IllegalArgumentException {
    super.checkNull(imageName, destImageName);
    super.checkHasImage(imageName, "blur");

    GeneralImage toBlur = this.images.get(imageName);
    double[][] kernel = new double[][]{
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};

    this.blurAndSharpen(kernel, toBlur.getPixels(), destImageName, toBlur.getWidth(),
        toBlur.getHeight(), toBlur.getMaxValue());
  }

  // abstracts filtering into one method, based on a given kernel.
  private void blurAndSharpen(double[][] kernel, Pixel[][] oldPixels, String destImageName,
                              int width, int height, int maxValue) {
    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;
    Pixel pixel = null;
    Pixel targetPixel = null;
    Pixel[][] newPixels = new Pixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        targetPixel = oldPixels[row][col];

        for (int i = (-kernel.length / 2); i < (kernel.length / 2) + 1; i++) {
          for (int j = (-kernel.length / 2); j < (kernel.length / 2) + 1; j++) {

            try {
              pixel = oldPixels[row + i][col + j];
            } catch (Exception e) {
              continue;
            }

            redSum += (double) pixel.getRed() * kernel[i + (kernel.length / 2)]
                [j + (kernel.length / 2)];
            greenSum += (double) pixel.getGreen() * kernel[i + (kernel.length / 2)]
                [j + (kernel.length / 2)];
            blueSum += (double) pixel.getBlue() * kernel[i + (kernel.length / 2)]
                [j + (kernel.length / 2)];
          }
        }
        int[] toClomp = new int[]{(int) redSum, (int) greenSum, (int) blueSum, maxValue};
        int[] originalVals = new int[]{targetPixel.getRed(), targetPixel.getGreen(),
            targetPixel.getBlue(), maxValue};
        clampValues(toClomp);
        newPixels[row][col] = attemptManipulation(row, col, toClomp, originalVals);

        // reset local sums
        redSum = 0.0;
        greenSum = 0.0;
        blueSum = 0.0;
      }
    }
    this.images.put(destImageName, new GeneralImage(newPixels, width, height, maxValue));
  }

  /**
   * Applies a sepia color transformation to an image.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName is invalid.
   */
  @Override
  public void sepia(String imageName, String destImageName) throws IllegalArgumentException {
    super.checkNull(imageName, destImageName);
    super.checkHasImage(imageName, "sepia");

    GeneralImage toSepia = this.images.get(imageName);
    double[][] matrix = new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}};

    this.colorTransformation(matrix, toSepia.getPixels(), destImageName, toSepia.getWidth(),
        toSepia.getHeight(), toSepia.getMaxValue());
  }

  /**
   * Applies a greyscale color transformation to an image.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName to greyscale is invalid.
   */
  @Override
  public void greyscale(String imageName, String destImageName) throws IllegalArgumentException {
    super.checkNull(imageName, destImageName);
    super.checkHasImage(imageName, "greyscale");

    GeneralImage toGreyscale = this.images.get(imageName);
    double[][] matrix = new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};

    this.colorTransformation(matrix, toGreyscale.getPixels(), destImageName, toGreyscale.getWidth(),
        toGreyscale.getHeight(), toGreyscale.getMaxValue());
  }

  protected Pixel attemptManipulation(int row, int col, int[] clomped, int[] originalValues) {
    return new Pixel(clomped[0], clomped[1], clomped[2]);
  }

  // abstracts color transformations into one method, based on a given matrix.
  private void colorTransformation(double[][] matrix, Pixel[][] oldPixels, String destImageName,
                                   int width, int height, int maxValue)
      throws IllegalArgumentException {
    Pixel[][] newPixels = new Pixel[height][width];
    Pixel pixel;
    int[] result = new int[3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {

        pixel = oldPixels[row][col];
        for (int i = 0; i < matrix.length; i++) {
          double r = pixel.getRed() * matrix[i][0];
          double g = pixel.getGreen() * matrix[i][1];
          double b = pixel.getBlue() * matrix[i][2];
          result[i] = (int) r + (int) g + (int) b;
        }

        int[] toClomp = new int[]{result[0], result[1], result[2], maxValue};
        int[] originalVals = new int[]{pixel.getRed(), pixel.getGreen(), pixel.getBlue()};
        clampValues(toClomp);
        newPixels[row][col] = attemptManipulation(row,col,toClomp,originalVals);
      }
    }

    this.images.put(destImageName, new GeneralImage(newPixels, width, height, maxValue));
  }


  // helper to clamp values between 0 and the maxValue
  private void clampValues(int[] toClomp) {
    if (toClomp[0] > toClomp[3]) {
      toClomp[0] = toClomp[3];
    }
    if (toClomp[1] > toClomp[3]) {
      toClomp[1] = toClomp[3];
    }
    if (toClomp[2] > toClomp[3]) {
      toClomp[2] = toClomp[3];
    }
    if (toClomp[0] < 0) {
      toClomp[0] = 0;
    }
    if (toClomp[1] < 0) {
      toClomp[1] = 0;
    }
    if (toClomp[2] < 0) {
      toClomp[2] = 0;
    }
  }

}
