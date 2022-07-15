package model.adapter;

import java.util.Map;

import model.util.GeneralImage;

/**
 * Adapter interface used to limit access to model method when passed into the view.
 */
public interface IViewModel {

  /**
   * Retrieves the model's loaded images.
   *
   * @return the model's loaded image map
   */
  public Map<String, GeneralImage> getLoadedImages();

}

