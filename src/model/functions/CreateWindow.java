package model.functions;

import java.util.Objects;

import model.IModel;

public class CreateWindow implements ModelFunctions {
  private final String old;
  private final int x;
  private final int y;
  private final String mask;
  private final String dest;

  public CreateWindow(String old, String mask, String dest, int x, int y) {
    this.old = Objects.requireNonNull(old);
    this.mask = Objects.requireNonNull(mask);
    this.dest = Objects.requireNonNull(dest);
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Cannot create a window with negative coordinates");
    }
    this.x = x;
    this.y = y;
  }

  @Override
  public void apply(IModel model) {
    int[][] image = model.getFromMap(this.old);
    int width = image[0][0];
    int height = image[0][1];
    int[][] maskIm = new int[height + 1][width * 3];
    int[][] resultIm = new int[height + 1][width * 3];

    maskIm[0][0] = width;
    maskIm[0][1] = height;
    for (int i = 1; i < height + 1; i++) {
      for (int j = 0; j < width * 3; j++) {
        maskIm[i][j] = 255;
      }
    }

    int rowIndex = Math.min(maskIm[0][1], 200);
    int colIndex = Math.min(maskIm[0][0]*3, 600);
    for (int i = 0; i < rowIndex; i++) {
      for (int j = 0; j < colIndex; j++) {
        maskIm[y + i + 1][3 * x + j] = 0;
      }
    }

    model.addToMap(mask, maskIm);

    for (int i = 0; i < height + 1; i++) {
      for (int j = 0; j < width * 3; j++) {
        resultIm[i][j] = image[i][j];
      }
    }
    model.addToMap(dest, resultIm);
  }
}
