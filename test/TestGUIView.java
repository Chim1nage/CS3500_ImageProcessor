import org.junit.Test;

import java.util.HashMap;

import controller.GUIController;
import controller.IFeaturesController;
import model.FinalProcessingModel;
import model.IFinalProcessingModel;
import model.util.GeneralImage;
import model.util.Pixel;
import view.IGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Test the inputs sent the GUI view from the controller.
 */
public final class TestGUIView {

  @Test
  public void testGUIView() {
    Appendable confirmInputs = new StringBuilder();
    IGUIView view = new GUIViewMock(confirmInputs);
    IFinalProcessingModel model = new FinalProcessingModel();
    IFeaturesController controller = new GUIController(model);
    Pixel[][] pxiels = new Pixel[1][1];
    pxiels[0][0] = new Pixel(255, 120, 100);

    Pixel[][] pixels = new Pixel[1][1];
    pixels[0][0] = new Pixel(10, 10, 10);
    model.addImage("image",
            new GeneralImage(pixels, 1, 1, 255));
    assertEquals("", confirmInputs.toString());

    view.renderMessage("Test rending a message to the view.");
    assertEquals("Render Message: Test rending a message to the view.\n",
            confirmInputs.toString());

    view.addFeatures(controller);
    assertEquals("Render Message: Test rending a message to the view.\n" +
            "Features added.\n", confirmInputs.toString());

    view.getDataForImageList();
    assertEquals("Render Message: Test rending a message to the view.\n" +
            "Features added.\n" +
            "Data for image list of images was retrieved.\n", confirmInputs.toString());

    view.addImage("image", new HashMap<>(),
            new GeneralImage(pxiels, 1, 1, 255));
    assertEquals("Render Message: Test rending a message to the view.\n" +
            "Features added.\n" +
            "Data for image list of images was retrieved.\n" +
            "Image added. Dest Name: image.\n", confirmInputs.toString());

    view.updateImage("image", new HashMap<>(),
            new GeneralImage(pxiels, 1, 1, 255));
    assertEquals("Render Message: Test rending a message to the view.\n" +
            "Features added.\n" +
            "Data for image list of images was retrieved.\n" +
            "Image added. Dest Name: image.\n" +
            "Update Image. Image Name: image.\n", confirmInputs.toString());

    assertEquals("Render Message: Test rending a message to the view.\n" +
            "Features added.\n" +
            "Data for image list of images was retrieved.\n" +
            "Image added. Dest Name: image.\n" +
            "Update Image. Image Name: image.\n", confirmInputs.toString());
  }

}


















