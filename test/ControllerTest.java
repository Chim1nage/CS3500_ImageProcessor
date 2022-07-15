import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.ControllerImpl;
import controller.IController;
import model.FinalProcessingModel;
import model.IFinalProcessingModel;
import model.util.GeneralImage;
import model.util.Pixel;
import view.IView;
import view.SimpleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test the controller output, and verifies correct input to different methods.
 */
public final class ControllerTest {
  private IFinalProcessingModel model;
  private IView view;
  private Readable input;
  private Appendable output;
  private Appendable corruptOutput;
  private IController controller;

  @Before
  public void init() {
    this.model = new FinalProcessingModel();
    this.output = new StringBuilder();
    this.corruptOutput = new CorruptAppendable();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/SmallPPM.ppm original\nred-component " +
        "original originalRed\n"
        + "green-component original originalGreen\nblue-component original originalBlue\n"
        + "value-component original originalValue\nintensity-component original " +
        "originalIntensity\n"
        + "luma-component original originalLuma\nhorizontal-flip original original-horizontal\n"
        + "vertical-component original original-vertical\nbrighten 50 original " +
        "originalBrighten\n" +
        "brighten -50 original originalDarken\n" +
        "load images/Unknown.ppm koala\n" +
        "green-component original original\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
  }

  @Test
  public void testNullInputs() {
    try {
      this.controller = new ControllerImpl(this.model, this.view, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Supplied model, view, or readable is null.", e.getMessage());
    }
    try {
      this.controller = new ControllerImpl(null, this.view, new StringReader("q\n"));
    } catch (IllegalArgumentException e) {
      assertEquals("Supplied model, view, or readable is null.", e.getMessage());
    }
    try {
      this.controller = new ControllerImpl(this.model, null, new StringReader("Q\n"));
    } catch (IllegalArgumentException e) {
      assertEquals("Supplied model, view, or readable is null.", e.getMessage());
    }


  }

  @Test
  public void testAllCommandsController() {
    assertEquals("", this.output.toString());
    this.controller.startProcessor();
    assertEquals("" +
        "Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName destImageName\n"
        + "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed o" +
        "n that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command (of " +
        "the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "red-component visualization applied.\n" +
        "green-component visualization applied.\n" +
        "blue-component visualization applied.\n" +
        "value-component visualization applied.\n" +
        "intensity-component visualization applied.\n" +
        "luma-component visualization applied.\n" +
        "horizontal-flip applied.\n" +
        "Invalid Inputs. Try Again.\n" +
        "Image was brightened by factor of: 50.\n" +
        "Image was darkened by factor of: -50.\n" +
        "File is loading. Please wait...\n" +
        "Invalid supplied file.\n" +
        "green-component visualization applied.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testInvalidInputs() {
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/Unkown.ppm image\nload images/SmallPPM.ppm Small"
        + "\nvertical-flip Small Small\n" +
        "load images/SmallPPM.ppm\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
    assertEquals(""
        + "Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black" +
        " and white version) ofthat image where partial image manipulation will be" +
        " performed on that mask image.)**To quit: Press 'q' or 'Q'. Any position " +
        "in your command (of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "Invalid supplied file.\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "vertical-flip applied.\n" +
        "Invalid Inputs. Try Again.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testInvalidAppendable() {
    this.controller = new ControllerImpl(this.model, new SimpleView(this.model, this.corruptOutput),
        this.input);
    try {
      this.controller.startProcessor();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Unable to transmit message.", e.getMessage());
    }
  }

  @Test
  public void testDifferentInputs() {
    this.input = new StringReader("load images/SmallPPM.ppm image\nred-component image image\n"
        + "green-component image image\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "red-component visualization applied.\n" +
        "green-component visualization applied.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testReadableOutOfInputs() {
    this.input = new StringReader("load images/SmallPPM.ppm image\nred-component image image\n"
        + "green-component image image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);

    try {
      this.controller.startProcessor();
    } catch (IllegalStateException e) {
      assertEquals("Readable has run out of inputs.", e.getMessage());
    }

  }

  @Test
  public void testSave() {
    this.input = new StringReader("load images/SmallPPM.ppm image\nsave images/TrySave.ppm "
        + "image\nsave badInput image\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
    assertEquals(""
        + "Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Image saving. Please wait...\n" +
        "Invalid supplied file type.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  // test vertical flip
  @Test
  public void testLoadAndSave() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new Pixel(240, 120, 100);
    pixels[0][1] = new Pixel(255, 0, 50);
    pixels[1][0] = new Pixel(240, 120, 150);
    pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage originalPhoto = new GeneralImage(pixels, 2, 2, 255);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[1][0] = new Pixel(240, 120, 100);
    pixels2[1][1] = new Pixel(255, 0, 50);
    pixels2[0][0] = new Pixel(240, 120, 150);
    pixels2[0][1] = new Pixel(250, 130, 140);
    GeneralImage flippedPhoto = new GeneralImage(pixels2, 2, 2, 255);

    this.input = new StringReader("load images/SmallPPM.ppm image\n" +
        "vertical-flip image imageVertical\nsave images/Vertical.ppm image\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals(originalPhoto, this.model.getLoadedImages().get("image"));
    assertEquals(flippedPhoto, this.model.getLoadedImages().get("imageVertical"));
  }

  // test horizontal flip
  @Test
  public void testLoadAndSaveV2() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new Pixel(240, 120, 100);
    pixels[0][1] = new Pixel(255, 0, 50);
    pixels[1][0] = new Pixel(240, 120, 150);
    pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage originalPhoto = new GeneralImage(pixels, 2, 2, 255);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][1] = new Pixel(240, 120, 100);
    pixels2[0][0] = new Pixel(255, 0, 50);
    pixels2[1][1] = new Pixel(240, 120, 150);
    pixels2[1][0] = new Pixel(250, 130, 140);
    GeneralImage flippedPhoto = new GeneralImage(pixels2, 2, 2, 255);

    this.input = new StringReader("load images/SmallPPM.ppm image\n" +
        "horizontal-flip image imageHorizontal\nsave images/Horizontal.ppm image\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals(originalPhoto, this.model.getLoadedImages().get("image"));
    assertEquals(flippedPhoto, this.model.getLoadedImages().get("imageHorizontal"));
  }

  // test brighten +50
  @Test
  public void testLoadAndSaveV3() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new Pixel(240, 120, 100);
    pixels[0][1] = new Pixel(255, 0, 50);
    pixels[1][0] = new Pixel(240, 120, 150);
    pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage originalPhoto = new GeneralImage(pixels, 2, 2, 255);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(255, 150, 130);
    pixels2[0][1] = new Pixel(255, 30, 80);
    pixels2[1][0] = new Pixel(255, 150, 180);
    pixels2[1][1] = new Pixel(255, 160, 170);
    GeneralImage brightPhoto = new GeneralImage(pixels2, 2, 2, 255);

    Pixel[][] pixels3 = new Pixel[2][2];
    pixels3[0][0] = new Pixel(190, 70, 50);
    pixels3[0][1] = new Pixel(205, 0, 0);
    pixels3[1][0] = new Pixel(190, 70, 100);
    pixels3[1][1] = new Pixel(200, 80, 90);
    GeneralImage dimPhoto = new GeneralImage(pixels3, 2, 2, 255);

    this.input = new StringReader("load images/SmallPPM.ppm image\n" +
        "brighten 30 image imageBright\nsave images/Bright.ppm image\n" +
        "brighten -50 image imageDim\nsave images/Dim.ppm imageDim\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals(originalPhoto, this.model.getLoadedImages().get("image"));
    assertEquals(brightPhoto, this.model.getLoadedImages().get("imageBright"));
    assertEquals(dimPhoto, this.model.getLoadedImages().get("imageDim"));
  }

  // test all grey scale
  @Test
  public void testLoadAndSaveV4() {
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new Pixel(240, 120, 100);
    pixels[0][1] = new Pixel(255, 0, 50);
    pixels[1][0] = new Pixel(240, 120, 150);
    pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage originalPhoto = new GeneralImage(pixels, 2, 2, 255);

    Pixel[][] pixels2 = new Pixel[2][2];
    pixels2[0][0] = new Pixel(240, 240, 240);
    pixels2[0][1] = new Pixel(255, 255, 255);
    pixels2[1][0] = new Pixel(240, 240, 240);
    pixels2[1][1] = new Pixel(250, 250, 250);
    GeneralImage redPhoto = new GeneralImage(pixels2, 2, 2, 255);

    Pixel[][] pixels3 = new Pixel[2][2];
    pixels3[0][0] = new Pixel(120, 120, 120);
    pixels3[0][1] = new Pixel(0, 0, 0);
    pixels3[1][0] = new Pixel(120, 120, 120);
    pixels3[1][1] = new Pixel(130, 130, 130);
    GeneralImage greenPhoto = new GeneralImage(pixels3, 2, 2, 255);

    Pixel[][] pixels4 = new Pixel[2][2];
    pixels4[0][0] = new Pixel(100, 100, 100);
    pixels4[0][1] = new Pixel(50, 50, 50);
    pixels4[1][0] = new Pixel(150, 150, 150);
    pixels4[1][1] = new Pixel(140, 140, 140);
    GeneralImage bluePhoto = new GeneralImage(pixels4, 2, 2, 255);

    Pixel[][] pixles5 = new Pixel[2][2];
    pixles5[0][0] = new Pixel(240, 240, 240);
    pixles5[0][1] = new Pixel(255, 255, 255);
    pixles5[1][0] = new Pixel(240, 240, 240);
    pixles5[1][1] = new Pixel(250, 250, 250);
    GeneralImage valuePhoto = new GeneralImage(pixles5, 2, 2, 255);

    Pixel[][] pixels6 = new Pixel[2][2];
    pixels6[0][0] = new Pixel(153, 153, 153);
    pixels6[0][1] = new Pixel(101, 101, 101);
    pixels6[1][0] = new Pixel(170, 170, 170);
    pixels6[1][1] = new Pixel(173, 173, 173);
    GeneralImage intensityPhoto = new GeneralImage(pixels6, 2, 2, 255);

    Pixel[][] pixels7 = new Pixel[2][2];
    pixels7[0][0] = new Pixel(143, 143, 143);
    pixels7[0][1] = new Pixel(57, 57, 57);
    pixels7[1][0] = new Pixel(146, 146, 146);
    pixels7[1][1] = new Pixel(155, 155, 155);
    GeneralImage lumaPhoto = new GeneralImage(pixels7, 2, 2, 255);

    this.input = new StringReader("load images/SmallPPM.ppm image\n" +
        "red-component image imageRed\nsave images/RedImage.ppm imageRed\n" +
        "green-component image imageGreen\nsave images/Green.ppm imageGreen\n" +
        "blue-component image imageBlue\nsave images/Blue.ppm imageBlue\n" +
        "value-component image imageValue\nsave images/Value.ppm imageValue\n" +
        "intensity-component image imageIntensity\nsave images/Intensity.ppm imageIntensity\n"
        + "luma-component image imageLuma\nsave images/LumaImage.ppm imageLuma\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals(originalPhoto, this.model.getLoadedImages().get("image"));
    assertEquals(redPhoto, this.model.getLoadedImages().get("imageRed"));
    assertEquals(greenPhoto, this.model.getLoadedImages().get("imageGreen"));
    assertEquals(bluePhoto, this.model.getLoadedImages().get("imageBlue"));
    assertEquals(valuePhoto, this.model.getLoadedImages().get("imageValue"));
    assertEquals(intensityPhoto, this.model.getLoadedImages().get("imageIntensity"));
    assertEquals(lumaPhoto, this.model.getLoadedImages().get("imageLuma"));
  }

  @Test
  public void testQuitDiffernetPlacesAndInvalidInput() {
    this.input = new StringReader("q load images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName" +
        " widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and" +
        " white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "Photo Editor Quit.\n", this.output.toString());

    this.input = new StringReader("load q images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
  }

  @Test
  public void testQuitDifferentPlacesAndInvalidInputV2() {
    this.input = new StringReader("load q images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale" +
        " after you supply name of image, you can supply name of mask image (black " +
        "and white version) ofthat image where partial image manipulation will be performed " +
        "on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command (of " +
        "the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "Photo Editor Quit.\n", this.output.toString());

    this.input = new StringReader("load q images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
  }

  @Test
  public void testQuitDifferentPlacesAndInvalidInputV3() {
    this.input = new StringReader("load images/SmallPPM.ppm q image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName" +
        " widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale" +
        " after you supply name of image, you can supply name of mask image (black and" +
        " white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "Photo Editor Quit.\n", this.output.toString());

    this.input = new StringReader("load q images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
  }

  @Test
  public void testQuitDifferentPlacesAndInvalidInputV4() {
    this.input = new StringReader("load images/SmallPPM.ppm image q\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command" +
        " (of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "Photo Editor Quit.\n", this.output.toString());

    this.input = new StringReader("load q images/SmallPPM.ppm image\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();
  }

  @Test
  public void testQuitDifferentPlacesAndInvalidInputV5() {
    this.input = new StringReader("frifjr\n frnfjr q load images/SmallPPM.ppm\n" +
        "load images/SmallPPM.ppm efef  rrgr q\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale" +
        " after you supply name of image, you can supply name of mask image (black " +
        "and white version) ofthat image where partial image manipulation will be " +
        "performed on that mask image.)**To quit: Press 'q' or 'Q'. Any position in" +
        " your command (of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "Invalid Inputs. Try Again.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testLoadPPMSaveToOtherFileFormats() {
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/SmallPPM.ppm original\n" +
        "blur original originalBlur\n" +
        "save images/originalBlur.png originalBlur\n" +
        "sharpen original originalSharpen\n" +
        "save images/originalSharpen.jpg originalSharpen\n" +
        "sepia original originalSepia\n" +
        "save images/originalSepia.bmp originalSepia\n" +
        "greyscale original originalGreyscale\n" +
        "save images/originalGreyscale.ppm originalGreyscale\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale" +
        " after you supply name of image, you can supply name of mask image (black " +
        "and white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image Blurred.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Image Sharpened.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Sepia applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Greyscale applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testLoadJPGSaveToOtherFileFormats() {
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/SmallPPM.jpg originalJPG\n" +
        "blur originalJPG originalJPGBlur\n" +
        "save images/originalJPGBlur.png originalJPGBlur\n" +
        "sharpen originalJPG originalJPGSharpen\n" +
        "save images/originalJPGSharpen.jpg originalJPGSharpen\n" +
        "sepia originalJPG originalJPGSepia\n" +
        "save images/originalJPGSepia.bmp originalJPGSepia\n" +
        "greyscale originalJPG originalJPGGreyScale\n" +
        "save images/originalJPGGreyScale.ppm originalJPGGreyScale\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number)" +
        " imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and" +
        " white version) ofthat image where partial image manipulation will be performed " +
        "on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image Blurred.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Image Sharpened.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Sepia applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Greyscale applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testLoadPNGSaveToOtherFileFormats() {
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/SmallPPM.png original\n" +
        "blur original originalBlur\n" +
        "save images/originalBlur.png originalBlur\n" +
        "sharpen original originalSharpen\n" +
        "save images/originalSharpen.jpg originalSharpen\n" +
        "sepia original originalSepia\n" +
        "save images/originalSepia.bmp originalSepia\n" +
        "greyscale original originalGreyscale\n" +
        "save images/originalGreyscale.ppm\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale after" +
        " you supply name of image, you can supply name of mask image (black and white " +
        "version) ofthat image where partial image manipulation will be performed on " +
        "that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image Blurred.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Image Sharpened.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Sepia applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Greyscale applied.\n" +
        "Invalid Inputs. Try Again.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testLoaBMPSaveToOtherFileFormats() {
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.input = new StringReader("load images/SmallPPM.bmp originalBMP\n" +
        "blur originalBMP originalBMPBlur\n" +
        "save images/originalBMPBlur.png originalBMPBlur\n" +
        "sharpen originalBMP originalBMPSharpen\n" +
        "save images/originalBMPSharpen.jpg originalBMPSharpen\n" +
        "sepia originalBMP originalBMPSepia\n" +
        "save images/originalBMPSepia.bmp originalBMPSepia\n" +
        "greyscale originalBMP originalBMPGreyscale\n" +
        "save images/originalBMPGreyscale.ppm originalBMPGreyscale\nq\n");
    this.controller = new ControllerImpl(this.model, this.view, this.input);
    this.controller.startProcessor();

    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName" +
        " widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command " +
        "(of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image Blurred.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Image Sharpened.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Sepia applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Greyscale applied.\n" +
        "Image saving. Please wait...\n" +
        "Image saved. Continue:\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

  @Test
  public void testScript() {
    this.model = new FinalProcessingModel();
    Readable readable = new StringReader("load res/Hunt.ppm HuntPPM\n" +
        "load res/Hunt.png HuntPNG\n" +
        "load res/Hunt.jpg HuntJPG\n" +
        "load res/Hunt.bmp HuntBMP\n" +
        "vertical-flip HuntPPM HuntVerticalPPM\n" +
        "save res/HuntVertical.png HuntVerticalPPM\n" +
        "horizontal-flip HuntPNG HuntHorizontalPNG\n" +
        "save res/HuntHorizontal.jpg HuntHorizontalPNG\n" +
        "brighten 50 HuntPNG HuntBrighten\n" +
        "save res/HuntBrighten.bmp HuntBrighten\n" +
        "brighten -50 HuntPNG HuntDarken\n" +
        "save res/HuntDarken.ppm HuntDarken\n" +
        "red-component HuntBMP HuntRed\n" +
        "save res/HuntRed.bmp HuntRed\n" +
        "green-component HuntBMP HuntGreenBMP\n" +
        "save res/HuntGreen.bmp HuntGreenBMP\n" +
        "blue-component HuntJPG HuntBlueJPG\n" +
        "save res/HuntBlue.ppm HuntBlueJPG\n" +
        "value-component HuntPNG HuntValuePNG\n" +
        "save res/HuntValue.png HuntValuePNG\n" +
        "intensity-component HuntPNG HuntIntensityPNG\n" +
        "save res/HuntIntensity.bmp HuntIntensityPNG\n" +
        "luma-component HuntJPG HuntLumaJPG\n" +
        "save res/HuntLuma.ppm HuntLumaJPG\n" +
        "sepia HuntPPM HuntSepiaPPM\n" +
        "save res/HuntSepia.png HuntSepiaPPM\n" +
        "greyscale HuntPPM HuntGreyscalePPM\n" +
        "save res/HuntGreyscale.ppm HuntGreyscalePPM\n" +
        "blur HuntPNG HuntBlurPNG\n" +
        "save res/HuntBlur.jpg HuntBlurPNG\n" +
        "sharpen HuntJPG HuntSharpenJPG\n" +
        "save res/HuntSharpen.bmp HuntSharpenJPG\n" +
        "Q");
    this.model = new ProcessorMock(this.output);
    this.view = new SimpleView(this.model, new StringBuilder());
    this.controller = new ControllerImpl(this.model, this.view, readable);
    this.controller.startProcessor();
    assertEquals("Vertical-Flip. Image Name: HuntPPM. Destination Image Name: " +
            "HuntVerticalPPM.\n" +
            "Get Image. Image Name: HuntVerticalPPM.\n" +
            "Horizontal-Flip. Image Name: HuntPNG. Destination Image Name: " +
            "HuntHorizontalPNG.\n" +
            "Get Image. Image Name: HuntHorizontalPNG.\n" +
            "Brighten. Image Name: HuntPNG. Destination Image Name: HuntBrighten.\n" +
            "Get Image. Image Name: HuntBrighten.\n" +
            "Brighten. Image Name: HuntPNG. Destination Image Name: HuntDarken.\n" +
            "Get Image. Image Name: HuntDarken.\n" +
            "Red-component greyscale. Image Name: HuntBMP. Destination Image Name: " +
            "HuntRed.\n" +
            "Get Image. Image Name: HuntRed.\n" +
            "Green-component greyscale. Image Name: HuntBMP. Destination Image Name:" +
            " HuntGreenBMP.\n" +
            "Get Image. Image Name: HuntGreenBMP.\n" +
            "Blue-component greyscale. Image Name: HuntJPG. Destination Image Name: " +
            "HuntBlueJPG.\n" +
            "Get Image. Image Name: HuntBlueJPG.\n" +
            "Value-component greyscale. Image Name: HuntPNG. Destination Image Name: " +
            "HuntValuePNG.\n" +
            "Get Image. Image Name: HuntValuePNG.\n" +
            "Intensity-component greyscale. Image Name: HuntPNG. Destination Image " +
            "Name: HuntIntensityPNG.\n" +
            "Get Image. Image Name: HuntIntensityPNG.\n" +
            "Luma-component greyscale. Image Name: HuntJPG. Destination Image Name: " +
            "HuntLumaJPG.\n" +
            "Get Image. Image Name: HuntLumaJPG.\n" +
            "Sepia. Image Name: HuntPPM. Destination Image Name: HuntSepiaPPM.\n" +
            "Get Image. Image Name: HuntSepiaPPM.\n" +
            "Greyscale. Image Name: HuntPPM. Destination Image Name: HuntGreyscalePPM.\n" +
            "Get Image. Image Name: HuntGreyscalePPM.\n" +
            "Blur. Image Name: HuntPNG. Destination Image Name: HuntBlurPNG.\n" +
            "Get Image. Image Name: HuntBlurPNG.\n" +
            "Sharpen. Image Name: HuntJPG. Destination Image Name: HuntSharpenJPG.\n" +
            "Get Image. Image Name: HuntSharpenJPG.\n",
        this.output.toString());
  }

  @Test
  public void testInvalidScriptCommandsOutput() {
    Readable readable = new StringReader("load images/dff.jpg\n" +
        "load images/image.png image\n" +
        "save image image\n" +
        "brighten image image\n" +
        "blur image\n" +
        "sharpen\n" +
        "red-component images.jpg image\n" +
        "luma-component image\n" +
        "intensity-component image\n" +
        "greyscale image223 image\n" +
        "sepia image image image\n" +
        "ffufhu hfuirhjfiuj rifjirjfijrfijrifjir\n" +
        " Q\n ifhrihgrihgrhgu hrughr ir\n");
    this.model = new FinalProcessingModel();
    this.view = new SimpleView(this.model, this.output);
    this.controller = new ControllerImpl(this.model, this.view, readable);
    this.controller.startProcessor();
    assertEquals("Welcome to Photo Editor:\n" +
            "Syntax for Commands:\n" +
            "    - Load a photo to be edited:         load imagePath imageName\n" +
            "    - Flip vertically:                   vertical-flip imageName " +
            "destImageName\n" +
            "    - Flip horizontally:                 horizontal-flip imageName " +
            "destImageName\n" +
            "    - Brighten:                          brighten #(increment scale number) " +
            "imageName destImageName\n" +
            "    - Red-Component Greyscale:           red-component imageName " +
            "destImageName\n" +
            "    - Green-Component Greyscale:         green-component imageName " +
            "destImageName\n" +
            "    - Blue-Component Greyscale:          blue-component imageName " +
            "destImageName\n" +
            "    - Value-Component Greyscale:         value-component imageName " +
            "destImageName\n" +
            "    - Intensity-Component Greyscale:     intensity-component imageName " +
            "destImageName\n" +
            "    - Luma-Component Greyscale:          luma-component imageName " +
            "destImageName\n" +
            "    - Sepia:                             sepia imageName destImageName\n" +
            "    - General Greyscale:                 greyscale imageName destImageName\n" +
            "    - Sharpen:                           sharpen imageName destImageName\n" +
            "    - blur:                              blur imageName destImageName\n" +
            "    - Save:                              save imagePath imageName\n" +
            "    - Downscale:                         downscale imageName destImageName " +
            "widthScaleFactor heightScaleFactor\n" +
            "*(For component-greyscales, brightening, sharpen/blur, " +
            "and sepia/greyscale after you supply name of image, you can supply " +
            "name of mask image (black and white version) ofthat image where partial " +
            "image manipulation will be performed on that mask image.)**To quit: " +
            "Press 'q' or 'Q'. Any position in your command (of the singular letter) " +
            "will cause the Photo Editor to quit.*\n" +
            "Enter input below:\n" +
            "Invalid Inputs. Try Again.\n" +
            "File is loading. Please wait...\n" +
            "File loaded. Continue:\n" +
            "Image saving. Please wait...\n" +
            "Invalid supplied file type.\n" +
            "Brighten expects 4 or 5 parameters, got 3\n" +
            "The image to greyscale-component cannot be found\n" +
            "The image to greyscale cannot be found\n" +
            "Partial Sepia applied.\n" +
            "Invalid Inputs. Try Again.\n" +
            "Photo Editor Quit.\n",
        this.output.toString());
  }

  @Test
  public void testLoadEmptyFile() {
    try {
      this.controller.load("images/BlankImage.png", "BlankImage");
    } catch (IllegalArgumentException e) {
      assertEquals("Image to load is blank.", e.getMessage());
    }
    try {
      this.controller.load("images/BlankImage.bmp", "BlankImage");
    } catch (IllegalArgumentException e) {
      assertEquals("Image to load is blank.", e.getMessage());
    }
    try {
      this.controller.load("images/BlankImage.ppm", "BlankImage");
    } catch (IllegalArgumentException e) {
      assertEquals("Image to load is blank.", e.getMessage());
    }
    try {
      this.controller.load("images/BlankImage.jpg", "BlankImage");
    } catch (IllegalArgumentException e) {
      assertEquals("Image to load is blank.", e.getMessage());
    }
  }

  @Test
  public void testCorruptAppendable() {
    this.view = new SimpleView(this.model, new CorruptAppendable());
    this.controller = new ControllerImpl(this.model, this.view, new StringReader("q\n"));
    try {
      this.controller.startProcessor();
    } catch (IllegalStateException e) {
      assertEquals("Unable to transmit message.", e.getMessage());
    }
  }

  @Test
  public void testInvalidSave() {
    this.controller = new ControllerImpl(this.model, this.view,
        new StringReader("load images/SmallPPM.png image\n" +
            "save fdjgijg/grhgurhg.png image\n" +
            "save gijrgijtg/gihgitg.ppm image\n" +
            "save ghrighrig/grhgurg/grighr.bmp image\n" +
            "save girjgir/grighr/gjhri.jpg image\nq\n"));
    this.controller.startProcessor();
    assertEquals("Welcome to Photo Editor:\n" +
        "Syntax for Commands:\n" +
        "    - Load a photo to be edited:         load imagePath imageName\n" +
        "    - Flip vertically:                   vertical-flip imageName destImageName\n" +
        "    - Flip horizontally:                 horizontal-flip imageName destImageName\n" +
        "    - Brighten:                          brighten #(increment scale number) " +
        "imageName destImageName\n" +
        "    - Red-Component Greyscale:           red-component imageName destImageName\n" +
        "    - Green-Component Greyscale:         green-component imageName destImageName\n" +
        "    - Blue-Component Greyscale:          blue-component imageName destImageName\n" +
        "    - Value-Component Greyscale:         value-component imageName destImageName\n" +
        "    - Intensity-Component Greyscale:     intensity-component imageName " +
        "destImageName\n" +
        "    - Luma-Component Greyscale:          luma-component imageName destImageName\n" +
        "    - Sepia:                             sepia imageName destImageName\n" +
        "    - General Greyscale:                 greyscale imageName destImageName\n" +
        "    - Sharpen:                           sharpen imageName destImageName\n" +
        "    - blur:                              blur imageName destImageName\n" +
        "    - Save:                              save imagePath imageName\n" +
        "    - Downscale:                         downscale imageName destImageName " +
        "widthScaleFactor heightScaleFactor\n" +
        "*(For component-greyscales, brightening, sharpen/blur, and sepia/greyscale " +
        "after you supply name of image, you can supply name of mask image (black and " +
        "white version) ofthat image where partial image manipulation will be performed" +
        " on that mask image.)**To quit: Press 'q' or 'Q'. Any position in your command" +
        " (of the singular letter) will cause the Photo Editor to quit.*\n" +
        "Enter input below:\n" +
        "File is loading. Please wait...\n" +
        "File loaded. Continue:\n" +
        "Image saving. Please wait...\n" +
        "File destination to save to invalid.\n" +
        "Image saving. Please wait...\n" +
        "File save failed\n" +
        "Image saving. Please wait...\n" +
        "File destination to save to invalid.\n" +
        "Image saving. Please wait...\n" +
        "File destination to save to invalid.\n" +
        "Photo Editor Quit.\n", this.output.toString());
  }

}



