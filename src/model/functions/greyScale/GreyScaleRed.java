package model.functions.greyScale;

import java.util.ArrayList;

/**
 * Function class for red-component greyscale.
 * to execute a greyScale of red component on given image.
 */
public class GreyScaleRed extends GreyScaleAbstract {

//  /**
//   * Constructor for red grey scaling.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleRed(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for red grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleRed(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleRed function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleRed(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the red component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer red component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    return image[i][3 * j];
  }
}
