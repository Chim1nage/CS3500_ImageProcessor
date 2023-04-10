package controller;

import org.junit.Test;

import java.io.StringReader;

import model.IModel;
import model.ImageModel;

import static org.junit.Assert.assertEquals;

/**
 * Overall Test of program.
 */
public class OverallTest {
  /**
   * Test compile.
   */
  @Test
  public void testCompile() {
    Readable input = new StringReader("load res/2x2color.ppm 2x2color\n"
            + "brighten 40  2x2color 2x2colorBrighten\n"
            + "brighten -100  2x2color 2x2colorDarken\n"
            + "vertical-flip  2x2color 2x2colorVerticalFlip\n"
            + "horizontal-flip 2x2color 2x2colorHorizontalFlip\n"
            + "red-greyscale 2x2color 2x2colorRed\n"
            + "green-greyscale 2x2color 2x2colorGreen\n"
            + "blue-greyscale 2x2color 2x2colorBlue\n"
            + "value-greyscale 2x2color 2x2colorValue\n"
            + "intensity-greyscale 2x2color 2x2colorIntensity\n"
            + "luma-greyscale 2x2color 2x2colorLuma\n"
            + "blur 2x2color 2x2colorBlur\n"
            + "sharpen 2x2color 2x2colorSharpen\n"
            + "greyscale 2x2color 2x2colorGreyScale\n"
            + "sepia-tone 2x2color 2x2colorSepia\n"
            + "save res/All/2x2colorBrighten.ppm 2x2colorBrighten\n"
            + "save res/All/2x2colorDarken.ppm 2x2colorDarken\n"
            + "save res/All/2x2colorVerticalFlip.ppm 2x2colorVerticalFlip\n"
            + "save res/All/2x2colorHorizontalFlip.ppm 2x2colorHorizontalFlip\n"
            + "save res/All/2x2colorRed.ppm 2x2colorRed\n"
            + "save res/All/2x2colorGreen.ppm 2x2colorGreen\n"
            + "save res/All/2x2colorBlue.ppm 2x2colorBlue\n"
            + "save res/All/2x2colorValue.ppm 2x2colorValue\n"
            + "save res/All/2x2colorIntensity.ppm 2x2colorIntensity\n"
            + "save res/All/2x2colorLuma.ppm 2x2colorLuma\n"
            + "save res/All/2x2colorBlur.ppm 2x2colorBlur\n"
            + "save res/All/2x2colorSharpen.ppm 2x2colorSharpen\n"
            + "save res/All/2x2colorGreyScale.ppm 2x2colorGreyScale\n"
            + "save res/All/2x2colorSepia-tone.ppm 2x2colorSepia\n"
            + "q");
    IModel model = new ImageModel();
    IController controller = new Controller(model, input);
    controller.runProgram();

    int[][] newImage = utils.ImageUtil.readImage("res/All/2x2colorBrighten.ppm");

    assertEquals(2, newImage[0][0]);
    assertEquals(2, newImage[0][1]);
    assertEquals(255, newImage[0][2]);
  }

  /**
   * Test the image given from the class.
   */
  @Test
  public void testClassImage() {
    Readable input = new StringReader("load res/class/class.png class\n"
            + "blur class classBlur\n"
            + "blur classBlur classBlur+\n"
            + "sharpen class classSharpen\n"
            + "sharpen classSharpen classSharpen+\n"
            + "greyscale class classGreyScale\n"
            + "sepia-tone class classSepiaTone\n"
            + "save res/class/classBlur.png classBlur\n"
            + "save res/class/classBlur+.png classBlur+\n"
            + "save res/class/classSharpen.png classSharpen\n"
            + "save res/class/classSharpen+.png classSharpen+\n"
            + "save res/class/classGreyScale.png classGreyScale\n"
            + "save res/class/classSepiaTone.png classSepiaTone\n"
            + "save res/class/classJPG.jpg class\n"
            + "save res/class/classJPEG.jpeg class\n"
            + "save res/class/classBMP.bmp class\n"
            + "save res/class/classPPM.ppm class\n"
            + "q");
    IModel model = new ImageModel();
    IController controller = new Controller(model, input);
    controller.runProgram();

    int[][] newImage = utils.ImageUtil.readImage("res/class/classBMP.bmp");
    assertEquals(500, newImage[0][0]);
    assertEquals(200, newImage[0][1]);
  }

  /**
   * Test image from online.
   */
  @Test
  public void testOtherIMage() {
    Readable input = new StringReader("load res/other/whatsapp.png fox\n"
            + "blur fox foxBlur\n"
            + "blur foxBlur foxBlur+\n"
            + "sharpen fox foxSharpen\n"
            + "sharpen foxSharpen foxSharpen+\n"
            + "greyscale fox foxGreyScale\n"
            + "sepia-tone fox foxSepiaTone\n"
            + "save res/other/whatsAppBlur.png foxBlur\n"
            + "save res/other/whatsAppBlur+.png foxBlur+\n"
            + "save res/other/whatsAppSharpen.png foxSharpen\n"
            + "save res/other/whatsAppSharpen+.png foxSharpen+\n"
            + "save res/other/whatsAppGreyScale.png foxGreyScale\n"
            + "save res/other/whatsAppSepiaTone.png foxSepiaTone\n"
            + "save res/other/whatsAppBMP.bmp fox\n"
            + "save res/other/whatsAppJPEG.jpeg fox\n"
            + "save res/other/whatsAppJPG.jpg fox\n"
            + "save res/other/whatsAppPPM.ppm fox\n"
            + "q");

    IModel model = new ImageModel();
    IController controller = new Controller(model, input);
    controller.runProgram();

    int[][] newImage = utils.ImageUtil.readImage("res/other/whatsAppPPM.ppm");

    assertEquals(512, newImage[0][0]);
    assertEquals(512, newImage[0][1]);
  }
}