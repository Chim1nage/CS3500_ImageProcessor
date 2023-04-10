package model.functions.greyScale;

import java.util.ArrayList;
import java.util.Objects;

import model.IModel;
import model.functions.colorTransformation.ColorTransformation;
import model.functions.ModelFunctions;

/**
 * Grey scale function class that is similar to GreyScaleLuma using ColorTransformation.
 * to execute a greyScale by matrix on given image.
 */
public class GreyScale implements ModelFunctions {
  private final String old;
  private final String mask;
  private final String dest;

//  /**
//   * constructor for GreyScale.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScale(String old, String dest) {
//    this(old, "", dest);
//  }
//
//  /**
//   * constructor for GreyScale.
//   *
//   * @param old  is instance of {@link String} representing old image name
//   * @param mask is instance of {@link String} representing mask image name
//   * @param dest is instance of {@link String} representing new image name
//   */
//  public GreyScale(String old, String mask, String dest) {
//    this.old = Objects.requireNonNull(old);
//    this.mask = Objects.requireNonNull(mask);
//    this.dest = Objects.requireNonNull(dest);
//  }

  /**
   * Constructor for the GreyScale function, which accept numerous parameters.
   *
   * @param params represent the params.
   */
  public GreyScale(ArrayList<String> params) {
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
   * apply method provide functionality to do greyscale operation.
   *
   * @param model is instance of {@link IModel} that the function will apply on
   */
  @Override
  public void apply(IModel model) {
    double[][] matrix = new double[3][3];
    matrix[0][0] = 0.2126;
    matrix[0][1] = 0.7152;
    matrix[0][2] = 0.0722;
    matrix[1][0] = 0.2126;
    matrix[1][1] = 0.7152;
    matrix[1][2] = 0.0722;
    matrix[2][0] = 0.2126;
    matrix[2][1] = 0.7152;
    matrix[2][2] = 0.0722;
    if (this.mask.equals("")) {
      new ColorTransformation(this.old, this.dest, matrix).apply(model);
    } else {
      new ColorTransformation(this.old, this.dest, this.mask, matrix).apply(model);
    }
  }
}