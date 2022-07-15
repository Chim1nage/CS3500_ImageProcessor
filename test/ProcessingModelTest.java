import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controller.ControllerImpl;
import controller.IController;
import model.FinalProcessingModel;
import model.IFinalProcessingModel;
import model.IProcessingModel;
import model.SimpleProcessingModel;
import model.util.GeneralImage;
import model.util.Pixel;
import view.IView;
import view.SimpleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the SimpleProcessingModel methods and initialization.
 */
public final class ProcessingModelTest {
  private IProcessingModel model;
  private IFinalProcessingModel finalModel;
  private Pixel[][] pixels;
  private Map<String, GeneralImage> images;
  private IView view;
  private IController controller;

  @Before
  public void init() {
    this.model = new SimpleProcessingModel();
    this.finalModel = new FinalProcessingModel();
    this.view = new SimpleView(this.finalModel, new StringBuilder());

    this.images = new HashMap<>();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(240, 120, 100);
    this.pixels[0][1] = new Pixel(255, 0, 50);
    this.pixels[1][0] = new Pixel(240, 120, 150);
    this.pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage photo = new GeneralImage(pixels, 2, 2, 255);
    this.images.put("image", photo);
  }

  @Test
  public void testSimpleProcessingModel() {
    this.controller = new ControllerImpl(this.finalModel, view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    Map<String, GeneralImage> images = new HashMap<>();
    assertEquals(images, this.finalModel.getLoadedImages());
    GeneralImage photo = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("image", photo);
    this.controller.startProcessor();
    assertEquals(images, this.finalModel.getLoadedImages());
  }


  @Test
  public void testFlipVertical() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(240, 120, 100);
    this.pixels[1][1] = new Pixel(255, 0, 50);
    this.pixels[0][0] = new Pixel(240, 120, 150);
    this.pixels[0][1] = new Pixel(250, 130, 140);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageVertical", photo2);
    this.controller.startProcessor();
    this.finalModel.flip(false, "image", "imageVertical");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testFlipHorizontal() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][1] = new Pixel(240, 120, 100);
    this.pixels[0][0] = new Pixel(255, 0, 50);
    this.pixels[1][1] = new Pixel(240, 120, 150);
    this.pixels[1][0] = new Pixel(250, 130, 140);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageHorizontal", photo2);
    this.controller.startProcessor();
    this.finalModel.flip(true, "image", "imageHorizontal");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testBrightenUp() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(250, 130, 110);
    this.pixels[0][1] = new Pixel(255, 10, 60);
    this.pixels[1][0] = new Pixel(250, 130, 160);
    this.pixels[1][1] = new Pixel(255, 140, 150);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("image-brighten10", photo2);
    this.controller.startProcessor();
    this.finalModel.brighten(10, "image", "image-brighten10");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testBrightenDown() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(230, 110, 90);
    this.pixels[0][1] = new Pixel(245, 0, 40);
    this.pixels[1][0] = new Pixel(230, 110, 140);
    this.pixels[1][1] = new Pixel(240, 120, 130);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("image-brightenDown10", photo2);
    this.controller.startProcessor();
    this.finalModel.brighten(-10, "image", "image-brightenDown10");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testRedGreyScale() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(240, 240, 240);
    this.pixels[0][1] = new Pixel(255, 255, 255);
    this.pixels[1][0] = new Pixel(240, 240, 240);
    this.pixels[1][1] = new Pixel(250, 250, 250);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageRed", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Red, "image",
            "imageRed");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testGreenGreyScale() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(120, 120, 120);
    this.pixels[0][1] = new Pixel(0, 0, 0);
    this.pixels[1][0] = new Pixel(120, 120, 120);
    this.pixels[1][1] = new Pixel(130, 130, 130);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageGreen", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Green,
            "image", "imageGreen");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testBlueGreyScale() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(100, 100, 100);
    this.pixels[0][1] = new Pixel(50, 50, 50);
    this.pixels[1][0] = new Pixel(150, 150, 150);
    this.pixels[1][1] = new Pixel(140, 140, 140);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageBlue", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Blue, "image",
            "imageBlue");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testValueGreyScale() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(240, 240, 240);
    this.pixels[0][1] = new Pixel(255, 255, 255);
    this.pixels[1][0] = new Pixel(240, 240, 240);
    this.pixels[1][1] = new Pixel(250, 250, 250);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageValue", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Value, "image",
            "imageValue");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testKoalaIntensity() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(153, 153, 153);
    this.pixels[0][1] = new Pixel(101, 101, 101);
    this.pixels[1][0] = new Pixel(170, 170, 170);
    this.pixels[1][1] = new Pixel(173, 173, 173);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageIntensity", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Intensity, "image",
            "imageIntensity");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testKoalaLuma() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(143, 143, 143);
    this.pixels[0][1] = new Pixel(57, 57, 57);
    this.pixels[1][0] = new Pixel(146, 146, 146);
    this.pixels[1][1] = new Pixel(155, 155, 155);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageLuma", photo2);
    this.controller.startProcessor();
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Luma, "image",
            "imageLuma");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testGetLoadedImage() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.controller.startProcessor();
    assertEquals(this.images, this.finalModel.getLoadedImages());

    Map<String, GeneralImage> photoMap = this.model.getLoadedImages();
    photoMap.clear();

    Map<String, GeneralImage> expected = new HashMap<>();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(240, 120, 100);
    this.pixels[0][1] = new Pixel(255, 0, 50);
    this.pixels[1][0] = new Pixel(240, 120, 150);
    this.pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage photo = new GeneralImage(this.pixels, 2, 2, 255);
    expected.put("image", photo);

    // determines if getLoadedImages allows the user to mutate the processing model
    assertEquals(expected, this.finalModel.getLoadedImages());
  }

  @Test
  // Flip --> RedGreyScale
  public void testStackCommands() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.controller.startProcessor();
    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(240, 120, 100);
    this.pixels[1][1] = new Pixel(255, 0, 50);
    this.pixels[0][0] = new Pixel(240, 120, 150);
    this.pixels[0][1] = new Pixel(250, 130, 140);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageVertical", photo2);

    this.finalModel.flip(false, "image", "imageVertical");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(240, 240, 240);
    this.pixels[1][1] = new Pixel(255, 255, 255);
    this.pixels[0][0] = new Pixel(240, 240, 240);
    this.pixels[0][1] = new Pixel(250, 250, 250);
    GeneralImage photo3 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("image", photo3);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Red,
            "imageVertical", "image");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(255, 255, 255);
    this.pixels[1][1] = new Pixel(255, 255, 255);
    this.pixels[0][0] = new Pixel(255, 255, 255);
    this.pixels[0][1] = new Pixel(255, 255, 255);
    GeneralImage photo4 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("image", photo4);

    this.finalModel.brighten(50, "image", "image");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(205, 205, 205);
    this.pixels[1][1] = new Pixel(205, 205, 205);
    this.pixels[0][0] = new Pixel(205, 205, 205);
    this.pixels[0][1] = new Pixel(205, 205, 205);
    GeneralImage photo5 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("image", photo5);

    this.finalModel.brighten(-50, "image", "image");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(205, 205, 205);
    this.pixels[1][1] = new Pixel(205, 205, 205);
    this.pixels[0][0] = new Pixel(205, 205, 205);
    this.pixels[0][1] = new Pixel(205, 205, 205);
    GeneralImage photo6 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageValue", photo6);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Value, "image",
            "imageValue");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(205, 205, 205);
    this.pixels[1][1] = new Pixel(205, 205, 205);
    this.pixels[0][0] = new Pixel(205, 205, 205);
    this.pixels[0][1] = new Pixel(205, 205, 205);
    GeneralImage photo7 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageIntensity", photo7);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Intensity,
            "image", "imageIntensity");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][0] = new Pixel(203, 203, 203);
    this.pixels[1][1] = new Pixel(203, 203, 203);
    this.pixels[0][0] = new Pixel(203, 203, 203);
    this.pixels[0][1] = new Pixel(203, 203, 203);
    GeneralImage photo8 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageLuma", photo8);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Luma, "image",
            "imageLuma");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[1][1] = new Pixel(205, 205, 205);
    this.pixels[1][0] = new Pixel(205, 205, 205);
    this.pixels[0][1] = new Pixel(205, 205, 205);
    this.pixels[0][0] = new Pixel(205, 205, 205);
    GeneralImage photo9 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageHorizontal", photo9);

    this.finalModel.flip(true, "image",
            "imageHorizontal");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testBlurAndSharpen() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(137, 53, 58);
    this.pixels[0][1] = new Pixel(140, 38, 51);
    this.pixels[1][0] = new Pixel(137, 61, 70);
    this.pixels[1][1] = new Pixel(139, 55, 66);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    assertEquals(new HashMap<>(), this.finalModel.getLoadedImages());
    this.images.put("imageBlur", photo2);

    this.controller.startProcessor();
    this.finalModel.blur("image", "imageBlur");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(255, 182, 185);
    this.pixels[0][1] = new Pixel(255, 92, 147);
    this.pixels[1][0] = new Pixel(255, 182, 222);
    this.pixels[1][1] = new Pixel(255, 190, 215);
    GeneralImage photo3 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageSharpen", photo3);

    this.finalModel.sharpen("image", "imageSharpen");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testSepiaAndGreyscale() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(204, 181, 142);
    this.pixels[0][1] = new Pixel(109, 96, 75);
    this.pixels[1][0] = new Pixel(214, 190, 148);
    this.pixels[1][1] = new Pixel(223, 199, 155);
    GeneralImage photo2 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageSepia", photo2);

    this.controller.startProcessor();
    this.finalModel.sepia("image", "imageSepia");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(143, 143, 143);
    this.pixels[0][1] = new Pixel(57, 57, 57);
    this.pixels[1][0] = new Pixel(146, 146, 146);
    this.pixels[1][1] = new Pixel(155, 155, 155);
    GeneralImage photo3 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageGreyscale", photo3);

    this.finalModel.greyscale("image", "imageGreyscale");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(143, 143, 143);
    this.pixels[0][1] = new Pixel(57, 57, 57);
    this.pixels[1][0] = new Pixel(146, 146, 146);
    this.pixels[1][1] = new Pixel(155, 155, 155);
    GeneralImage photo4 = new GeneralImage(this.pixels, 2, 2, 255);
    this.images.put("imageLuma", photo4);

    this.finalModel.greyscale("image", "imageLuma");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    // check that both methods that compute luma are the same
    assertEquals(this.images.get("imageLuma"), this.images.get("imageGreyscale"));
  }

  // PPM Loaded --> Save to other file types
  @Test
  public void testloadPPMSaveToOtherFileTypes() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.ppm image\nq\n"));
    this.controller.startProcessor();
    assertEquals(this.images, this.finalModel.getLoadedImages());

    try {
      this.controller.save("images/image.png", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.bmp", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.jpg", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.ppm", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }
  }

  // PNG Loaded --> Save to other file types
  @Test
  public void testloadPNGSaveToOtherFileTypes() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.png image\nq\n"));
    this.controller.startProcessor();
    assertEquals(this.images, this.finalModel.getLoadedImages());

    try {
      this.controller.save("images/image.png", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.bmp", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.jpg", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.ppm", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }
  }

  // BMP Loaded --> Save to other file types
  @Test
  public void testloadBMPSaveToOtherFileTypes() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.bmp image\nq\n"));
    this.controller.startProcessor();
    assertEquals(this.images, this.finalModel.getLoadedImages());

    try {
      this.controller.save("images/image.png", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.bmp", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.jpg", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.ppm", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }
  }

  // JPG Loaded --> Save to other file types
  // JPG uses different color compression that other file formats, which is why images is different
  @Test
  public void testloadJPGSaveToOtherFileTypes() {
    this.model = new SimpleProcessingModel();
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.jpg image\nq\n"));
    this.controller.startProcessor();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(245, 86, 106);
    this.pixels[0][1] = new Pixel(212, 53, 73);
    this.pixels[1][0] = new Pixel(255, 128, 148);
    this.pixels[1][1] = new Pixel(255, 115, 135);
    GeneralImage photo = new GeneralImage(pixels, 2, 2, 255);
    this.images.put("image", photo);
    assertEquals(this.images, this.finalModel.getLoadedImages());

    try {
      this.controller.save("images/image.png", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.bmp", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.jpg", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

    try {
      this.controller.save("images/image.ppm", "image");
    } catch (IllegalArgumentException e) {
      fail("Save should have been successful.");
    }

  }

  @Test
  public void testBetterProcessingModelCommands() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.jpg image\nq\n"));
    this.controller.startProcessor();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(245, 86, 106);
    this.pixels[0][1] = new Pixel(212, 53, 73);
    this.pixels[1][0] = new Pixel(255, 128, 148);
    this.pixels[1][1] = new Pixel(255, 115, 135);
    GeneralImage photo = new GeneralImage(pixels, 2, 2, 255);
    this.images.put("image", photo);
    assertEquals(this.images, this.finalModel.getLoadedImages());
    // Sepia
    this.finalModel.sepia("image", "imageSepia");
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(182, 160, 124);
    this.pixels[0][1] = new Pixel(136, 121, 94);
    this.pixels[1][0] = new Pixel(225, 199, 156);
    this.pixels[1][1] = new Pixel(213, 188, 147);
    GeneralImage photo2 = new GeneralImage(pixels, 2, 2, 255);
    this.images.put("imageSepia", photo2);
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(135, 51, 62);
    this.pixels[0][1] = new Pixel(131, 46, 57);
    this.pixels[1][0] = new Pixel(139, 60, 71);
    this.pixels[1][1] = new Pixel(137, 56, 68);
    GeneralImage photo3 = new GeneralImage(pixels, 2, 2, 255);
    this.images.put("imageBlur", photo3);
    // Blur
    this.finalModel.blur("image", "imageBlur");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(255, 160, 195);
    this.pixels[0][1] = new Pixel(255, 135, 170);
    this.pixels[1][0] = new Pixel(255, 191, 226);
    this.pixels[1][1] = new Pixel(255, 181, 216);
    GeneralImage photo4 = new GeneralImage(pixels, 2, 2, 255);
    // sharpen
    this.images.put("imageSharpen", photo4);
    this.finalModel.sharpen("image", "imageSharpen");
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(120, 120, 120);
    this.pixels[0][1] = new Pixel(87, 87, 87);
    this.pixels[1][0] = new Pixel(155, 155, 155);
    this.pixels[1][1] = new Pixel(145, 145, 145);
    GeneralImage photo5 = new GeneralImage(pixels, 2, 2, 255);
    // sharpen
    this.images.put("imageGreyScale", photo5);
    this.finalModel.greyscale("image", "imageGreyScale");
    assertEquals(this.images, this.finalModel.getLoadedImages());
  }

  @Test
  public void testPIMOperations() {
    Pixel[][] maskPixels;
    this.images = new HashMap<>();
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(255, 0, 0);
    this.pixels[0][1] = new Pixel(0, 255, 0);
    this.pixels[1][0] = new Pixel(0, 0, 255);
    this.pixels[1][1] = new Pixel(255, 255, 0);
    GeneralImage original = new GeneralImage(this.pixels, 2, 2, 255);
    this.finalModel.addImage("original", original);

    maskPixels = new Pixel[2][2];
    maskPixels[0][0] = new Pixel(0, 0, 0);
    maskPixels[0][1] = new Pixel(255, 255, 255);
    maskPixels[1][0] = new Pixel(255, 255, 255);
    maskPixels[1][1] = new Pixel(0, 0, 0);
    GeneralImage originalmask = new GeneralImage(maskPixels, 2, 2, 255);
    this.finalModel.addImage("mask", originalmask);

    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(79, 47, 31);
    this.pixels[0][1] = new Pixel(0, 255, 0);
    this.pixels[1][0] = new Pixel(0, 0, 255);
    this.pixels[1][1] = new Pixel(79, 95, 31);
    GeneralImage blurred = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialBlur", blurred);

    this.finalModel.blur("original", "mask", "partialBlur");
    assertEquals(this.images.get("partialBlur"),
            this.finalModel.getLoadedImages().get("partialBlur"));

    this.pixels[0][0] = new Pixel(255, 127, 63);
    this.pixels[1][1] = new Pixel(255, 255, 63);
    GeneralImage sharpened = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialSharpen", sharpened);

    this.finalModel.sharpen("original", "mask", "partialSharpen");
    assertEquals(this.images.get("partialSharpen"),
            this.finalModel.getLoadedImages().get("partialSharpen"));

    this.pixels[0][0] = new Pixel(100, 88, 69);
    this.pixels[1][1] = new Pixel(255, 255, 205);
    GeneralImage sepia = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialSepia", sepia);

    this.finalModel.sepia("original", "mask", "partialSepia");
    assertEquals(this.images.get("partialSepia"),
            this.finalModel.getLoadedImages().get("partialSepia"));

    this.pixels[0][0] = new Pixel(54, 54, 54);
    this.pixels[1][1] = new Pixel(236, 236, 236);
    GeneralImage greyscale = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialGreyscale", greyscale);

    this.finalModel.greyscale("original", "mask", "partialGreyscale");
    assertEquals(this.images.get("partialGreyscale"),
            this.finalModel.getLoadedImages().get("partialGreyscale"));
    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Luma, "original", "mask",
            "partialGreyscale");
    assertEquals(this.images.get("partialGreyscale"),
            this.finalModel.getLoadedImages().get("partialGreyscale"));

    this.pixels[0][0] = new Pixel(255, 50, 50);
    this.pixels[1][1] = new Pixel(255, 255, 50);
    GeneralImage brighten50 = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialBrighten", brighten50);

    this.finalModel.brighten(50, "original", "mask", "partialBrighten");
    assertEquals(this.images.get("partialBrighten"),
            this.finalModel.getLoadedImages().get("partialBrighten"));

    this.pixels[0][0] = new Pixel(205, 0, 0);
    this.pixels[1][1] = new Pixel(205, 205, 0);
    GeneralImage darken50 = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialDarken", darken50);

    this.finalModel.brighten(-50, "original", "mask", "partialDarken");
    assertEquals(this.images.get("partialDarken"),
            this.finalModel.getLoadedImages().get("partialDarken"));

    this.pixels[0][0] = new Pixel(255, 255, 255);
    this.pixels[1][1] = new Pixel(255, 255, 255);
    GeneralImage redComponent = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialRedComponent", redComponent);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Red, "original", "mask",
            "partialRedComponent");
    assertEquals(this.images.get("partialRedComponent"),
            this.finalModel.getLoadedImages().get("partialRedComponent"));

    this.pixels[0][0] = new Pixel(0, 0, 0);
    this.pixels[1][1] = new Pixel(255, 255, 255);
    GeneralImage greenComponent = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialGreenComponent", greenComponent);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Green, "original", "mask",
            "partialGreenComponent");
    assertEquals(this.images.get("partialGreenComponent"),
            this.finalModel.getLoadedImages().get("partialGreenComponent"));

    this.pixels[0][0] = new Pixel(0, 0, 0);
    this.pixels[1][1] = new Pixel(0, 0, 0);
    GeneralImage blueComponent = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialBlueComponent", blueComponent);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Blue, "original", "mask",
            "partialBlueComponent");
    assertEquals(this.images.get("partialBlueComponent"),
            this.finalModel.getLoadedImages().get("partialBlueComponent"));

    this.pixels[0][0] = new Pixel(255, 255, 255);
    this.pixels[1][1] = new Pixel(255, 255, 255);
    GeneralImage valueComponent = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialValueComponent", valueComponent);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Value, "original", "mask",
            "partialValueComponent");
    assertEquals(this.images.get("partialValueComponent"),
            this.finalModel.getLoadedImages().get("partialValueComponent"));

    this.pixels[0][0] = new Pixel(85, 85, 85);
    this.pixels[1][1] = new Pixel(170, 170, 170);
    GeneralImage intensityComponent = new GeneralImage(this.pixels, 2, 2, 255);
    images.put("partialIntensityComponent", intensityComponent);

    this.finalModel.componentGreyScale(IProcessingModel.GreyscaleType.Intensity, "original",
            "mask", "partialIntensityComponent");
    assertEquals(this.images.get("partialIntensityComponent"),
            this.finalModel.getLoadedImages().get("partialIntensityComponent"));
  }

  @Test
  public void testLoadAndSave() {
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/SmallPPM.png image\n" +
                    "load images/image.ppm image q\n" +
                    "load images/image.bmp image\n" +
                    "save images/image.bmp image\n" +
                    "load images/image.ppm image\n" +
                    "load images/image.jpg image\nq\n"));
    this.controller.startProcessor();

    assertEquals(this.images, this.finalModel.getLoadedImages());
    this.pixels = new Pixel[2][2];
    this.pixels[0][0] = new Pixel(240, 120, 100);
    this.pixels[0][1] = new Pixel(255, 0, 50);
    this.pixels[1][0] = new Pixel(240, 120, 150);
    this.pixels[1][1] = new Pixel(250, 130, 140);
    this.images.put("image", new GeneralImage(this.pixels, 2, 2, 255));
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.finalModel.downScale("image", "imageDownscale",
            0.5, 0.5);

    this.pixels = new Pixel[1][1];
    this.pixels[0][0] = new Pixel(240, 120, 100);
    this.images.put("imageDownscale", new GeneralImage(this.pixels, 1, 1,
            255));
    assertEquals(this.images, this.finalModel.getLoadedImages());

    this.pixels = new Pixel[4][4];
    this.pixels[0][0] = new Pixel(100, 200, 75);
    this.pixels[0][1] = new Pixel(150, 75, 75);
    this.pixels[0][2] = new Pixel(100, 150, 100);
    this.pixels[0][3] = new Pixel(75, 200, 150);

    this.pixels[1][0] = new Pixel(100, 200, 150);
    this.pixels[1][1] = new Pixel(100, 150, 100);
    this.pixels[1][2] = new Pixel(150, 75, 100);
    this.pixels[1][3] = new Pixel(100, 150, 100);

    this.pixels[2][0] = new Pixel(100, 200, 100);
    this.pixels[2][1] = new Pixel(100, 200, 150);
    this.pixels[2][2] = new Pixel(100, 150, 100);
    this.pixels[2][3] = new Pixel(100, 75, 75);

    this.pixels[3][0] = new Pixel(150, 200, 150);
    this.pixels[3][1] = new Pixel(150, 200, 75);
    this.pixels[3][2] = new Pixel(75, 150, 100);
    this.pixels[3][3] = new Pixel(150, 200, 75);
    this.finalModel.addImage("newImage", new GeneralImage(this.pixels,
            4, 4, 255));

    this.finalModel.downScale("newImage", "newImageDownscale",
            0.5, 1.0);

    Pixel[][] newPixels = new Pixel[4][2];
    newPixels[0][0] = new Pixel(100, 200, 75);
    newPixels[0][1] = new Pixel(100, 150, 100);

    newPixels[1][0] = new Pixel(100, 200, 150);
    newPixels[1][1] = new Pixel(150, 75, 100);

    newPixels[2][0] = new Pixel(100, 200, 100);
    newPixels[2][1] = new Pixel(100, 150, 100);

    newPixels[3][0] = new Pixel(150, 200, 150);
    newPixels[3][1] = new Pixel(75, 150, 100);

    assertEquals(new GeneralImage(newPixels, 2, 4, 255),
            this.finalModel.getImage("newImageDownscale"));

    this.pixels = new Pixel[4][3];
    this.pixels[0][0] = new Pixel(100, 200, 75);
    this.pixels[0][1] = new Pixel(150, 75, 75);
    this.pixels[0][2] = new Pixel(100, 150, 100);

    this.pixels[1][0] = new Pixel(100, 200, 150);
    this.pixels[1][1] = new Pixel(100, 150, 100);
    this.pixels[1][2] = new Pixel(150, 75, 100);

    this.pixels[2][0] = new Pixel(100, 200, 100);
    this.pixels[2][1] = new Pixel(100, 200, 150);
    this.pixels[2][2] = new Pixel(100, 150, 100);

    this.pixels[3][0] = new Pixel(150, 200, 150);
    this.pixels[3][1] = new Pixel(150, 200, 75);
    this.pixels[3][2] = new Pixel(75, 150, 100);
    this.finalModel.addImage("newImage", new GeneralImage(this.pixels,
            3, 4, 255));

    this.finalModel.downScale("newImage", "newImageDownscale",
            0.3, 0.7);

    Pixel[][] newPixelsV2 = new Pixel[3][1];
    newPixelsV2[0][0] = new Pixel(100, 200, 75);

    newPixelsV2[1][0] = new Pixel(100, 200, 128);

    newPixelsV2[2][0] = new Pixel(142, 200, 142);

    assertEquals(new GeneralImage(newPixelsV2, 1, 3, 255),
            this.finalModel.getImage("newImageDownscale"));
  }

  @Test
  public void testConfirmInputsDownscale() {
    Appendable log = new StringBuilder();
    this.finalModel = new ProcessorMock(log);
    this.view = new SimpleView(this.model, new StringBuilder());
    this.controller = new ControllerImpl(this.finalModel, this.view,
            new StringReader("load images/image.jpg image\n" +
                    "downscale image imageDownscale 0.5 0.5\nq\n"));
    this.controller.startProcessor();

    assertEquals("Add Image. Image Name: image. Image-Width: 2. Image-Height: 2. " +
            "Image-MaxValue: 255.\n" +
            "Mask Image DownScale. Src Image Name: image. Dest Image Name: imageDownscale. " +
            "Width Scale Factor: 0.5. Height Scale Factor: 0.5.", log.toString());
  }

}