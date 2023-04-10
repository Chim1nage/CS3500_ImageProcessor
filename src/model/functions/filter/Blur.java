package model.functions.filter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;
import utils.Util;

/**
 * Class Blur provide functionality of using Filter operation to blur the image.
 * to execute a blur operation on given image.
 */
public class Blur implements ModelFunctions {
  private final String old;

  private final String mask;
  private final String dest;
  private final double[][] kernel;

//  /**
//   * Constructor for Blur function, which accept two parameters
//   * {@link String old} and {@link String dest}.
//   *
//   * @param old  is an instance of {@link String} representing old image name
//   * @param dest is an instance of {@link String} representing new image name
//   */
//  public Blur(String old, String dest) {
//    this(old, "", dest);
//  }
//
//  /**
//   * Constructor for Blur function, which accept three parameters
//   * {@link String old}, {@link String mask} and {@link String dest}.
//   *
//   * @param old  is an instance of {@link String} representing old image name
//   * @param mask is an instance of {@link String} representing mask image name
//   * @param dest is an instance of {@link String} representing new image name
//   */
//  public Blur(String old, String mask, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//    kernel = new double[3][3];
//    kernel[0][0] = 0.0625;
//    kernel[0][1] = 0.125;
//    kernel[0][2] = 0.0625;
//    kernel[1][0] = 0.125;
//    kernel[1][1] = 0.25;
//    kernel[1][2] = 0.125;
//    kernel[2][0] = 0.0625;
//    kernel[2][1] = 0.125;
//    kernel[2][2] = 0.0625;
//  }

  /**
   * Constructor for the Blur function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public Blur(ArrayList<String> params) {
    this.old = Objects.requireNonNull(params.get(0));
    if (params.size() == 2){
      this.mask = "";
      this.dest = Objects.requireNonNull(params.get(1));
    } else {
      this.mask = Objects.requireNonNull(params.get(1));
      this.dest = Objects.requireNonNull(params.get(2));
    }
    kernel = new double[3][3];
    kernel[0][0] = 0.0625;
    kernel[0][1] = 0.125;
    kernel[0][2] = 0.0625;
    kernel[1][0] = 0.125;
    kernel[1][1] = 0.25;
    kernel[1][2] = 0.125;
    kernel[2][0] = 0.0625;
    kernel[2][1] = 0.125;
    kernel[2][2] = 0.0625;
  }

  /**
   * apply method provide functionality to do blur operation.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    int[][] origin = model.getFromMap(old);
    if (mask.equals("")) {
      int width = origin[0][0];
      int height = origin[0][1];

      for (int i = 1; i < height + 1; i++) {
        for (int j = 0; j < width; j++) {
          origin[i][3 * j] = new Filter(this.old, this.kernel, i, j, "red", origin).apply();
          origin[i][3 * j + 1] = new Filter(this.old, this.kernel, i, j, "green", origin).apply();
          origin[i][3 * j + 2] = new Filter(this.old, this.kernel, i, j, "blue", origin).apply();
        }
      }
    } else {
      int[][] maskIm = model.getFromMap(this.mask);
      Util util = new Util();
      List<Point> list = util.getPointsNeedToChange(maskIm);
      for (Point point : list) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        if (y % 3 == 0) {
          origin[x][y] = new Filter(this.old, this.kernel, x, y / 3, "red", origin).apply();
        } else if (y % 3 == 1) {
          origin[x][y] = new Filter(this.old, this.kernel, x, y / 3, "green", origin).apply();
        } else {
          origin[x][y] = new Filter(this.old, this.kernel, x, y / 3, "blue", origin).apply();
        }
      }
    }
    model.addToMap(this.dest, origin);
  }
}
