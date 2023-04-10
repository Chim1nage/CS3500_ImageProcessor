package model.functions.flip;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;

/**
 * The horizontal flip function.
 * It accepts {@link String old} and {@link String dest}
 * to execute a horizontal flip on given image.
 */
public class HorizontalFlip implements ModelFunctions {
  private final String old;
  private final String dest;

//  /**
//   * Constructor for horizontal flip function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public HorizontalFlip(String old, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.dest = Objects.requireNonNull(dest);
//  }


  /**
   * Constructor for the Horizontal Flip function, which only accept two parameters.
   * if not two parameters, throw exception.
   *
   * @param params represent the params.
   * @throws IllegalArgumentException when params length is not equal to 2
   */
  public HorizontalFlip(ArrayList<String> params) throws IllegalArgumentException{
    if (params.size() != 2){
      throw new IllegalArgumentException("Arguments exceed demand!");
    }
    this.old = Objects.requireNonNull(params.get(0));
    this.dest = Objects.requireNonNull(params.get(1));
  }

  /**
   * apply method provide functionality to flips the given model.
   *
   * @param model is instance of {@link IModel} that the function will apply on.
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    int width = image[0][0];
    int height = image[0][1];

    for (int i = 1; i < height + 1; i++) {
      for (int j = 0; j < width / 2; j++) {
        int r = image[i][3 * j];
        int g = image[i][3 * j + 1];
        int b = image[i][3 * j + 2];
        image[i][3 * j] = image[i][3 * (width - 1 - j)];
        image[i][3 * j + 1] = image[i][3 * (width - 1 - j) + 1];
        image[i][3 * j + 2] = image[i][3 * (width - 1 - j) + 2];
        image[i][3 * (width - 1 - j)] = r;
        image[i][3 * (width - 1 - j) + 1] = g;
        image[i][3 * (width - 1 - j) + 2] = b;
      }
    }

    model.addToMap(this.dest, image);
  }
}
