package model.functions;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;
import utils.ImageUtil;

/**
 * Save function class that saves a model to a specific destination.
 * It accepts {@link String filePath} and {@link String name}
 * to execute save operation with given image.
 */
public class Save implements ModelFunctions {
  private final String filePath;
  private final String name;

  /**
   * Constructor for the Save, which only accept two parameters.
   * if not two parameters, throw exception.
   *
   * @param params represent the params.
   * @throws IllegalArgumentException when params length is not equal to 2
   */
  public Save(ArrayList<String> params) throws IllegalArgumentException{
    if (params.size() != 2){
      throw new IllegalArgumentException("Arguments exceed demand!");
    }
    this.filePath = Objects.requireNonNull(params.get(0));
    this.name = Objects.requireNonNull(params.get(1));
  }

  /**
   * apply method provide functionality to save the image at designated place.
   *
   * @param model is instance of {@link IModel} that the function works on.
   */
  @Override
  public void apply(IModel model) {
    ImageUtil.saveImage(this.filePath, model.getFromMap(this.name));
  }
}
