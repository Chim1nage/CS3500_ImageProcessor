package model;

import java.util.HashMap;
import java.util.Map;

import model.functions.ModelFunctions;
import model.histogram.IHistogram;

/**
 * Class ImageModel represents a image processor.
 * It implements {@link IModel}.
 * It contains a map with name and the image.
 * It provides functionality to apply function object on designated image.
 */
public class ImageModel implements IModel {
  private final Map<String, int[][]> map;

  /**
   * Constructor for the image model class.
   */
  public ImageModel() {
    this.map = new HashMap<>();
  }

  /**
   * Applies a function object {@link ModelFunctions function} on the model.
   *
   * @param function is an instance of {@link ModelFunctions}
   */
  @Override
  public void apply(ModelFunctions function) {
    function.apply(this);
  }

  /**
   * Applies a strategy function object {@link IHistogram histogram} on the model.
   *
   * @param histogram is an instance of {@link IHistogram}
   */
  @Override
  public Map<Integer, Integer> apply(IHistogram histogram) {

    return histogram.summary(this);
  }

  /**
   * Gets the image according to the {@link String name}.
   *
   * @param name is an instance of {@link String} which is
   *             the name of image
   * @return the image
   */
  @Override
  public int[][] getFromMap(String name) {
    int[][] need = null;
    try {
      need = this.map.get(name);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Cannot find original image");
    }

    int width = need[0][0];
    int height = need[0][1];
    int[][] result = new int[height + 1][3 * width];
    for (int i = 0; i < height + 1; i++) {
      for (int j = 0; j < 3 * width; j++) {
        result[i][j] = need[i][j];
      }
    }
    return result;
  }

  /**
   * Adds an image to the model's map for storage.
   *
   * @param name  is {@link String name} of the image
   * @param image the added image
   */
  @Override
  public void addToMap(String name, int[][] image) {
    this.map.put(name, image);
  }
}
