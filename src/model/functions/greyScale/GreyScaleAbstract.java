package model.functions.greyScale;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;
import utils.Util;

/**
 * Abstract class for all grey scale functions, and implements the {@link ModelFunctions}.
 * to execute a greyScale of given type on given image.
 */
public abstract class GreyScaleAbstract implements ModelFunctions {
  protected final String old;
  protected final String mask;
  protected final String dest;

//  /**
//   * Constructor for grey scale function objects.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleAbstract(String old, String dest) {
//    this(old, "", dest);
//  }
//
//  /**
//   * constructor for GreyScale.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleAbstract(String old, String mask, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//  }

  /**
   * Constructor for the GreyScaleAbstract function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleAbstract(ArrayList<String> params) {
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
   * apply method provide functionality to coverts the model to an x-component greyscale.
   *
   * @param model is instance of {@link IModel} that the function will be applied on
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    if (this.mask.equals("")) {
      int width = image[0][0];
      int height = image[0][1];

      for (int i = 1; i < height + 1; i++) {
        for (int j = 0; j < width; j++) {
          int value = this.getValue(i, j, image);
          image[i][3 * j] = value;
          image[i][3 * j + 1] = value;
          image[i][3 * j + 2] = value;
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
          int value = this.getValue(x, y / 3, image);
          image[x][y] = value;
          image[x][y + 1] = value;
          image[x][y + 2] = value;
        }
      }
    }
    model.addToMap(this.dest, image);
  }

  /**
   * Gets the value needed to change to for each pixel.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer objective value}
   */
  protected abstract int getValue(int i, int j, int[][] image);
}
