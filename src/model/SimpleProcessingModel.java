package model;

import java.util.HashMap;
import java.util.Map;

import model.util.GeneralImage;
import model.util.Pixel;

/**
 * Maintains the state of an image processing model. Loads and saves images, and allows
 * for various operations to be applied.
 */
public class SimpleProcessingModel implements IProcessingModel {

  protected final Map<String, GeneralImage> images;
  protected int width = -1;
  protected int height = -1;
  protected int maxValue = -1;
  protected GeneralImage photo;

  /**
   * Initializes this model with an empty map of image names to images.
   */
  public SimpleProcessingModel() {
    this.images = new HashMap<>();
  }

  /**
   * Flips an image either horizontally or vertically.
   *
   * @param horizontal    the direction to flip: true is horizontal, false is vertical
   * @param imageName     the image to flip
   * @param destImageName destination path name of the image file
   * @throws IllegalArgumentException if either parameter is null or the imageName cannot be found
   */
  @Override
  public void flip(boolean horizontal, String imageName, String destImageName)
          throws IllegalArgumentException {
    checkNull(imageName, destImageName);
    checkHasImage(imageName, "flip");

    this.photo = this.images.get(imageName);
    Pixel[][] oldPixels = photo.getPixels();
    this.width = this.photo.getWidth();
    this.height = this.photo.getHeight();
    this.maxValue = this.photo.getMaxValue();
    Pixel[][] newPixels = new Pixel[this.height][this.width];

    int x;
    int y;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (horizontal) {
          x = i;
          y = width - j - 1;
        } else {
          x = height - i - 1;
          y = j;
        }
        newPixels[x][y] = oldPixels[i][j];
      }
    }

    this.images.put(destImageName, new GeneralImage(newPixels, width, height, maxValue));
  }

  /**
   * Modifies the brightness of an image. Can increase or decrease.
   *
   * @param increment     value added to each RGB value
   * @param imageName     name of the image file
   * @param destImageName destination path of the image file
   * @throws IllegalArgumentException if a parameter is null or the imageName cannot be found
   */
  @Override
  public void brighten(int increment, String imageName, String destImageName)
          throws IllegalArgumentException {
    checkNull(imageName, destImageName);
    checkHasImage(imageName, "brighten");

    photo = this.images.get(imageName);
    Pixel[][] oldPixels = photo.getPixels();
    this.width = photo.getWidth();
    this.height = photo.getHeight();
    this.maxValue = photo.getMaxValue();
    Pixel[][] newPixels = new Pixel[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel newPixel;
        newPixel = oldPixels[row][col].brighten(increment, this.maxValue);
        int[] newVals = new int[]{newPixel.getRed(), newPixel.getGreen(), newPixel.getBlue()};
        int[] oldVals = new int[]{oldPixels[row][col].getRed(), oldPixels[row][col].getGreen(),
                oldPixels[row][col].getBlue()};
        newPixels[row][col] = attemptManipulation(row, col, newVals, oldVals);
      }
    }
    this.images.put(destImageName, new GeneralImage(newPixels, width, height, maxValue));
  }

  /**
   * Visualizes a component on an image greyscale.
   *
   * @param type          of the greyscale component to visualize
   * @param imageName     name of the image
   * @param destImageName destination path of the image file
   * @throws IllegalArgumentException if a parameter is null or the imageName cannot be found
   */
  @Override
  public void componentGreyScale(GreyscaleType type, String imageName, String destImageName)
          throws IllegalArgumentException {
    checkNull(imageName, destImageName);
    checkHasImage(imageName, "greyscale-component");

    photo = this.images.get(imageName);
    Pixel[][] oldPixels = photo.getPixels();
    this.width = photo.getWidth();
    this.height = photo.getHeight();
    this.maxValue = photo.getMaxValue();
    Pixel[][] newPixels = new Pixel[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Pixel oldPixel = oldPixels[row][col];
        int[] oldVals = new int[]{oldPixel.getRed(), oldPixel.getGreen(), oldPixel.getBlue()};
        Pixel newPixel = oldPixels[row][col].greyscale(type);
        int[] newVals = new int[]{newPixel.getRed(), newPixel.getGreen(), newPixel.getBlue()};
        newPixels[row][col] = attemptManipulation(row, col, newVals, oldVals);
      }
    }
    this.images.put(destImageName, new GeneralImage(newPixels, width, height, maxValue));
  }

  protected Pixel attemptManipulation(int row, int col, int[] clomped, int[] originalValues) {
    return new Pixel(clomped[0], clomped[1], clomped[2]);
  }

  /**
   * Checks if either of the two parameters are null.
   *
   * @param s1 the first String to check for null
   * @param s2 the second String to check for null
   * @throws IllegalArgumentException if either String is null
   */
  protected void checkNull(String s1, String s2) throws IllegalArgumentException {
    try {
      int i1 = s1.charAt(0);
      int i2 = s2.charAt(0);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Null values are illegal");
    }
  }

  /**
   * Checks if the image has been saved to this model.
   *
   * @param imageName    the image name to check
   * @param errorMessage the type of command that is being attempted on the image
   * @throws IllegalArgumentException if the image cannot be found
   */
  protected void checkHasImage(String imageName, String errorMessage)
          throws IllegalArgumentException {
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("The image to " + errorMessage + " cannot be found");
    }
  }

  /**
   * Retrieves the model's loaded images.
   *
   * @return the model's loaded image map
   */
  @Override
  public Map<String, GeneralImage> getLoadedImages() {
    Map<String, GeneralImage> deepCopy = new HashMap<>();
    for (Map.Entry<String, GeneralImage> mapEntry : this.images.entrySet()) {
      deepCopy.put(mapEntry.getKey(), mapEntry.getValue());
    }
    return deepCopy;
  }

  @Override
  public GeneralImage getImage(String imageName) {
    return this.images.getOrDefault(imageName, null);
  }

  @Override
  public void addImage(String imageName, GeneralImage image) {
    this.images.put(imageName, image);
  }


}