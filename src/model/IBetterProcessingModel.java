package model;

/**
 * Represents a better processing model that offers more filters and color transformations to edit
 * images.
 */
public interface IBetterProcessingModel extends IProcessingModel {

  /**
   * Sharpens an image by applying a kernel.
   *
   * @param imageName     name of image to sharpen.
   * @param destImageName name of image after sharpened.
   * @throws IllegalArgumentException when imageName is invalid or if a parameter is null.
   */
  public void sharpen(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Blurs an image by applying a kernel.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName is invalid or if a parameter is null.
   */
  public void blur(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Applies a sepia color transformation to an image.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName is invalid.
   */
  public void sepia(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Applies a greyscale color transformation to an image.
   *
   * @param imageName     name of image to blur.
   * @param destImageName name of image after blurred.
   * @throws IllegalArgumentException when imageName to greyscale is invalid.
   */
  public void greyscale(String imageName, String destImageName) throws IllegalArgumentException;
}
