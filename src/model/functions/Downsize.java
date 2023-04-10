package model.functions;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;

/**
 * The downsize function.
 * It accepts {@link Integer width}, {@link Integer height},
 * {@link String old} and {@link String dest}
 * to execute a down in size on given image.
 */
public class Downsize implements ModelFunctions {
  private final String old;
  private final String dest;
  private final int width;
  private final int height;

  /**
   * Constructor for the Downsize function, which only accept four parameters.
   * if not four parameters, throw exception.
   *
   * @param width  represent the width of aimed size.
   * @param height represent the height of aimed size.
   * @param params represent the params.
   * @throws IllegalArgumentException when params length is not equal to 4
   */
  public Downsize(int width, int height, ArrayList<String> params) throws IllegalArgumentException {
    if (params.size() != 2) {
      throw new IllegalArgumentException("Arguments exceed demand!");
    }
    this.width = width;
    this.height = height;
    this.old = Objects.requireNonNull(params.get(0));
    this.dest = Objects.requireNonNull(params.get(1));
  }

  /**
   * apply method provide functionality to do downsize operation.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    int width = image[0][0];
    int height = image[0][1];
    if (this.width > width || this.height > height) {
      throw new IllegalArgumentException("Given scale is larger than original image");
    }
    int[][] result = new int[this.height + 1][this.width * 3];
    this.change(width, height, image, result);
    result[0][0] = this.width;
    result[0][1] = this.height;
    model.addToMap(dest, result);
  }

  /**
   * Change values for a pixel.
   *
   * @param width  width of original image
   * @param height height of original image
   * @param image  original image
   * @param result result image
   */
  private void change(int width, int height, int[][] image, int[][] result) {
    for (int i = 1; i < this.height + 1; i++) {
      for (int j = 0; j < this.width; j++) {
        double x = (double) (i - 1) / (double) this.height * height;
        double y = (double) j / (double) this.width * width;
        double xd = Math.max(1, Math.min(height, (int) x));
        double xu = Math.max(1, Math.min(height, xd + 1));
        double yd = Math.min(width, (int) y);
        double yu = Math.min(width, yd + 1);

        int red = 0;
        int green = 0;
        int blue = 0;

        for (int k = 0; k < 3; k++) {
          switch (k) {
            case 0:
              red = this.calculate(x, y, xd, xu, yd, yu, k, image);
              break;
            case 1:
              green = this.calculate(x, y, xd, xu, yd, yu, k, image);
              break;
            case 2:
              blue = this.calculate(x, y, xd, xu, yd, yu, k, image);
              break;
            default:
              continue;
          }
        }
        result[i][3 * j] = Math.max(0, Math.min(255, red));
        result[i][3 * j + 1] = Math.max(0, Math.min(255, green));
        result[i][3 * j + 2] = Math.max(0, Math.min(255, blue));
      }
    }
  }

  /**
   * Using the assignment formula to calculate the value for one channel.
   *
   * @param x     x of pixel
   * @param y     y of pixel
   * @param xd    floor of x
   * @param xu    roof of x
   * @param yd    floor of y
   * @param yu    roof of y
   * @param k     channel number
   * @param image image
   * @return the result value
   */
  private int calculate(double x, double y, double xd, double xu, double yd, double yu,
                        int k, int[][] image) {
    double a = image[(int) xd][(int) yd * 3 + k];
    double b = image[(int) xu][(int) yd * 3 + k];
    double c = image[(int) xd][(int) yu * 3 + k];
    double d = image[(int) xu][(int) yu * 3 + k];
    double m = b * (x - xd) + a * (xu - x);
    double n = d * (x - xd) + c * (xu - x);
    return (int) (n * (y - yd) + m * (yu - y));
  }
}
