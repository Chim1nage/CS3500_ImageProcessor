package model.functions.greyScale;

import java.util.ArrayList;

/**
 * Function class for green-component greyscale.
 * to execute a greyScale of green component on given image.
 */
public class GreyScaleGreen extends GreyScaleAbstract {

//  /**
//   * Constructor for green grey scaling.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleGreen(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for green grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleGreen(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleGreen function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleGreen(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the green component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer green component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    return image[i][3 * j + 1];
  }
}
