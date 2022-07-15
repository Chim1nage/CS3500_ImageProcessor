import org.junit.Before;
import org.junit.Test;

import model.IProcessingModel;
import model.util.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests methods and Initialization of Pixel.
 */
public final class PixelTest {

  private Pixel pixel;

  @Before
  public void init() {
    this.pixel = new Pixel(10, 20, 30);
  }

  @Test
  public void testFilters() {
    assertEquals(new Pixel(10, 10, 10), this.pixel.greyscale(IProcessingModel.GreyscaleType.Red));
    assertEquals(new Pixel(20, 20, 20), this.pixel.greyscale(IProcessingModel.GreyscaleType.Green));
    assertEquals(new Pixel(30, 30, 30), this.pixel.greyscale(IProcessingModel.GreyscaleType.Blue));
    assertEquals(new Pixel(30, 30, 30), this.pixel.greyscale(IProcessingModel.GreyscaleType.Value));
    assertEquals(new Pixel(20, 20, 20),
            this.pixel.greyscale(IProcessingModel.GreyscaleType.Intensity));
    assertEquals(new Pixel(18, 18, 18), this.pixel.greyscale(IProcessingModel.GreyscaleType.Luma));
  }

  @Test
  public void testInvalidPixel() {
    try {
      this.pixel = new Pixel(256, 123, 234);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB value Invalid.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(123, 345, 234);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB value Invalid.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(53, 123, 500);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB value Invalid.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(-1, 3, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be positive.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(53, -5, 34);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be positive.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(255, 123, -10);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be positive.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(0, 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be positive.", e.getMessage());
    }
    try {
      this.pixel = new Pixel(256, 256, 256);
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be positive.", e.getMessage());
    }

  }

  @Test
  public void testGetterMethods() {
    assertEquals(10, this.pixel.getRed());
    assertEquals(20, this.pixel.getGreen());
    assertEquals(30, this.pixel.getBlue());
    assertEquals(255, this.pixel.getAlpha());
  }

  @Test
  public void testPixelMethods() {
    int[] values = new int[3];
    values[0] = 10;
    values[1] = 20;
    values[2] = 30;

    int[] valuesCopy = new int[3];
    valuesCopy[0] = this.pixel.getRed();
    valuesCopy[1] = this.pixel.getGreen();
    valuesCopy[2] = this.pixel.getBlue();

    for (int i = 0; i < 3; i++) {
      assertEquals(values[i], valuesCopy[i]);
    }

  }

}
