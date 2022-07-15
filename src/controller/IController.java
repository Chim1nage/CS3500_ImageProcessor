package controller;

/**
 * Represents the supported functionality of an image processor controller.
 */
public interface IController {

  /**
   * Runs the controller. Accepts input from the user, passes it to the model, informing
   * the view what to output to the supplied Appendable.
   *
   * @throws IllegalStateException when the program is not quit, and the Readable has run out
   *                               of inputs. Or when appending output from the view to the
   *                               Appendable fails.
   */
  public void startProcessor() throws IllegalStateException;

  /**
   * Retrieves an image file to be operation on. Support files of type: ppm, png, jpg, and bmp.
   *
   * @param imagePath path to the image file
   * @param imageName name of the image file
   * @throws IllegalArgumentException if either parameter is null, the imagePath cannot be found,
   *                                  or the image type is unknown
   */
  public void load(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Saves the current state of the image to the file directory. Support files of type: ppm, png,
   * jpg, and bmp.
   *
   * @param imagePath path to the image file.
   * @param imageName name of the image file.
   * @throws IllegalArgumentException if either parameter is null, the imageName cannot be found,
   *                                  or if the file save failed
   */
  public void save(String imagePath, String imageName) throws IllegalArgumentException;

}

