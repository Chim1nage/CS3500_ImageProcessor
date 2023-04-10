package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.functions.Brighten;
import model.functions.Downsize;
import model.functions.Load;
import model.functions.Save;
import model.functions.colorTransformation.SepiaTone;
import model.functions.filter.Blur;
import model.functions.filter.Sharpening;
import model.functions.flip.HorizontalFlip;
import model.functions.flip.VerticalFlip;
import model.functions.greyScale.GreyScale;
import model.functions.greyScale.GreyScaleBlue;
import model.functions.greyScale.GreyScaleGreen;
import model.functions.greyScale.GreyScaleIntensity;
import model.functions.greyScale.GreyScaleLuma;
import model.functions.greyScale.GreyScaleRed;
import model.functions.greyScale.GreyScaleValue;
import model.histogram.BlueHistogram;
import model.histogram.GreenHistogram;
import model.histogram.IntensityHistogram;
import model.histogram.RedHistogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for ImageModel.
 */
public class ImageModelTest {
  IModel model2;

  /**
   * Initialize model.
   */
  @Before
  public void init() {
    model2 = new ImageModel();
    ArrayList<String> params = new ArrayList<>();
    params.add("res/2x2color.ppm");
    params.add("sample");
    model2.apply(new Load(params));
  }

  /**
   * Test the load function.
   */
  @Test
  public void load() {
    IModel model1 = new ImageModel();
    assertThrows(NullPointerException.class, () -> model1.getFromMap("sample"));
    ArrayList<String> params = new ArrayList<>();
    params.add("res/2x2color.ppm");
    params.add("sample");
    model1.apply(new Load(params));

    int[][] expected = new int[3][6];
    expected[0][0] = 2;
    expected[0][1] = 2;
    expected[0][2] = 255;
    expected[1][0] = 0;
    expected[1][1] = 150;
    expected[1][2] = 150;
    expected[1][3] = 255;
    expected[1][4] = 0;
    expected[1][5] = 255;
    expected[2][0] = 137;
    expected[2][1] = 200;
    expected[2][2] = 86;
    expected[2][3] = 255;
    expected[2][4] = 230;
    expected[2][5] = 20;
    int[][] result = model1.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expected[i][j], result[i][j]);
      }
    }
  }

  /**
   * Test the save function.
   */
  @Test
  public void save() {
    ArrayList<String> params = new ArrayList<>();
    params.add("res/no.ppm");
    params.add("no");
    assertThrows(IllegalArgumentException.class, () -> model2.apply(new Load(params)));

    ArrayList<String> params2 = new ArrayList<>();
    params2.add("res/no.ppm");
    params2.add("sample");
    model2.apply(new Save(params2));

    model2.apply(new Load(params));
    int[][] result = model2.getFromMap("no");
    int[][] expected = new int[3][6];
    expected[0][0] = 2;
    expected[0][1] = 2;
    expected[0][2] = 255;
    expected[1][0] = 0;
    expected[1][1] = 150;
    expected[1][2] = 150;
    expected[1][3] = 255;
    expected[1][4] = 0;
    expected[1][5] = 255;
    expected[2][0] = 137;
    expected[2][1] = 200;
    expected[2][2] = 86;
    expected[2][3] = 255;
    expected[2][4] = 230;
    expected[2][5] = 20;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expected[i][j], result[i][j]);
      }
    }

  }

  /**
   * Tests normal vertical flip operation.
   */
  @Test
  public void verticalFlip() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleVerticalFlip");

    model2.apply(new VerticalFlip(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 137;
    expectedAfter[1][1] = 200;
    expectedAfter[1][2] = 86;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 230;
    expectedAfter[1][5] = 20;
    expectedAfter[2][0] = 0;
    expectedAfter[2][1] = 150;
    expectedAfter[2][2] = 150;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 0;
    expectedAfter[2][5] = 255;
    int[][] resultAfter = model2.getFromMap("sampleVerticalFlip");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test normal horizontal flip operation.
   */
  @Test
  public void horizontalFlip() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleHorizontalFlip");
    model2.apply(new HorizontalFlip(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 255;
    expectedAfter[1][1] = 0;
    expectedAfter[1][2] = 255;
    expectedAfter[1][3] = 0;
    expectedAfter[1][4] = 150;
    expectedAfter[1][5] = 150;
    expectedAfter[2][0] = 255;
    expectedAfter[2][1] = 230;
    expectedAfter[2][2] = 20;
    expectedAfter[2][3] = 137;
    expectedAfter[2][4] = 200;
    expectedAfter[2][5] = 86;
    int[][] resultAfter = model2.getFromMap("sampleHorizontalFlip");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test brighten method with positive brighten.
   */
  @Test
  public void brighten() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleBrighten");
    model2.apply(new Brighten(30, params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 30;
    expectedAfter[1][1] = 180;
    expectedAfter[1][2] = 180;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 30;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 167;
    expectedAfter[2][1] = 230;
    expectedAfter[2][2] = 116;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 255;
    expectedAfter[2][5] = 50;
    int[][] resultAfter = model2.getFromMap("sampleBrighten");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test brighten method with negative brighten.
   */
  @Test
  public void darken() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleDarken");
    model2.apply(new Brighten(-30, params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 0;
    expectedAfter[1][1] = 120;
    expectedAfter[1][2] = 120;
    expectedAfter[1][3] = 225;
    expectedAfter[1][4] = 0;
    expectedAfter[1][5] = 225;
    expectedAfter[2][0] = 107;
    expectedAfter[2][1] = 170;
    expectedAfter[2][2] = 56;
    expectedAfter[2][3] = 225;
    expectedAfter[2][4] = 200;
    expectedAfter[2][5] = 0;
    int[][] resultAfter = model2.getFromMap("sampleDarken");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test brighten method with zero brighten.
   */
  @Test
  public void brightenNothing() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleBrightenNoChange");
    model2.apply(new Brighten(0, params));

    int[][] resultAfter = model2.getFromMap("sampleBrightenNoChange");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use red component to greyscale image.
   */
  @Test
  public void greyScaleRed() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleRed");
    model2.apply(new GreyScaleRed(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 0;
    expectedAfter[1][1] = 0;
    expectedAfter[1][2] = 0;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 255;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 137;
    expectedAfter[2][1] = 137;
    expectedAfter[2][2] = 137;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 255;
    expectedAfter[2][5] = 255;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleRed");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use green component to greyscale image.
   */
  @Test
  public void greyScaleGreen() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleGreen");
    model2.apply(new GreyScaleGreen(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 150;
    expectedAfter[1][1] = 150;
    expectedAfter[1][2] = 150;
    expectedAfter[1][3] = 0;
    expectedAfter[1][4] = 0;
    expectedAfter[1][5] = 0;
    expectedAfter[2][0] = 200;
    expectedAfter[2][1] = 200;
    expectedAfter[2][2] = 200;
    expectedAfter[2][3] = 230;
    expectedAfter[2][4] = 230;
    expectedAfter[2][5] = 230;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleGreen");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use blue component to greyscale image.
   */
  @Test
  public void greyScaleBlue() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleBlue");
    model2.apply(new GreyScaleBlue(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 150;
    expectedAfter[1][1] = 150;
    expectedAfter[1][2] = 150;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 255;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 86;
    expectedAfter[2][1] = 86;
    expectedAfter[2][2] = 86;
    expectedAfter[2][3] = 20;
    expectedAfter[2][4] = 20;
    expectedAfter[2][5] = 20;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleBlue");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use value component to greyscale image.
   */
  @Test
  public void greyScaleValue() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleValue");
    model2.apply(new GreyScaleValue(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 150;
    expectedAfter[1][1] = 150;
    expectedAfter[1][2] = 150;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 255;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 200;
    expectedAfter[2][1] = 200;
    expectedAfter[2][2] = 200;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 255;
    expectedAfter[2][5] = 255;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleValue");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use intensity component to greyscale image.
   */
  @Test
  public void greyScaleIntensity() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleIntensity");
    model2.apply(new GreyScaleIntensity(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 100;
    expectedAfter[1][1] = 100;
    expectedAfter[1][2] = 100;
    expectedAfter[1][3] = 170;
    expectedAfter[1][4] = 170;
    expectedAfter[1][5] = 170;
    expectedAfter[2][0] = 141;
    expectedAfter[2][1] = 141;
    expectedAfter[2][2] = 141;
    expectedAfter[2][3] = 168;
    expectedAfter[2][4] = 168;
    expectedAfter[2][5] = 168;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleIntensity");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Testing Use luma component to greyscale image.
   */
  @Test
  public void greyScaleLuma() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScaleLuma");
    model2.apply(new GreyScaleLuma(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 118;
    expectedAfter[1][1] = 118;
    expectedAfter[1][2] = 118;
    expectedAfter[1][3] = 72;
    expectedAfter[1][4] = 72;
    expectedAfter[1][5] = 72;
    expectedAfter[2][0] = 178;
    expectedAfter[2][1] = 178;
    expectedAfter[2][2] = 178;
    expectedAfter[2][3] = 220;
    expectedAfter[2][4] = 220;
    expectedAfter[2][5] = 220;
    int[][] resultAfter = model2.getFromMap("sampleGreyScaleLuma");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test the operation of the apply function.
   */
  @Test
  public void apply() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleApply");
    model2.apply(new Brighten(30, params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 30;
    expectedAfter[1][1] = 180;
    expectedAfter[1][2] = 180;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 30;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 167;
    expectedAfter[2][1] = 230;
    expectedAfter[2][2] = 116;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 255;
    expectedAfter[2][5] = 50;
    int[][] resultAfter = model2.getFromMap("sampleApply");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }

  }

  /**
   * Test operations for getFromMap function.
   */
  @Test
  public void getFromMap() {
    int[][] result = model2.getFromMap("sample");
    int[][] expected = new int[3][6];
    expected[0][0] = 2;
    expected[0][1] = 2;
    expected[0][2] = 255;
    expected[1][0] = 0;
    expected[1][1] = 150;
    expected[1][2] = 150;
    expected[1][3] = 255;
    expected[1][4] = 0;
    expected[1][5] = 255;
    expected[2][0] = 137;
    expected[2][1] = 200;
    expected[2][2] = 86;
    expected[2][3] = 255;
    expected[2][4] = 230;
    expected[2][5] = 20;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expected[i][j], result[i][j]);
      }
    }
  }

  /**
   * Test operations for addToMap function.
   */
  @Test
  public void addToMap() {
    int[][] add = new int[2][3];
    add[0][0] = 1;
    add[0][1] = 1;
    add[0][2] = 50;
    add[1][0] = 10;
    add[1][1] = 50;
    add[1][2] = 30;
    model2.addToMap("test", add);

    int[][] result = model2.getFromMap("test");
    int[][] expected = new int[2][3];
    expected[0][0] = 1;
    expected[0][1] = 1;
    expected[0][2] = 50;
    expected[1][0] = 10;
    expected[1][1] = 50;
    expected[1][2] = 30;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], result[i][j]);
      }
    }
  }

  /**
   * Test multiple operations.
   */
  @Test
  public void multipleMoveTest() {
    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("vertical");
    model2.apply(new VerticalFlip(params));
    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 137;
    expectedAfter[1][1] = 200;
    expectedAfter[1][2] = 86;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 230;
    expectedAfter[1][5] = 20;
    expectedAfter[2][0] = 0;
    expectedAfter[2][1] = 150;
    expectedAfter[2][2] = 150;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 0;
    expectedAfter[2][5] = 255;
    int[][] resultAfter = model2.getFromMap("vertical");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }

    ArrayList<String> params2 = new ArrayList<>();
    params2.add("vertical");
    params2.add("horizontal");
    model2.apply(new HorizontalFlip(params2));

    int[][] expectedAfter2 = new int[3][6];
    expectedAfter2[0][0] = 2;
    expectedAfter2[0][1] = 2;
    expectedAfter2[0][2] = 255;
    expectedAfter2[1][0] = 255;
    expectedAfter2[1][1] = 230;
    expectedAfter2[1][2] = 20;
    expectedAfter2[1][3] = 137;
    expectedAfter2[1][4] = 200;
    expectedAfter2[1][5] = 86;
    expectedAfter2[2][0] = 255;
    expectedAfter2[2][1] = 0;
    expectedAfter2[2][2] = 255;
    expectedAfter2[2][3] = 0;
    expectedAfter2[2][4] = 150;
    expectedAfter2[2][5] = 150;
    int[][] resultAfter2 = model2.getFromMap("horizontal");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter2[i][j], resultAfter2[i][j]);
      }
    }
  }

  /**
   * Test normal Blur Operations.
   */
  @Test
  public void Blur() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleBlur");
    model2.apply(new Blur(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 64;
    expectedAfter[1][1] = 76;
    expectedAfter[1][2] = 81;
    expectedAfter[1][3] = 112;
    expectedAfter[1][4] = 50;
    expectedAfter[1][5] = 81;
    expectedAfter[2][0] = 81;
    expectedAfter[2][1] = 91;
    expectedAfter[2][2] = 39;
    expectedAfter[2][3] = 91;
    expectedAfter[2][4] = 79;
    expectedAfter[2][5] = 25;
    int[][] resultAfter = model2.getFromMap("sampleBlur");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test normal sharpening operation.
   */
  @Test
  public void Sharpening() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleSharpen");
    model2.apply(new Sharpening(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 161;
    expectedAfter[1][1] = 255;
    expectedAfter[1][2] = 240;
    expectedAfter[1][3] = 255;
    expectedAfter[1][4] = 171;
    expectedAfter[1][5] = 255;
    expectedAfter[2][0] = 255;
    expectedAfter[2][1] = 255;
    expectedAfter[2][2] = 214;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 255;
    expectedAfter[2][5] = 197;
    int[][] resultAfter = model2.getFromMap("sampleSharpen");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test GreyScale operation.
   */
  @Test
  public void GreyScale() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleGreyScale");
    model2.apply(new GreyScale(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 118;
    expectedAfter[1][1] = 118;
    expectedAfter[1][2] = 118;
    expectedAfter[1][3] = 72;
    expectedAfter[1][4] = 72;
    expectedAfter[1][5] = 72;
    expectedAfter[2][0] = 178;
    expectedAfter[2][1] = 178;
    expectedAfter[2][2] = 178;
    expectedAfter[2][3] = 220;
    expectedAfter[2][4] = 220;
    expectedAfter[2][5] = 220;
    int[][] resultAfter = model2.getFromMap("sampleGreyScale");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test Sepia tone operation.
   */
  @Test
  public void SepiaTone() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("sampleSepiaTone");
    model2.apply(new SepiaTone(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 143;
    expectedAfter[1][1] = 128;
    expectedAfter[1][2] = 99;
    expectedAfter[1][3] = 148;
    expectedAfter[1][4] = 131;
    expectedAfter[1][5] = 102;
    expectedAfter[2][0] = 223;
    expectedAfter[2][1] = 199;
    expectedAfter[2][2] = 155;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 250;
    expectedAfter[2][5] = 194;
    int[][] resultAfter = model2.getFromMap("sampleSepiaTone");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  /**
   * Test usage of Red Histogram function object.
   */
  @Test
  public void RedHistogram() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;

    IModel model = new ImageModel();
    model.addToMap("test", expectedBefore);

    HashMap<Integer, Integer> result = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      result.put(i, 0);
    }
    result.put(0, 1);
    result.put(255, 2);
    result.put(137, 1);

    for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
      assertEquals(entry.getValue(),
              model.apply(new RedHistogram("test")).get(entry.getKey()));
    }
  }

  /**
   * Test usage of Green Histogram function object.
   */
  @Test
  public void GreenHistogram() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;

    IModel model = new ImageModel();
    model.addToMap("test", expectedBefore);

    HashMap<Integer, Integer> result = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      result.put(i, 0);
    }
    result.put(0, 1);
    result.put(150, 1);
    result.put(200, 1);
    result.put(230, 1);

    for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
      assertEquals(entry.getValue(),
              model.apply(new GreenHistogram("test")).get(entry.getKey()));
    }
  }

  /**
   * Test usage of Blue Histogram function object.
   */
  @Test
  public void BlueHistogram() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;

    IModel model = new ImageModel();
    model.addToMap("test", expectedBefore);

    HashMap<Integer, Integer> result = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      result.put(i, 0);
    }
    result.put(150, 1);
    result.put(255, 1);
    result.put(86, 1);
    result.put(20, 1);

    for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
      assertEquals(entry.getValue(),
              model.apply(new BlueHistogram("test")).get(entry.getKey()));
    }
  }

  /**
   * Test usage of Intensity Histogram function object.
   */
  @Test
  public void IntensityHistogram() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;

    IModel model = new ImageModel();
    model.addToMap("test", expectedBefore);

    HashMap<Integer, Integer> result = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      result.put(i, 0);
    }
    result.put(100, 1);
    result.put(170, 1);
    result.put(141, 1);
    result.put(168, 1);

    for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
      assertEquals(entry.getValue(),
              model.apply(new IntensityHistogram("test")).get(entry.getKey()));
    }
  }

  @Test
  public void DownsizeTest() {
    int[][] expectedBefore = new int[3][6];
    expectedBefore[0][0] = 2;
    expectedBefore[0][1] = 2;
    expectedBefore[0][2] = 255;
    expectedBefore[1][0] = 0;
    expectedBefore[1][1] = 150;
    expectedBefore[1][2] = 150;
    expectedBefore[1][3] = 255;
    expectedBefore[1][4] = 0;
    expectedBefore[1][5] = 255;
    expectedBefore[2][0] = 137;
    expectedBefore[2][1] = 200;
    expectedBefore[2][2] = 86;
    expectedBefore[2][3] = 255;
    expectedBefore[2][4] = 230;
    expectedBefore[2][5] = 20;
    int[][] resultBefore = model2.getFromMap("sample");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBefore[i][j], resultBefore[i][j]);
      }
    }

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("downsized");
    model2.apply(new Downsize( 1, 1,params));

    int[][] expectedAfter = new int[2][3];
    expectedAfter[0][0] = 1;
    expectedAfter[0][1] = 1;
    expectedAfter[0][2] = 0;
    expectedAfter[1][0] = 0;
    expectedAfter[1][1] = 100;
    expectedAfter[1][2] = 214;
    int[][] resultAfter = model2.getFromMap("downsized");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3
              ; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }
  }

  @Test
  public void maskTest() {
    int[][] mask = new int[3][6];
    mask[0][0] = 2;
    mask[0][1] = 2;
    mask[0][2] = 255;
    mask[1][0] = 0;
    mask[1][1] = 0;
    mask[1][2] = 0;
    mask[1][3] = 0;
    mask[1][4] = 0;
    mask[1][5] = 0;
    mask[2][0] = 255;
    mask[2][1] = 255;
    mask[2][2] = 255;
    mask[2][3] = 255;
    mask[2][4] = 255;
    mask[2][5] = 255;
    model2.addToMap("mask", mask);

    ArrayList<String> params = new ArrayList<>();
    params.add("sample");
    params.add("mask");
    params.add("sampleSepiaTone");
    model2.apply(new SepiaTone(params));

    int[][] expectedAfter = new int[3][6];
    expectedAfter[0][0] = 2;
    expectedAfter[0][1] = 2;
    expectedAfter[0][2] = 255;
    expectedAfter[1][0] = 143;
    expectedAfter[1][1] = 128;
    expectedAfter[1][2] = 99;
    expectedAfter[1][3] = 148;
    expectedAfter[1][4] = 131;
    expectedAfter[1][5] = 102;
    expectedAfter[2][0] = 137;
    expectedAfter[2][1] = 200;
    expectedAfter[2][2] = 86;
    expectedAfter[2][3] = 255;
    expectedAfter[2][4] = 230;
    expectedAfter[2][5] = 20;
    int[][] resultAfter = model2.getFromMap("sampleSepiaTone");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter[i][j], resultAfter[i][j]);
      }
    }

    ArrayList<String> params2 = new ArrayList<>();
    params2.add("sample");
    params2.add("mask");
    params2.add("sampleBlur");
    model2.apply(new Blur(params2));

    int[][] expectedAfter2 = new int[3][6];
    expectedAfter2[0][0] = 2;
    expectedAfter2[0][1] = 2;
    expectedAfter2[0][2] = 255;
    expectedAfter2[1][0] = 64;
    expectedAfter2[1][1] = 76;
    expectedAfter2[1][2] = 81;
    expectedAfter2[1][3] = 112;
    expectedAfter2[1][4] = 50;
    expectedAfter2[1][5] = 81;
    expectedAfter2[2][0] = 137;
    expectedAfter2[2][1] = 200;
    expectedAfter2[2][2] = 86;
    expectedAfter2[2][3] = 255;
    expectedAfter2[2][4] = 230;
    expectedAfter2[2][5] = 20;
    int[][] resultAfter2 = model2.getFromMap("sampleBlur");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter2[i][j], resultAfter2[i][j]);
      }
    }

    ArrayList<String> params3 = new ArrayList<>();
    params3.add("sample");
    params3.add("mask");
    params3.add("sampleSharpen");
    model2.apply(new Sharpening(params3));

    int[][] expectedAfter3 = new int[3][6];
    expectedAfter3[0][0] = 2;
    expectedAfter3[0][1] = 2;
    expectedAfter3[0][2] = 255;
    expectedAfter3[1][0] = 161;
    expectedAfter3[1][1] = 255;
    expectedAfter3[1][2] = 240;
    expectedAfter3[1][3] = 255;
    expectedAfter3[1][4] = 171;
    expectedAfter3[1][5] = 255;
    expectedAfter3[2][0] = 137;
    expectedAfter3[2][1] = 200;
    expectedAfter3[2][2] = 86;
    expectedAfter3[2][3] = 255;
    expectedAfter3[2][4] = 230;
    expectedAfter3[2][5] = 20;
    int[][] resultAfter3 = model2.getFromMap("sampleSharpen");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter3[i][j], resultAfter3[i][j]);
      }
    }

    ArrayList<String> params4 = new ArrayList<>();
    params4.add("sample");
    params4.add("mask");
    params4.add("sampleG");
    model2.apply(new GreyScale(params4));

    int[][] expectedAfter4 = new int[3][6];
    expectedAfter4[0][0] = 2;
    expectedAfter4[0][1] = 2;
    expectedAfter4[0][2] = 255;
    expectedAfter4[1][0] = 118;
    expectedAfter4[1][1] = 118;
    expectedAfter4[1][2] = 118;
    expectedAfter4[1][3] = 72;
    expectedAfter4[1][4] = 72;
    expectedAfter4[1][5] = 72;
    expectedAfter4[2][0] = 137;
    expectedAfter4[2][1] = 200;
    expectedAfter4[2][2] = 86;
    expectedAfter4[2][3] = 255;
    expectedAfter4[2][4] = 230;
    expectedAfter4[2][5] = 20;
    int[][] resultAfter4 = model2.getFromMap("sampleG");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter4[i][j], resultAfter4[i][j]);
      }
    }

    ArrayList<String> params5 = new ArrayList<>();
    params5.add("sample");
    params5.add("mask");
    params5.add("sampleB");
    model2.apply(new Brighten(30, params5));

    int[][] expectedAfter5 = new int[3][6];
    expectedAfter5[0][0] = 2;
    expectedAfter5[0][1] = 2;
    expectedAfter5[0][2] = 255;
    expectedAfter5[1][0] = 30;
    expectedAfter5[1][1] = 180;
    expectedAfter5[1][2] = 180;
    expectedAfter5[1][3] = 255;
    expectedAfter5[1][4] = 30;
    expectedAfter5[1][5] = 255;
    expectedAfter5[2][0] = 137;
    expectedAfter5[2][1] = 200;
    expectedAfter5[2][2] = 86;
    expectedAfter5[2][3] = 255;
    expectedAfter5[2][4] = 230;
    expectedAfter5[2][5] = 20;
    int[][] resultAfter5 = model2.getFromMap("sampleB");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter5[i][j], resultAfter5[i][j]);
      }
    }

    ArrayList<String> params6 = new ArrayList<>();
    params6.add("sample");
    params6.add("mask");
    params6.add("sampleD");
    model2.apply(new Brighten(-30, params6));

    int[][] expectedAfter6 = new int[3][6];
    expectedAfter6[0][0] = 2;
    expectedAfter6[0][1] = 2;
    expectedAfter6[0][2] = 255;
    expectedAfter6[1][0] = 0;
    expectedAfter6[1][1] = 120;
    expectedAfter6[1][2] = 120;
    expectedAfter6[1][3] = 225;
    expectedAfter6[1][4] = 0;
    expectedAfter6[1][5] = 225;
    expectedAfter6[2][0] = 137;
    expectedAfter6[2][1] = 200;
    expectedAfter6[2][2] = 86;
    expectedAfter6[2][3] = 255;
    expectedAfter6[2][4] = 230;
    expectedAfter6[2][5] = 20;
    int[][] resultAfter6 = model2.getFromMap("sampleD");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedAfter6[i][j], resultAfter6[i][j]);
      }
    }
  }
}