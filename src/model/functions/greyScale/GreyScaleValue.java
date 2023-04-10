package model.functions.greyScale;

import java.util.ArrayList;

/**
 * Function class for value-component greyscale.
 * to execute a greyScale of value on given image.
 */
public class GreyScaleValue extends GreyScaleAbstract {

//  /**
//   * Constructor for value grey scaling.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleValue(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for value grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleValue(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleValue function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleValue(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the value component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer value component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    int result = Math.max(image[i][3 * j], image[i][3 * j + 1]);
    result = Math.max(result, image[i][3 * j + 2]);
    return result;
  }
}
