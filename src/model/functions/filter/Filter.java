package model.functions.filter;

import java.util.Objects;

import model.IModel;
import model.functions.ModelFunctions;

/**
 * Class Filter represents a pixel filter, which returns the value of a channel of a pixel
 * after multiplying. It is modified from {@link ModelFunctions} which provide same method
 * of {@link ModelFunctions#apply(IModel)}.
 */
public class Filter {
  private final String old;
  private final double[][] kernel;
  private final int row;
  private final int col;
  private final String channel;
  private final int[][] origin;

  /**
   * Constructor for Filter, which accepts five parameters{@link String old}, kernel,
   * {@link Integer row}, {@link Integer col} and {@link String channel}.
   *
   * @param old     is an instance of {@link  String} representing old image name
   * @param kernel  is the matrix multiplied
   * @param row     is an instance of {@link  Integer} representing row of the pixel
   * @param col     is an instance of {@link  Integer} representing column of the pixel
   * @param channel is an instance of {@link  String} representing channel needed to be changed
   */
  public Filter(String old, double[][] kernel, int row, int col, String channel, int[][] origin) {
    this.old = old;
    this.kernel = kernel;
    this.row = row;
    this.col = col;
    this.channel = Objects.requireNonNull(channel);
    this.origin = Objects.requireNonNull(origin);
  }

  /**
   * apply method provide functionality to filter pixel.
   */
  public int apply() {
    int width = this.origin[0][0];
    int height = this.origin[0][1];

    int kernelH = this.kernel.length;
    int kernelW = this.kernel[0].length;

    int middleH = kernelH / 2;
    int middleW = kernelW / 2;

    double sum = 0;

    for (int i = 0; i < kernelH; i++) {
      for (int j = 0; j < kernelW; j++) {
        int deltaH = i - middleH;
        int deltaW = j - middleW;
        double factor = this.kernel[i][j];
        if (!(this.row + deltaH < 1 || this.row + deltaH >= height + 1
                || this.col + deltaW < 0 || this.col + deltaW >= width)) {
          switch (this.channel) {
            case "red":
              sum = sum + factor * origin[this.row + deltaH][(this.col + deltaW) * 3];
              break;
            case "green":
              sum = sum + factor * origin[this.row + deltaH][(this.col + deltaW) * 3 + 1];
              break;
            case "blue":
              sum = sum + factor * origin[this.row + deltaH][(this.col + deltaW) * 3 + 2];
              break;
            default:
              break;
          }

        }
      }
    }
    if (sum > 255) {
      return 255;
    } else if (sum < 0) {
      return 0;
    } else {
      return (int) sum;
    }
  }
}
