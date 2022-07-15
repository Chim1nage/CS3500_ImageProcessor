package model;

import java.util.Map;

/**
 * Represents the final processing model, which offers (value,intensity) table generation,
 * downscaling, and partial image manipulation (PIM) for select operations.
 */
public interface IFinalProcessingModel extends IBetterProcessingModel {


  /**
   * Generates the frequency of every value 0 through 255 in a given image, divided into three
   * color channels and their average (intensity).
   *
   * @param imageName the image to generate the value frequency table of
   * @return the (value,intensity) table
   * @throws IllegalArgumentException if the provided image cannot be found in the model or is null
   */
  public Map<Integer, int[]> generateFrequencies(String imageName) throws IllegalArgumentException;

  /**
   * Blurs an image, such that only pixels corresponding to a black pixel in the mask image are
   * blurred.
   *
   * @param imageName     the image to partially blur
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the partially blurred image
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void blur(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Sharpens an image, such that only pixels corresponding to a black pixel in the mask image are
   * sharpened.
   *
   * @param imageName     the image to partially sharpen
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the partially sharpened image
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void sharpen(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Applies a sepia filter to an image, such that only pixels corresponding to a black pixel in
   * the mask image receive the sepia effect.
   *
   * @param imageName     the image to partially apply a sepia filter to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial sepia filter
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void sepia(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Modifies the brightness of an image such that only pixels corresponding to a black pixel in
   * the mask image receive the change in brightness. Brightness can go up or down.
   *
   * @param increment     the amount to modify the brightness by
   * @param imageName     the image to change the brightness of
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image whose brightness has been partially modified
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void brighten(int increment, String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Applies a luma greyscale filter to an image, such that only pixels corresponding to a black
   * pixel in the mask image receive the luma greyscale filter.
   *
   * @param imageName     the image to partially apply a luma greyscale filter to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial luma greyscale filer
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void greyscale(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Applies a greyscale visualization to an image, such that only pixels corresponding to a black
   * pixel in the mask image receive the greyscale visualization.
   *
   * @param type          the type of greyscale component to visualize
   * @param imageName     the image to partially apply a greyscale visualization to
   * @param maskImageName the mask image of imageName, which is a black and white version with the
   *                      exact same dimensions
   * @param destImageName the name of the image which has received a partial greyscale
   *                      visualization
   * @throws IllegalArgumentException if the imageName or maskImageName cannot be found, or if
   *                                  any of the parameters are null
   */
  public void componentGreyScale(GreyscaleType type, String imageName, String maskImageName,
                                 String destImageName) throws IllegalArgumentException;

  /**
   * Scales down the size of an image by a scalar width and height value. The percentage that each
   * value is equal to in decimal form is the percentage of the original image that is kept.
   * After computations, I round down on the numbers to prevent overflow over the max value
   * of images.
   *
   * @param imageName         the image to scale down
   * @param destImageName     the name of the scaled image
   * @param widthScaleFactor  the scalar to scale down the width of the image by. If you want width
   *                          to stay the same, enter 1.0. If you want it to decrease, input the
   *                          decimal percentage of the width that you want to keep (example:
   *                          0.7 results in an image with 70% of the width).
   * @param heightScaleFactor the scalar to scale down the height of the image by If you want height
   *                          to stay the same, enter 1.0. If you want it to decrease,
   *                          input the decimal percentage of the height that you want to keep
   *                          (example: 0.3 results in an image with 30% of the original height).
   */
  public void downScale(String imageName, String destImageName, double widthScaleFactor,
                        double heightScaleFactor);

}
