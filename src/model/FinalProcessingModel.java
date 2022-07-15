package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import model.util.GeneralImage;
import model.util.Pixel;

/**
 * The complete Image Processing model, which stipulates the supported functionality of the final
 * version of our model. Maintains the state of an improved image processing model. New operation
 * support includes pixel value frequency generation as well as partial image manipulation (PIM)
 * for select operations and downscaling.
 */
public final class FinalProcessingModel extends BetterProcessingModel
        implements IFinalProcessingModel {
  private Pixel[][] maskPixels;
  private Map<String, File> files = new HashMap<>();

  /**
   * Initialize this final processing model. Calls the parent model to create the HashMap to store
   * images in.
   */
  public FinalProcessingModel() {
    super();
  }

  /**
   * Generates the frequency of every value 0 through 255 in a given image, divided into three
   * color channels and their average (intensity).
   *
   * @param imageName the image to generate the value frequency table of
   * @return the (value,intensity) table
   * @throws IllegalArgumentException if the provided image cannot be found in the model or is null
   */
  @Override
  public Map<Integer, int[]> generateFrequencies(String imageName) throws IllegalArgumentException {
    HashMap<Integer, int[]> frequencies = new HashMap<>();
    for (int k = 0; k < 256; k++) {
      frequencies.put(k, new int[]{0, 0, 0, 0});
    }
    checkNull(imageName, imageName);
    checkHasImage(imageName, "frequency");

    this.photo = this.images.get(imageName);
    Pixel[][] pixels = photo.getPixels();
    this.width = this.photo.getWidth();
    this.height = this.photo.getHeight();
    this.maxValue = this.photo.getMaxValue();
    int[] currValues;
    int r;
    int g;
    int b;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel c = pixels[i][j];
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        currValues = new int[]{r, g, b, (int) ((r + g + b) / 3.0)};
        for (int m = 0; m < 4; m++) {
          int[] currentFreq = frequencies.get(currValues[m]);
          currentFreq[m] += 1;
          frequencies.put(currValues[m], currentFreq);
        }
      }
    }

    return frequencies;
  }

  /**
   * Blurs an image, such that only pixels corresponding to a black pixel in the mask image are
   * blurred.
   *
   * @param imageName     the image to partially blur
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the partially blurred image
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void blur(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial blur");
    super.blur(imageName, destImageName);
    this.maskPixels = null;
  }

  /**
   * Sharpens an image, such that only pixels corresponding to a black pixel in the mask image are
   * sharpened.
   *
   * @param imageName     the image to partially sharpen
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the partially sharpened image
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void sharpen(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial sharpen");
    super.sharpen(imageName, destImageName);
    this.maskPixels = null;
  }

  /**
   * Applies a sepia filter to an image, such that only pixels corresponding to a black pixel in
   * the mask image receive the sepia effect.
   *
   * @param imageName     the image to partially apply a sepia filter to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial sepia filter
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void sepia(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial sepia filter");
    super.sepia(imageName, destImageName);
    this.maskPixels = null;
  }

  /**
   * Modifies the brightness of an image such that only pixels corresponding to a black pixel in
   * the mask image receive the change in brightness. Brightness can go up or down.
   *
   * @param increment     the amount to modify the brightness by
   * @param imageName     the image to change the brightness of
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image whose brightness has been partially modified
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void brighten(int increment, String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial brightness change");
    super.brighten(increment, imageName, destImageName);
    this.maskPixels = null;
  }

  /**
   * Applies a luma greyscale filter to an image, such that only pixels corresponding to a black
   * pixel in the mask image receive the luma greyscale filter.
   *
   * @param imageName     the image to partially apply a luma greyscale filter to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial luma greyscale filer
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void greyscale(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial luma greyscale");
    super.greyscale(imageName, destImageName);
    this.maskPixels = null;
  }

  /**
   * Applies a greyscale visualization to an image, such that only pixels corresponding to a black
   * pixel in the mask image receive the greyscale visualization.
   *
   * @param type          the type of greyscale component to visualize
   * @param imageName     the image to partially apply a greyscale visualization to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial greyscale
   *                      visualization
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  @Override
  public void componentGreyScale(GreyscaleType type, String imageName, String maskImageName,
                                 String destImageName) throws IllegalArgumentException {
    initiatePartialManipulation(imageName, maskImageName, "partial component greyscale filter");
    super.componentGreyScale(type, imageName, destImageName);
    this.maskPixels = null;
  }

  // helper to centralize code related to PIMs
  private void initiatePartialManipulation(String imageName, String maskImageName,
                                           String errorMessage) throws IllegalArgumentException {
    super.checkHasImage(maskImageName, errorMessage);
    super.checkHasImage(imageName, errorMessage);
    this.maskPixels = images.get(maskImageName).getPixels();
  }

  /* helper to support PIM. Conditions to modify the pixel:
     - a PIM is not being attempted
     - a PIM is being attempted, and the corresponding pixel in the mask image is black
     if neither condition is met, the resulting pixel is the same as the original
   */
  @Override
  protected Pixel attemptManipulation(int row, int col, int[] clomped, int[] originalVals)
          throws IllegalArgumentException {
    if (maskPixels != null) { // only enters this statement if PIM is being attempted
      Pixel target;
      try {
        target = maskPixels[row][col]; // check the corresponding pixel in the mask image
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new IllegalArgumentException("The given mask image has invalid dimensions");
      }
      if (target.getRed() == 0 && target.getGreen() == 0 && target.getBlue() == 0) {
        // PIM is being attempted, and the corresponding pixel in the mask image is black:
        // manipulate the target Pixel accordingly
        return new Pixel(clomped[0], clomped[1], clomped[2]);
      } else {
        // PIM is being attempted, but the corresponding pixel in the mask image is NOT black:
        // do not modify the target Pixel
        return new Pixel(originalVals[0], originalVals[1], originalVals[2]);
      }
    }
    // PIM is not being attempted: manipulate the pixel accordingly
    return new Pixel(clomped[0], clomped[1], clomped[2]);
  }

  @Override
  public void downScale(String imageName, String destImageName, double widthScaleFactor,
                        double heightScaleFactor) {
    super.checkNull(imageName, destImageName);
    super.checkHasImage(imageName, "downscale");
    int newWidth;
    int newHeight;
    GeneralImage image = this.images.get(imageName);

    newWidth = (int) Math.ceil(image.getWidth() * widthScaleFactor);
    newHeight = (int) Math.ceil(image.getHeight() * heightScaleFactor);

    Pixel[][] newPixels = new Pixel[newHeight][newWidth];

    for (int row = 0; row < newHeight; row++) {
      for (int col = 0; col < newWidth; col++) {
        int[] rgbSums = new int[3];

        double getRow = row * (1.0 / heightScaleFactor);
        double getCol = col * (1.0 / widthScaleFactor);

        Pixel pixelA = image.getPixelAt((int) Math.floor(getRow), (int) Math.floor(getCol));

        Pixel pixelB;
        try {
          pixelB = image.getPixelAt((int) Math.floor(getRow) + 1, (int) Math.floor(getCol));
        } catch (ArrayIndexOutOfBoundsException e) {
          pixelB = image.getPixelAt((int) Math.ceil(getRow), (int) Math.floor(getCol));
        }

        Pixel pixelC;
        try {
          pixelC = image.getPixelAt((int) Math.floor(getRow), (int) Math.floor(getCol) + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
          pixelC = image.getPixelAt((int) Math.floor(getRow), (int) Math.ceil(getCol));
        }

        Pixel pixelD;
        try {
          pixelD = image.getPixelAt((int) Math.floor(getRow) + 1, (int) Math.floor(getCol) + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
          pixelD = image.getPixelAt((int) Math.ceil(getRow), (int) Math.ceil(getCol));
        }

        int[] pixelAValues = pixelA.getValues();
        int[] pixelBValues = pixelB.getValues();
        int[] pixelCValues = pixelC.getValues();
        int[] pixelDValues = pixelD.getValues();

        for (int i = 0; i <= 2; i++) {
          double m = (pixelBValues[i] * (getRow - Math.floor(getRow))) + (pixelAValues[i]
                  * ((Math.floor(getRow) + 1) - getRow));
          double n = (pixelDValues[i] * (getRow - Math.floor(getRow))) + (pixelCValues[i]
                  * ((Math.floor(getRow) + 1) - getRow));
          int value = (int) ((n * (getCol - Math.floor(getCol))) + (m
                  * ((Math.floor(getCol) + 1) - getCol)));

          rgbSums[i] = value;
        }

        for (int i = 0; i < 3; i++) {
          if (rgbSums[i] > image.getMaxValue()) {
            rgbSums[i] = image.getMaxValue();
          }
        }

        newPixels[row][col] = new Pixel(rgbSums[0], rgbSums[1], rgbSums[2]);
      }
    }

    this.images.put(destImageName, new GeneralImage(newPixels, newWidth, newHeight,
            image.getMaxValue()));
  }

}
