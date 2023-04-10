package model.functions.greyScale;

import java.util.ArrayList;

/**
 * Function class for intensity-component greyscale.
 * to execute a greyScale of intensity on given image.
 */

public class GreyScaleIntensity extends GreyScaleAbstract {

//  /**
//   * Constructor for Intensity grey scaling.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleIntensity(String old, String dest) {
//    super(old, dest);
//  }
//
//  /**
//   * constructor for intensity grey scaling function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask  is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScaleIntensity(String old, String mask, String dest) {
//    super(old, mask, dest);
//  }

  /**
   * Constructor for the GreyScaleIntensity function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScaleIntensity(ArrayList<String> params) {
    super(params);
  }

  /**
   * Returns the Intensity component.
   *
   * @param i     is {@link Integer height of pixel}
   * @param j     is {@link Integer width of pixel}
   * @param image the image operated on
   * @return {@link Integer intensity component}
   */
  @Override
  protected int getValue(int i, int j, int[][] image) {
    return (image[i][3 * j] + image[i][3 * j + 1] + image[i][3 * j + 2]) / 3;
  }
}
