package model.functions.filter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;
import utils.Util;

/**
 * Sharpening function class that uses Filter to sharpen the image.
 * to execute a sharpening operation on given image.
 */
public class Sharpening implements ModelFunctions {
  private final String old;
  private final String mask;
  private final String dest;
  private final double[][] kernel;

//  /**
//   * constructor for sharpening.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public Sharpening(String old, String dest) {
//    this(old, "", dest);
//  }
//
//  /**
//   * constructor for sharpening.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public Sharpening(String old, String mask, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//    kernel = new double[5][5];
//    kernel[0][0] = -0.125;
//    kernel[0][1] = -0.125;
//    kernel[0][2] = -0.125;
//    kernel[0][3] = -0.125;
//    kernel[0][4] = -0.125;
//    kernel[1][0] = -0.125;
//    kernel[1][1] = 0.25;
//    kernel[1][2] = 0.25;
//    kernel[1][3] = 0.25;
//    kernel[1][4] = -0.125;
//    kernel[2][0] = -0.125;
//    kernel[2][1] = 0.25;
//    kernel[2][2] = 1;
//    kernel[2][3] = 0.25;
//    kernel[2][4] = -0.125;
//    kernel[3][0] = -0.125;
//    kernel[3][1] = 0.25;
//    kernel[3][2] = 0.25;
//    kernel[3][3] = 0.25;
//    kernel[3][4] = -0.125;
//    kernel[4][0] = -0.125;
//    kernel[4][1] = -0.125;
//    kernel[4][2] = -0.125;
//    kernel[4][3] = -0.125;
//    kernel[4][4] = -0.125;
//  }

  /**
   * Constructor for the Sharpening function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public Sharpening(ArrayList<String> params) {
    this.old = Objects.requireNonNull(params.get(0));
    if (params.size() == 2){
      this.mask = "";
      this.dest = Objects.requireNonNull(params.get(1));
    } else {
      this.mask = Objects.requireNonNull(params.get(1));
      this.dest = Objects.requireNonNull(params.get(2));
    }
    kernel = new double[5][5];
    kernel[0][0] = -0.125;
    kernel[0][1] = -0.125;
    kernel[0][2] = -0.125;
    kernel[0][3] = -0.125;
    kernel[0][4] = -0.125;
    kernel[1][0] = -0.125;
    kernel[1][1] = 0.25;
    kernel[1][2] = 0.25;
    kernel[1][3] = 0.25;
    kernel[1][4] = -0.125;
    kernel[2][0] = -0.125;
    kernel[2][1] = 0.25;
    kernel[2][2] = 1;
    kernel[2][3] = 0.25;
    kernel[2][4] = -0.125;
    kernel[3][0] = -0.125;
    kernel[3][1] = 0.25;
    kernel[3][2] = 0.25;
    kernel[3][3] = 0.25;
    kernel[3][4] = -0.125;
    kernel[4][0] = -0.125;
    kernel[4][1] = -0.125;
    kernel[4][2] = -0.125;
    kernel[4][3] = -0.125;
    kernel[4][4] = -0.125;
  }

  /**
   * apply method provide functionality to sharpening the given image.
   *
   * @param model is instance of {@link IModel} that the function works on.
   */
  @Override
  public void apply(IModel model) {
    int[][] origin = model.getFromMap(old);
    if (this.mask.equals("")) {
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
