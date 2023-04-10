package utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * class ConvertToBufferImage is designed to convert an given format of Image to a bufferImage
 * in order to facilitate the render in the {@link view.IView}
 */

public class ConvertToBufferImage {

  /**
   * Help transfer a int[][] image to a buffered image.
   *
   * @param image  int[][] image
   */
  public static BufferedImage intListToBufferImage(int[][] image){
    int width = image[0][0];
    int height = image[0][1];
    BufferedImage bufferedImage = new BufferedImage(width, height, 1);
    for (int i = 1; i < height + 1; i++) {
      for (int j = 0; j < width; j++) {
        int r = image[i][3 * j];
        int g = image[i][3 * j + 1];
        int b = image[i][3 * j + 2];
        if (r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
          throw new IllegalArgumentException("Invalid RGB");
        }
        Color color = new Color(r, g, b);
        int pixel = color.getRGB();
        bufferedImage.setRGB(j, i - 1, pixel);
      }
    }
    return bufferedImage;
  }
}
