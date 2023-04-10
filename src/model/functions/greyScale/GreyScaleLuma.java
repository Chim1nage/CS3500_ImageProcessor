package model.functions.greyScale;

import java.util.ArrayList;

/**
 * Function class for luma-component greyscale.
 * to execute a greyScale of luma on given image.
 */
public class GreyScaleLuma extends GreyScaleAbstract {

//  /**
//   * Constructor for luma grey scaling.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleLuma(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for luma grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleLuma(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleLuma function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleLuma(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the Luma component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer luma component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    return (int) (image[i][3 * j] * 0.2126
            + image[i][3 * j + 1] * 0.7152
            + image[i][3 * j + 2] * 0.0722);
  }
}
