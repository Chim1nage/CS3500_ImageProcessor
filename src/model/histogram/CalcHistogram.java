package model.histogram;

import java.util.HashMap;
import java.util.Objects;

import model.IModel;

/**
 * A function object that can calculate the specific histogram for an image after grey scaling.
 */
public class CalcHistogram {
  private final String name;
  private final String component;
  private final IModel model;

  /**
   * Constructor of the function object.
   *
   * @param name      name of the image stored inside the model
   * @param component the component to greyscale on
   * @param model     the model used
   */
  public CalcHistogram(String name, String component, IModel model) {
    this.name = Objects.requireNonNull(name);
    this.component = Objects.requireNonNull(component);
    this.model = Objects.requireNonNull(model);
  }

  /**
   * A method that can return the hashmap that contains 0 - 255 as key and their frequency as
   * value after performing a grey scale on the original image.
   *
   * @return the hashMap that contains the frequency of each value
   * @throws IllegalArgumentException if the given component is not verified in the method
   */
  public HashMap<Integer, Integer> calc() throws IllegalArgumentException {
    HashMap<Integer, Integer> map = new HashMap<>();

    int[][] temp = this.model.getFromMap(this.name);
    int width = temp[0][0];
    int height = temp[0][1];

    // Initialize map
    for (int i = 0; i < 256; i++) {
      map.put(i, 0);
    }

    for (int i = 1; i < height + 1; i++) {
      for (int j = 0; j < width; j++) {
        int value = -1;
        switch (this.component.toLowerCase()) {
          case "red":
            value = temp[i][3 * j];
            break;
          case "green":
            value = temp[i][3 * j + 1];
            break;
          case "blue":
            value = temp[i][3 * j + 2];
            break;
          case "intensity":
            value = (temp[i][3 * j] + temp[i][3 * j + 1] + temp[i][3 * j + 2]) / 3;
            break;
          default:
            throw new IllegalArgumentException("Cannot find color");
        }
        map.put(value, map.get(value) + 1);
      }
    }
    return map;
  }
}
