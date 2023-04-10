package model.functions.flip;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;

/**
 * A function object that vertically flips the image.
 * It implements {@link ModelFunctions}.
 * It accepts {@link String old} and {@link String dest}
 * to execute a vertically flip on given image.
 */
public class VerticalFlip implements ModelFunctions {
  private final String old;
  private final String dest;

//  /**
//   * Constructor for Vertical flip function, which accept
//   * two parameters {@link String old} and {@link String new}.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public VerticalFlip(String old, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.dest = Objects.requireNonNull(dest);
//  }

  /**
   * Constructor for the Vertical Flip function, which only accept two parameters.
   * if not two parameters, throw exception.
   *
   * @param params represent the params.
   * @throws IllegalArgumentException when params length is not equal to 2
   */
  public VerticalFlip(ArrayList<String> params) throws IllegalArgumentException{
    if (params.size() != 2){
      throw new IllegalArgumentException("Arguments exceed demand!");
    }
    this.old = Objects.requireNonNull(params.get(0));
    this.dest = Objects.requireNonNull(params.get(1));
  }

  /**
   * apply method provide functionality to flips the model vertically.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    int width = image[0][0];
    int height = image[0][1];

    for (int i = 1; i < (height + 2) / 2; i++) {
      for (int j = 0; j < width * 3; j++) {
        int temp = image[i][j];
        image[i][j] = image[height - (i - 1)][j];
        image[height - (i - 1)][j] = temp;
      }
    }

    model.addToMap(this.dest, image);
  }
}
