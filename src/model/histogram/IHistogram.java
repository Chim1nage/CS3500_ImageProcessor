package model.histogram;

import java.util.HashMap;

import model.IModel;

/**
 * A strategy interface that is used to form a hashmap that contains values of each pixel and
 * their corresponding frequencies.
 */
public interface IHistogram {
  /**
   * A method that given the model, it will calculate the hashmap of values of pixel and
   * corresponding frequency after a custom grey scaling.
   *
   * @param model Imodel used
   * @return the hashMap containing the information
   */
  HashMap<Integer, Integer> summary(IModel model);
}
