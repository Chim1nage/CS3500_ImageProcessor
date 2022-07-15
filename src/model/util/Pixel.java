package model.util;

import java.util.Objects;

import model.IProcessingModel;

/**
 * Represent a pixel from an image. Note: since hw4, we added new methods to retrieve the fields
 * of pixels.
 *
 * <p>Invariant: red, green, and blue are non-negative
 */
public final class Pixel {
  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;

  /**
   * Initialize this pixel with supplied the RGB values.
   *
   * @param red   Red RGB value
   * @param green Green RGB value
   * @param blue  Blue RGB value
   * @throws IllegalArgumentException if any parameter is negative
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("RGB values must be positive.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = 255; // default
  }

  /**
   * Initialize this pixel with supplied the RGB values.
   *
   * @param red   Red RGB value
   * @param green Green RGB value
   * @param blue  Blue RGB value
   * @throws IllegalArgumentException if any parameter is negative
   */
  public Pixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("RGB values must be positive.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  /**
   * Applies a greyscale visualization to this pixel.
   *
   * @param type the type of greyscale component to apply
   * @return the resulting pixel
   */
  public Pixel greyscale(IProcessingModel.GreyscaleType type) {
    if (type == null) {
      throw new IllegalArgumentException("Greyscale type cannot be null");
    }
    Pixel pixel;

    switch (type) {
      case Red:
        pixel = new Pixel(this.red, this.red, this.red);
        break;
      case Green:
        pixel = new Pixel(this.green, this.green, this.green);
        break;
      case Blue:
        pixel = new Pixel(this.blue, this.blue, this.blue);
        break;
      case Value:
        int value = Math.max(Math.max(this.red, this.green), this.blue);
        pixel = new Pixel(value, value, value);
        break;
      case Intensity:
        int numerator = this.red + this.green + this.blue;
        int intensity = numerator / 3;
        pixel = new Pixel(intensity, intensity, intensity);
        break;
      case Luma:
        int weightedSum = (int) (this.red * 0.2126) + (int) (this.green * 0.7152)
                + (int) (this.blue * 0.0722);
        pixel = new Pixel(weightedSum, weightedSum, weightedSum);
        break;
      default:
        pixel = new Pixel(this.red, this.green, this.blue);
        break;
    }
    return pixel;
  }

  /**
   * Changes the brightness of this pixel by the given increment.
   *
   * @param increment the amount to change each RGB value by
   * @param maxValue  the maximum value of each RBG value
   * @return the resulting pixel
   */
  public Pixel brighten(int increment, int maxValue) {
    int red;
    int green;
    int blue;

    if (increment > 0) {
      red = Math.min(maxValue, this.red + increment);
      green = Math.min(maxValue, this.green + increment);
      blue = Math.min(maxValue, this.blue + increment);
    } else if (increment < 0) {
      red = Math.max(0, this.red + increment);
      green = Math.max(0, this.green + increment);
      blue = Math.max(0, this.blue + increment);
    } else {
      red = this.red;
      green = this.green;
      blue = this.blue;
    }

    return new Pixel(red, green, blue);
  }

  /**
   * Determines if any of the Pixel's RGB values are over the supplied Max Value.
   * RGB value being less than 0 is already handled when initializing.
   *
   * @param maxValue of the image
   * @return whether of not a single RGB value is over the max value
   */
  public boolean overMaxValue(int maxValue) {
    return this.red > maxValue || this.green > maxValue || this.blue > maxValue;
  }

  /**
   * Convert pixel --> String. Used in saving images.
   *
   * @return RGB values as a string, all seperated by a new line.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append(this.red).append("\n");
    result.append(this.green).append("\n");
    result.append(this.blue).append("\n");
    return result.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Pixel)) {
      return false;
    }

    Pixel other = (Pixel) o;

    return this.red == other.red
            && this.green == other.green
            && this.blue == other.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  /**
   * Get red channel value of this pixel.
   *
   * @return red channel value.
   */
  public int getRed() {
    int red = this.red;
    return red;
  }

  /**
   * Get green channel of this pixel.
   *
   * @return green channel value.
   */
  public int getGreen() {
    int green = this.green;
    return green;
  }

  /**
   * Get blue channel of this pixel.
   *
   * @return blue channel value.
   */
  public int getBlue() {
    int blue = this.blue;
    return blue;
  }

  /**
   * Get alpha value of this pixel. Used to maintain image transparency.
   *
   * @return alpha value of pixel.
   */
  public int getAlpha() {
    int alpha = this.alpha;
    return alpha;
  }

  /**
   * Gets the values of this pixel as an array.
   *
   * @return the array representation of this pixel's RGB values
   */
  public int[] getValues() {
    int[] values = new int[3];
    values[0] = this.red;
    values[1] = this.green;
    values[2] = this.blue;

    return values;
  }

}




