package utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for the ImageUtil class.
 */
public class ImageUtilTest {

  /**
   * Tests all operations for the readPPM properties using readImage.
   */
  @Test
  public void readPPMTest() {
    // File not found
    assertThrows(IllegalArgumentException.class, () -> ImageUtil.readImage("abcd"));
    // File does not start with P3
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("res/util/P62x2color.ppm"));
    // File has width < 0;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("res/util/WidthSmallerThan0.ppm"));
    // File has height < 0;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("res/util/HeightSmallerThan0.ppm"));
    // File has maxValue < 0;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("res/util/maxSmallerThan0.ppm"));
    // File has maxValue > 255;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("res/util/maxLargerThan255.ppm"));
    // File is correctly loaded
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
    int[][] result = ImageUtil.readImage("res/2x2color.ppm");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expected[i][j], result[i][j]);
      }
    }
  }

  /**
   * Tests all operations for the readNormal properties using readImage.
   */
  @Test
  public void readNormalTest() {
    // Test not reading a file
    assertThrows(IllegalArgumentException.class, () -> ImageUtil.readImage("asdf"));

    // Test reading an unknown format
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.readImage("src/2x2color.ppm"));

    // Test reading normal file
    int[][] expected = new int[2][3];
    expected[0][0] = 1;
    expected[0][1] = 1;
    expected[1][0] = 100;
    expected[1][1] = 20;
    expected[1][2] = 81;
    int[][] resJPG = ImageUtil.readImage("res/util/1JPG.jpg");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], resJPG[i][j]);
      }
    }

    int[][] resJPEG = ImageUtil.readImage("res/util/1JPEG.jpeg");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], resJPEG[i][j]);
      }
    }

    int[][] resPNG = ImageUtil.readImage("res/util/1PNG.png");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], resPNG[i][j]);
      }
    }

    int[][] resBMP = ImageUtil.readImage("res/util/1BMP.bmp");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], resBMP[i][j]);
      }
    }
  }

  /**
   * Tests all operations for the savePPM properties using saveImage.
   */
  @Test
  public void savePPM() {
    // Width < 0
    int[][] temp = new int[3][6];
    temp[0][0] = -2;
    temp[0][1] = 2;
    temp[0][2] = 255;
    temp[1][0] = 0;
    temp[1][1] = 150;
    temp[1][2] = 150;
    temp[1][3] = 255;
    temp[1][4] = 0;
    temp[1][5] = 255;
    temp[2][0] = 137;
    temp[2][1] = 200;
    temp[2][2] = 86;
    temp[2][3] = 255;
    temp[2][4] = 230;
    temp[2][5] = 20;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.saveImage("res/util/output.ppm", temp));

    // height < 0
    int[][] temp2 = new int[3][6];
    temp[0][0] = 2;
    temp[0][1] = -2;
    temp[0][2] = 255;
    temp[1][0] = 0;
    temp[1][1] = 150;
    temp[1][2] = 150;
    temp[1][3] = 255;
    temp[1][4] = 0;
    temp[1][5] = 255;
    temp[2][0] = 137;
    temp[2][1] = 200;
    temp[2][2] = 86;
    temp[2][3] = 255;
    temp[2][4] = 230;
    temp[2][5] = 20;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.saveImage("res/util/output.ppm", temp2));

    // maxValue < 0
    int[][] temp3 = new int[3][6];
    temp[0][0] = 2;
    temp[0][1] = 2;
    temp[0][2] = -1;
    temp[1][0] = 0;
    temp[1][1] = 150;
    temp[1][2] = 150;
    temp[1][3] = 255;
    temp[1][4] = 0;
    temp[1][5] = 255;
    temp[2][0] = 137;
    temp[2][1] = 200;
    temp[2][2] = 86;
    temp[2][3] = 255;
    temp[2][4] = 230;
    temp[2][5] = 20;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.saveImage("res/util/output.ppm", temp3));

    // maxValue > 255
    int[][] temp4 = new int[3][6];
    temp[0][0] = 2;
    temp[0][1] = 2;
    temp[0][2] = 256;
    temp[1][0] = 0;
    temp[1][1] = 150;
    temp[1][2] = 150;
    temp[1][3] = 255;
    temp[1][4] = 0;
    temp[1][5] = 255;
    temp[2][0] = 137;
    temp[2][1] = 200;
    temp[2][2] = 86;
    temp[2][3] = 255;
    temp[2][4] = 230;
    temp[2][5] = 20;
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.saveImage("res/util/output.ppm", temp4));

    // Normal output
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

    int[][] result = new int[3][6];
    result[0][0] = 2;
    result[0][1] = 2;
    result[0][2] = 255;
    result[1][0] = 0;
    result[1][1] = 150;
    result[1][2] = 150;
    result[1][3] = 255;
    result[1][4] = 0;
    result[1][5] = 255;
    result[2][0] = 137;
    result[2][1] = 200;
    result[2][2] = 86;
    result[2][3] = 255;
    result[2][4] = 230;
    result[2][5] = 20;

    ImageUtil.saveImage("res/util/testOutput.ppm", result);
    int[][] output = ImageUtil.readImage("res/util/testOutput.ppm");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expected[i][j], output[i][j]);
      }
    }
  }

  /**
   * Tests all operations for the saveNormal properties using saveImage.
   */
  @Test
  public void saveNormal() {
    int[][] out = new int[2][3];
    out[0][0] = 1;
    out[0][1] = 1;
    out[1][0] = 100;
    out[1][1] = 20;
    out[1][2] = 81;

    int[][] invalid = new int[2][3];
    invalid[0][0] = 1;
    invalid[0][1] = 1;
    invalid[1][0] = -100;
    invalid[1][1] = 20;
    invalid[1][2] = 81;

    // Saving invalid images
    assertThrows(IllegalArgumentException.class, () ->
            ImageUtil.saveImage("res/util/2jpg.jpg", invalid));

    // Saving normal images
    ImageUtil.saveImage("res/util/2png.png", out);
    int[][] resPNG = ImageUtil.readImage("res/util/2png.png");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(out[i][j], resPNG[i][j]);
      }
    }

    ImageUtil.saveImage("res/util/2bmp.bmp", out);
    int[][] resBMP = ImageUtil.readImage("res/util/2bmp.bmp");
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(out[i][j], resBMP[i][j]);
      }
    }

  }

  /**
   * Test saving JPG.
   */
  @Test
  public void saveJPG() {
    int[][] expected = new int[2][3];
    expected[0][0] = 1;
    expected[0][1] = 1;
    expected[1][0] = 100;
    expected[1][1] = 20;
    expected[1][2] = 81;
    ImageUtil.saveImage("res/util/1JPG.jpg", expected);

    int[][] res = ImageUtil.readImage("res/util/1JPG.jpg");

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], res[i][j]);
      }
    }
  }

  /**
   * Test saving JPEG.
   */
  @Test
  public void saveJPEG() {
    int[][] expected = new int[2][3];
    expected[0][0] = 1;
    expected[0][1] = 1;
    expected[1][0] = 100;
    expected[1][1] = 20;
    expected[1][2] = 81;
    ImageUtil.saveImage("res/util/1JPEG.jpeg", expected);

    int[][] res = ImageUtil.readImage("res/util/1JPEG.jpeg");

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expected[i][j], res[i][j]);
      }
    }
  }

  /**
   * Test input as ppm and output as png.
   */
  @Test
  public void ppmToPng() {
    int[][] res = ImageUtil.readImage("res/2x2color.ppm");
    ImageUtil.saveImage("res/util/2x2colorPPMtoPNG.png", res);
    int[][] output = ImageUtil.readImage("res/util/2x2colorPPMtoPNG.png");
    res[0][2] = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(res[i][j], output[i][j]);
      }
    }
  }

  /**
   * Test input as png and output as ppm.
   */
  @Test
  public void pngToPpm() {
    int[][] res = ImageUtil.readImage("res/util/2x2colorPPMtoPNG.png");
    ImageUtil.saveImage("res/util/2x2colorPNGtoPPM.ppm", res);
    int[][] output = ImageUtil.readImage("res/util/2x2colorPNGtoPPM.ppm");
    res[0][2] = 255;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(res[i][j], output[i][j]);
      }
    }
  }

  /**
   * Test input as ppm and output as bmp.
   */
  @Test
  public void ppmToBMP() {
    int[][] res = ImageUtil.readImage("res/2x2color.ppm");
    ImageUtil.saveImage("res/util/2x2colorPPMtoBMP.bmp", res);
    int[][] output = ImageUtil.readImage("res/util/2x2colorPPMtoBMP.bmp");
    res[0][2] = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(res[i][j], output[i][j]);
      }
    }
  }

  /**
   * Test input as bmp and output as ppm.
   */
  @Test
  public void bmpToPpm() {
    int[][] res = ImageUtil.readImage("res/util/2x2colorPPMtoBMP.bmp");
    ImageUtil.saveImage("res/util/2x2colorBMPtoPPM.ppm", res);
    int[][] output = ImageUtil.readImage("res/util/2x2colorBMPtoPPM.ppm");
    res[0][2] = 255;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(res[i][j], output[i][j]);
      }
    }
  }

  /**
   * Test input as png and output as bmp.
   */
  @Test
  public void pngToBmp() {
    int[][] res = ImageUtil.readImage("res/util/2x2colorPPMtoPNG.png");
    ImageUtil.saveImage("res/util/2x2colorPNGtoBMP.bmp", res);
    int[][] output = ImageUtil.readImage("res/util/2x2colorPNGtoBMP.bmp");
    res[0][2] = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(res[i][j], output[i][j]);
      }
    }
  }

  /**
   * Override correctly.
   */
  @Test
  public void overrideTest() {
    int[][] result = new int[3][6];
    result[0][0] = 2;
    result[0][1] = 2;
    result[0][2] = 255;
    result[1][0] = 0;
    result[1][1] = 150;
    result[1][2] = 150;
    result[1][3] = 255;
    result[1][4] = 0;
    result[1][5] = 255;
    result[2][0] = 137;
    result[2][1] = 200;
    result[2][2] = 86;
    result[2][3] = 255;
    result[2][4] = 230;
    result[2][5] = 20;
    ImageUtil.saveImage("res/util/override.ppm", result);

    int[][] result2 = new int[3][6];
    result2[0][0] = 2;
    result2[0][1] = 2;
    result2[0][2] = 255;
    result2[1][0] = 0;
    result2[1][1] = 0;
    result2[1][2] = 0;
    result2[1][3] = 0;
    result2[1][4] = 0;
    result2[1][5] = 255;
    result2[2][0] = 137;
    result2[2][1] = 200;
    result2[2][2] = 86;
    result2[2][3] = 255;
    result2[2][4] = 230;
    result2[2][5] = 20;
    ImageUtil.saveImage("res/util/override.ppm", result2);

    int[][] output = ImageUtil.readImage("res/util/override.ppm");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(result2[i][j], output[i][j]);
      }
    }

  }
}