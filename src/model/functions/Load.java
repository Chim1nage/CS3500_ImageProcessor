package model.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import model.IModel;
import utils.ImageUtil;

/**
 * Load function class that loads an image into the model.
 * It accepts {@link String filePath} and {@link String name}
 * to execute a load operation with given image into {@link IModel model}.
 */
public class Load implements ModelFunctions {
  private final String filePath;
  private final String name;

//  /**
//   * constructor for load.
//   *
//   * @param filePath is an instance of {@link String} representing the file path of the image
//   * @param name     is an instance of {@link String} representing a name of image in the model
//   */
//  public Load(String filePath, String name) {
//    this.filePath = Objects.requireNonNull(filePath);
//    this.name = Objects.requireNonNull(name);
//  }

  /**
   * Constructor for the Load, which only accept two parameters.
   * if not two parameters, throw exception.
   *
   * @param params represent the params.
   * @throws IllegalArgumentException when params length is not equal to 2
   */
  public Load(ArrayList<String> params) throws IllegalArgumentException{
    if (params.size() != 2){
      throw new IllegalArgumentException("Arguments exceed demand!");
    }
    this.filePath = Objects.requireNonNull(params.get(0));
    this.name = Objects.requireNonNull(params.get(1));
  }

  /**
   * apply method provide functionality to load the image to {@link IModel model}.
   *
   * @param model is intance of {@link IModel} that the function works on.
   */
  @Override
  public void apply(IModel model) {
    int[][] input = ImageUtil.readImage(this.filePath);
    model.addToMap(this.name, input);
  }
}
