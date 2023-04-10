package model.histogram;

import java.util.HashMap;

import model.IModel;

/**
 * A function objects that uses CalcHistogram to form a hashMap of values corresponding to its
 * frequencies after intensity greyscale.
 */
public class IntensityHistogram implements IHistogram {
  private final String name;

  /**
   * Constructor of the function object.
   *
   * @param name name of the image stored in the model
   */
  public IntensityHistogram(String name) {
    this.name = name;
  }

  @Override
  public HashMap<Integer, Integer> summary(IModel model) {
    return new CalcHistogram(this.name, "intensity", model).calc();
  }
}
