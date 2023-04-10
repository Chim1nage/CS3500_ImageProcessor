package model.functions.colorTransformation;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;

/**
 * Sepia tone function class that uses ColorTransformation to
 * change an image to a certain style.
 * to execute a SepiaTone style on given image.
 */
public class SepiaTone implements ModelFunctions {
  private final String old;
  private final String mask;
  private final String dest;
//
//  /**
//   * Constructor for SepiaTone.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public SepiaTone(String old, String dest) {
//    this(old, "", dest);
//  }
//
//  /**
//   * constructor for SepiaTone function.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public SepiaTone(String old, String mask, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//  }

  /**
   * Constructor for the SepiaTone function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public SepiaTone(ArrayList<String> params) {
    this.old = Objects.requireNonNull(params.get(0));
    if (params.size() == 2){
      this.mask = "";
      this.dest = Objects.requireNonNull(params.get(1));
    } else {
      this.mask = Objects.requireNonNull(params.get(1));
      this.dest = Objects.requireNonNull(params.get(2));
    }
  }

  /**
   * apply method provide functionality to add sepia tone style on the given image.
   *
   * @param model is instance of {@link IModel} that the function works on.
   */
  @Override
  public void apply(IModel model) {
    double[][] matrix = new double[3][3];
    matrix[0][0] = 0.393;
    matrix[0][1] = 0.769;
    matrix[0][2] = 0.189;
    matrix[1][0] = 0.349;
    matrix[1][1] = 0.686;
    matrix[1][2] = 0.168;
    matrix[2][0] = 0.272;
    matrix[2][1] = 0.534;
    matrix[2][2] = 0.131;
    if (this.dest.equals("")) {
      new ColorTransformation(this.old, this.dest, matrix).apply(model);
    } else {
      new ColorTransformation(this.old, this.dest, this.mask, matrix).apply(model);
    }

  }
}