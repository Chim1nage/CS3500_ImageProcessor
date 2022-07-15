import org.junit.Before;
import org.junit.Test;

import model.util.GeneralImage;
import model.util.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test PPMImage methods and initialization.
 */
public final class GeneralImageTest {
  private GeneralImage image1;
  private Pixel[][] pixels;

  @Before
  public void init() {
    this.pixels = new Pixel[2][3];
    this.pixels[0][0] = new Pixel(255, 150, 205);
    this.pixels[0][1] = new Pixel(15, 100, 200);
    this.pixels[0][2] = new Pixel(253, 255, 200);

    this.pixels[1][0] = new Pixel(56, 34, 66);
    this.pixels[1][1] = new Pixel(190, 10, 34);
    this.pixels[1][2] = new Pixel(155, 33, 34);

    this.image1 = new GeneralImage(this.pixels, 3, 2, 255);
  }

  @Test
  public void testHashCodeAndEquals() {
    assertEquals(274911, this.image1.hashCode());
    assertFalse(this.image1.equals(10));
    assertTrue(this.image1.equals(this.image1));
  }

  @Test
  public void testInvalidImages() {
    // invalid height
    try {
      this.image1 = new GeneralImage(this.pixels, 3, 3, 255);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid width and height for supplied pixels.", e.getMessage());
    }
    // invalid width
    try {
      this.image1 = new GeneralImage(this.pixels, 2, 2, 255);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid width and height for supplied pixels.", e.getMessage());
    }

    // Pixels have max value over supplied
    try {
      this.image1 = new GeneralImage(this.pixels, 2, 3, 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid width and height for supplied pixels.", e.getMessage());
    }

    // null pixels array
    try {
      this.image1 = new GeneralImage(null, 2, 3, 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Pixels cannot be null", e.getMessage());
    }

    // negative width
    try {
      this.image1 = new GeneralImage(this.pixels, -1, 3, 255);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image characteristics cannot be negative", e.getMessage());
    }

    // negative height
    try {
      this.image1 = new GeneralImage(this.pixels, 2, -1, 255);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image characteristics cannot be negative", e.getMessage());
    }

    // negative maxValue
    try {
      this.image1 = new GeneralImage(this.pixels, 2, 3, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Image characteristics cannot be negative", e.getMessage());
    }

    Pixel[][] pixels = new Pixel[1][1];
    pixels[0][0] = new Pixel(255, 256, 0);

    try {
      this.image1 = new GeneralImage(pixels, 1, 1, 255);
      fail("Exception not caught.");
    } catch (IllegalArgumentException e) {
      assertEquals("Pixels can't be over max value", e.getMessage());
    }

    Pixel[][] pixelsv2 = new Pixel[1][1];
    pixelsv2[0][0] = new Pixel(255, 255, 256);
  }

  @Test
  public void testToString() {
    assertEquals(""
            + "P3 \n" +
            "# Created by Daniel Szyc + Evan Keister\n" +
            "3 2\n" +
            "255\n" +
            "\n" +
            "255\n" +
            "150\n" +
            "205\n" +
            "\n" +
            "15\n" +
            "100\n" +
            "200\n" +
            "\n" +
            "253\n" +
            "255\n" +
            "200\n" +
            "\n" +
            "56\n" +
            "34\n" +
            "66\n" +
            "\n" +
            "190\n" +
            "10\n" +
            "34\n" +
            "\n" +
            "155\n" +
            "33\n" +
            "34\n", this.image1.toString());
  }

  @Test
  public void testGetterMethods() {
    assertEquals(3, this.image1.getWidth());
    assertEquals(2, this.image1.getHeight());
    assertEquals(255, this.image1.getMaxValue());

    Pixel[][] pixels = new Pixel[2][3];
    pixels[0][0] = new Pixel(255, 150, 205);
    pixels[0][1] = new Pixel(15, 100, 200);
    pixels[0][2] = new Pixel(253, 255, 200);

    pixels[1][0] = new Pixel(56, 34, 66);
    pixels[1][1] = new Pixel(190, 10, 34);
    pixels[1][2] = new Pixel(155, 33, 34);


    assertEquals(pixels, this.image1.getPixels());

    // make sure that you cannot change pixels in image after using this method
    Pixel[][] tryToChange = this.image1.getPixels();
    for (Pixel[] row : tryToChange) {
      for (Pixel pixel : row) {
        pixel = new Pixel(10, 10, 10);
      }
    }

    assertEquals(pixels, this.image1.getPixels());
  }

}





















