package model.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an image. This is used to define common attributes of images such as their
 * dimensions. The design of our image processor is such that all manipulations are done on PPM
 * images, however this class is loosely coupled with our model so that new methods do not need
 * to be added in two places. It is simply used as a data structure. Note: all image manipulation
 * commands that were previously in this class have been moved to the model.
 *
 * <p>Invariant: pixels is not null
 * Invariant: width, height, and maxValue are not negative
 * Invariant: there is no pixel in the image that has an RGB greater than the maxValue
 * Invariant: the width is equal to the number of columns in pixels, and height is equal to the
 * number of rows in pixels
 */
public final class GeneralImage {
  private final int width;
  private final int height;
  private final int maxValue;
  private final Pixel[][] pixels;

  /**
   * Initializes this image with all elements of a PPM.
   *
   * @param width    the width of the image, in pixels
   * @param height   the height of the image, in pixels
   * @param maxValue the maximum value of an RBG in any pixel for the desired bit representation
   * @param pixels   the pixels of the image represented as a two-dimensional array
   * @throws IllegalArgumentException if pixels is null or a parameter is negative
   */
  public GeneralImage(Pixel[][] pixels, int width, int height, int maxValue)
          throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }
    if (width < 0 || height < 0 || maxValue < 0) {
      throw new IllegalArgumentException("Image characteristics cannot be negative");
    }

    if (pixels[0].length != width || pixels.length != height) {
      throw new IllegalArgumentException("Invalid width and height for supplied pixels.");
    }

    for (Pixel[] arr : pixels) {
      for (Pixel p : arr) {
        if (p.overMaxValue(maxValue)) {
          throw new IllegalArgumentException("Pixels can't be over max value");
        }
      }
    }

    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  /**
   * Returns the string representation of this photo.
   *
   * @return the string representation of this photo
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("P3 \n# Created by Daniel Szyc + Evan Keister\n");
    sb.append(this.width + " ");
    sb.append(this.height + "\n");
    sb.append(this.maxValue + "\n\n");
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (i == this.height - 1 && j == this.width - 1) {
          sb.append(this.pixels[i][j].toString());
        } else {
          sb.append(this.pixels[i][j].toString()).append("\n");
        }
      }
    }

    return sb.toString();
  }

  /**
   * Gets pixel at specified location.
   *
   * @param row of pixel (y-value in 2d array)
   * @param col of pixel (x-value in 2d array)
   * @return Pixel at specified location.
   * @throws IllegalArgumentException when Pixel does not exist at coordinates.
   */
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0 || row > this.height || col > this.width) {
      throw new IllegalArgumentException("Pixel doesn't exist.");
    }
    return this.pixels[row][col];
  }

  /**
   * Gets width of this image.
   *
   * @return width of image.
   */
  public int getWidth() {
    int width = this.width;
    return width;
  }

  /**
   * Gets height of this image.
   *
   * @return height of image.
   */
  public int getHeight() {
    int height = this.height;
    return height;
  }

  /**
   * Gets pixels that make up this image. Deep copy that does not allow used to involuntarily edit
   * existing images.
   *
   * @return 2d array of pixels.
   */
  public Pixel[][] getPixels() {
    Pixel[][] deepCopy = new Pixel[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel temp = this.pixels[row][col];
        deepCopy[row][col] = new Pixel(temp.getRed(), temp.getGreen(), temp.getBlue(),
                temp.getAlpha());
      }
    }
    return this.pixels;
  }

  /**
   * Gets max value of this image.
   *
   * @return max channel value.
   */
  public int getMaxValue() {
    int maxValue = this.maxValue;
    return maxValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof GeneralImage)) {
      return false;
    }

    GeneralImage image = (GeneralImage) o;

    return Arrays.deepEquals(this.pixels, image.pixels)
            && this.width == image.width
            && this.height == image.height
            && this.maxValue == image.maxValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.maxValue, this.height, this.width);
  }

}


