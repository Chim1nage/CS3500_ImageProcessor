package model;

import model.adapter.IViewModel;
import model.util.GeneralImage;

/**
 * This interface represents operations that can be used to retrieve output or
 * manipulate images.
 */
public interface IProcessingModel extends IViewModel {

  /**
   * Flips an image either horizontally or vertically.
   *
   * @param horizontal    the direction to flip: true is horizontal, false is vertical
   * @param imageName     the image to flip
   * @param destImageName destination path of the image file
   * @throws IllegalArgumentException if a parameter is null or the imageName cannot be found
   */
  public void flip(boolean horizontal, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Modifies the brightness of an image.
   *
   * @param increment     value added to each RGB value
   * @param imageName     name of the image file
   * @param destImageName destination path of the image file
   * @throws IllegalArgumentException if a parameter is null or the imageName cannot be found
   */
  public void brighten(int increment, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Visualizes a component on an image greyscale.
   *
   * @param type          of the greyscale component to visualize
   * @param imageName     name of the image
   * @param destImageName destination path of the image file
   * @throws IllegalArgumentException if a parameter is null or the imageName cannot be found
   */
  public void componentGreyScale(GreyscaleType type, String imageName, String destImageName)
          throws IllegalArgumentException;

  /**
   * Retrieves image by the supplied name from storage within this model.
   *
   * @param imageName name of the image to be retrieved.
   * @return image stored under the name, or null if it is not there.
   */
  public GeneralImage getImage(String imageName);

  /**
   * Adds image to the model's storage of information.
   *
   * @param imageName name of the image to store it under.
   * @param image     the image to store.
   */
  public void addImage(String imageName, GeneralImage image);

  /**
   * Represents the possible gray-scale manipulations of an image.
   */
  enum GreyscaleType { Red, Green, Blue, Value, Intensity, Luma }

}

