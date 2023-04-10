package model.functions.colorTransformation;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;
import utils.Util;

/**
 * Class ColorTransformation represent a Pixel processor, which
 * uses a given matrix to transform every pixel's RGB.
 * It accepts {@link String old}, {@link String dest} and matrix.
 * It implements {@link ModelFunctions}.
 */
public class ColorTransformation implements ModelFunctions {
  private final String old;
  private final String dest;
  private final double[][] matrix;
  private final String mask;

  /**
   * Constructor for color transformation function, which accepts
   * {@link String old}, {@link String dest} and matrix.
   *
   * @param old    is instance of {@link String} representing old image name
   * @param dest   is instance of {@link String} representing new image name
   * @param matrix represents matrix that will be applied on each pixel
   */
  public ColorTransformation(String old, String dest, double[][] matrix) {
    this(old, dest, "", matrix);
  }

  /**
   * Constructor for color transformation function, which accepts
   * {@link String old}, {@link String dest} and matrix.
   *
   * @param old    is instance of {@link String} representing old image name
   * @param dest   is instance of {@link String} representing new image name
   * @param matrix represents matrix that will be applied on each pixel
   */
  public ColorTransformation(String old, String dest, String mask, double[][] matrix) {
    this.old = Objects.requireNonNull(old);
    this.dest = Objects.requireNonNull(dest);
    this.mask = Objects.requireNonNull(mask);
    this.matrix = matrix;
  }

  /**
   * apply method provide functionality to make pixel processing.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    int width = image[0][0];
    int height = image[0][1];
    int[][] result = model.getFromMap(this.old);

    if (mask.equals("")) {
      for (int i = 1; i < height + 1; i++) {
        for (int j = 0; j < width; j++) {
          result[i][3 * j] = this.calculateMatrix(image[i][3 * j], image[i][3 * j + 1],
                  image[i][3 * j + 2], this.matrix, 0);
          result[i][3 * j + 1] = this.calculateMatrix(image[i][3 * j], image[i][3 * j + 1],
                  image[i][3 * j + 2], this.matrix, 1);
          result[i][3 * j + 2] = this.calculateMatrix(image[i][3 * j], image[i][3 * j + 1],
                  image[i][3 * j + 2], this.matrix, 2);
        }
      }
    } else {
      for (int i = 0; i < height + 1; i++) {
        for (int j = 0; j < width; j++) {
          result[i][j] = image[i][j];
        }
      }
      int[][] maskIm = model.getFromMap(this.mask);
      if (maskIm.length != image.length || maskIm[0].length != image[0].length) {
        throw new IllegalStateException("mask size does not fit");
      }
      Util util = new Util();
      List<Point> list = util.getPointsNeedToChange(maskIm);
      for (Point point : list) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        if (y % 3 == 0) {
          result[x][y] = this.calculateMatrix(image[x][y], image[x][y + 1],
                  image[x][y + 2], this.matrix, 0);
        } else if (y % 3 == 1) {
          result[x][y] = this.calculateMatrix(image[x][y - 1], image[x][y],
                  image[x][y + 1], this.matrix, 1);
        } else {
          result[x][y] = this.calculateMatrix(image[x][y - 2], image[x][y - 1],
                  image[x][y], this.matrix, 2);
        }
      }
    }
    model.addToMap(dest, result);
  }

  private int calculateMatrix(int r, int g, int b, double[][] matrix, int row) {
    int result = (int) (r * matrix[row][0] + g * matrix[row][1] + b * matrix[row][2]);
    if (result > 255) {
      return 255;
    } else if (result < 0) {
      return 0;
    } else {
      return result;
    }
  }

}
