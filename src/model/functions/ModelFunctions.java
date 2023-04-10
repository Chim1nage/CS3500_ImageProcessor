package model.functions;

import model.IModel;

/**
 * The interface that contains all function objects including brighten, greyscale, flip, etc.
 */
public interface ModelFunctions {
  /**
   * applies the function object on the model.
   *
   * @param model that the function will be applied on
   */
  public void apply(IModel model);
}
