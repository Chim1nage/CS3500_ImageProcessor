package utils;

/**
 * Class OutputImageUtil represent an util set for image, which is modified based on
 * {@link utils.ImageUtil} for test only.
 * It contains utility methods to read a PPM image from file and simply return its contents.
 */
public class OutputImageUtil {

  /**
   * Static method of savePPM is designed to do basically same thing as
   * {@link utils.ImageUtil#savePPM(String, int[][])}. Except, it returns the contents as String.
   *
   * @param photo    is the photo represents as list of list of {@link Integer}.
   * @param width    is the width of photo in {@link Integer}.
   * @param height   is the height of photo in {@link Integer}.
   * @param maxValue is the maxValue of photo in {@link Integer}.
   * @return {@link String photo in PPM format}
   */
  public static String savePPM(int[][] photo, int width, int height, int maxValue) {

    StringBuilder builder = new StringBuilder();

    builder.append("P3\n");

    if (width <= 0) {
      throw new IllegalArgumentException("Width cannot be less than 0");
    } else {
      builder.append(width).append(" ");
    }

    if (height <= 0) {
      throw new IllegalArgumentException("Height cannot be less than 0");
    } else {
      builder.append(height).append("\n");
    }

    if (maxValue < 0 || maxValue > 255) {
      throw new IllegalArgumentException("MaxValue cannot be over 255 or under 0");
    } else {
      builder.append(maxValue).append("\n");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width * 3; j++) {
        builder.append(photo[i][j]).append("\n");
      }
    }

    return builder.toString();

  }
}