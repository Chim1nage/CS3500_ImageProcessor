package model.adapter;

import java.util.Map;

import model.IProcessingModel;
import model.util.GeneralImage;

/**
 * Adapter used to limit access to model method when passed into the view.
 *
 * <p>Invariant: model is not null.
 */
public final class Adapter implements IViewModel {

  private final IProcessingModel model;

  /**
   * Initializes this adapter with the desired model.
   *
   * @param model that the adapter uses, and hides its functionality.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  public Adapter(IProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Supplied model is null");
    }
    this.model = model;
  }


  /**
   * Retrieves the model's loaded images.
   *
   * @return the model's loaded image map
   */
  @Override
  public Map<String, GeneralImage> getLoadedImages() {
    return this.model.getLoadedImages();
  }

}

