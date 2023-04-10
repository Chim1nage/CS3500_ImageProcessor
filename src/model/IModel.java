package model;

import java.util.Map;

import model.functions.ModelFunctions;
import model.histogram.IHistogram;

/**
 * The interface IModel represent an image processor.
 * It provides functionalities of load, save, flipping,
 * brightening, darkening, and greyscale the image.
 */
public interface IModel {
  /**
   * Applies a function object {@link ModelFunctions function} on the model.
   *
   * @param function is an instance of {@link ModelFunctions}
   */
  void apply(ModelFunctions function);

  /**
   * Applies a strategy function object {@link IHistogram histogram} on the model.
   *
   * @param histogram is an instance of {@link IHistogram}
   */
  Map<Integer, Integer> apply(IHistogram histogram);

  /**
   * Gets the image according to the {@link String name}.
   *
   * @param name is an instance of {@link String} which is
   *             the name of image
   * @return the image
   */
  int[][] getFromMap(String name);

  /**
   * Adds an image to the model's Map for storage.
   *
   * @param name  is {@link String name} of the image
   * @param image the added image
   */
  void addToMap(String name, int[][] image);
}