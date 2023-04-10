package model.functions.greyScale;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Function class for blue-component greyscale.
 * to execute a greyScale of blue component on given image.
 */
public class GreyScaleBlue extends GreyScaleAbstract {

//  /**
//   * constructor for blue grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleBlue(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for blue grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleBlue(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleBlue function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleBlue(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the blue component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image is the image operated on
   * @return {@link Integer blue component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    return image[i][3 * j + 2];
  }
}
