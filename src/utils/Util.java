package utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
  public List<Point> getPointsNeedToChange(int[][] mask) {
    int maskW = mask[0][0];
    int maskH = mask[0][1];
    List<Point> list = new ArrayList<>();
    for (int i = 1; i < maskH + 1; i++) {
      for (int j = 0; j < maskW * 3; j++) {
        if (mask[i][j] == 0) {
          list.add(new Point(i, j));
        }
      }
    }
    return list;
  }

}
