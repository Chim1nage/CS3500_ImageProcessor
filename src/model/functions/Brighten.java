package model.functions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.IModel;
import utils.Util;

/**
 * The Brighten function that brightens or darkens the image.
 * to execute a brighten operation on given image.
 */
public class Brighten implements ModelFunctions {
  private final int delta;
  private final String old;
  private final String mask;
  private final String dest;
//
//  /**
//   * Constructor for the Brighten function, which accept
//   * three parameter {@link Integer delta}, {@link String old}
//   * and {@link String dest}.
//   *
//   * @param delta is instance of {@link Integer} representing the change in brightness
//   * @param old   is instance of {@link String} representing old image name
//   * @param dest  is instance of {@link String} representing new image name
//   */
//  public Brighten(int delta, String old, String dest) {
//    this(delta, old, "", dest);
//  }
//
//  /**
//   * Constructor for the Brighten function, which accept
//   * three parameter {@link Integer delta}, {@link String old}
//   * and {@link String dest}.
//   *
//   * @param delta is instance of {@link Integer} representing the change in brightness
//   * @param old   is instance of {@link String} representing old image name
//   * @param dest  is instance of {@link String} representing new image name
//   */
//  public Brighten(int delta, String old, String mask, String dest) {
//    this.delta = delta;
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//  }

  /**
   * Constructor for the Brighten function, which accept numerous parameters.
   *
   * @param delta is instance of {@link Integer} representing the change in brightness
   * @param params represent the params.
   */
  public Brighten(int delta, ArrayList<String> params) {
    this.delta = delta;
    this.old = Objects.requireNonNull(params.get(0));
    if (params.size() == 2){
      this.mask = "";
      this.dest = Objects.requireNonNull(params.get(1));
    } else {
      this.mask = Objects.requireNonNull(params.get(1));
      this.dest = Objects.requireNonNull(params.get(2));
    }
  }

  /**
   * apply method provide functionality to do brightening operation.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    if (this.mask.equals("")) {
      int width = image[0][0];
      int height = image[0][1];

      for (int i = 1; i < height + 1; i++) {
        for (int j = 0; j < 3 * width; j++) {
          int t = image[i][j] + this.delta;
          image[i][j] = Math.max(0, Math.min(255, t));
        }
      }
    } else {
      int[][] maskIm = model.getFromMap(this.mask);
      Util util = new Util();
      List<Point> list = util.getPointsNeedToChange(maskIm);
      for (Point point : list) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int t = image[x][y] + this.delta;
        image[x][y] = Math.max(0, Math.min(255, t));
      }
    }
    model.addToMap(this.dest, image);
  }
}
