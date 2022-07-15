import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controller.ControllerImpl;
import controller.IController;
import model.FinalProcessingModel;
import model.IFinalProcessingModel;
import model.adapter.Adapter;
import model.util.GeneralImage;
import model.util.Pixel;
import view.IView;
import view.SimpleView;

import static org.junit.Assert.assertEquals;

/**
 * Test the Adapter class that is used to hide model functionality when passed to the view.
 */
public class AdapterTest {

  // test getLoadedImages
  @Test
  public void testAdapter() {
    IController controller;
    IFinalProcessingModel model = new FinalProcessingModel();
    IView view = new SimpleView(model, new StringBuilder());
    controller = new ControllerImpl(model, view, new StringReader("load images/SmallPPM.ppm " +
            "image\nq\n"));
    Adapter adapter = new Adapter(model);
    Map<String, GeneralImage> map = new HashMap<>();
    assertEquals(adapter.getLoadedImages(), model.getLoadedImages());
    assertEquals(map, adapter.getLoadedImages());
    assertEquals(map, model.getLoadedImages());

    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new Pixel(240, 120, 100);
    pixels[0][1] = new Pixel(255, 0, 50);
    pixels[1][0] = new Pixel(240, 120, 150);
    pixels[1][1] = new Pixel(250, 130, 140);
    GeneralImage image1 = new GeneralImage(pixels, 2, 2, 255);
    map.put("image", image1);

    controller.startProcessor();
    assertEquals(adapter.getLoadedImages(), model.getLoadedImages());
    assertEquals(map, adapter.getLoadedImages());
    assertEquals(map, model.getLoadedImages());


    try {
      adapter = new Adapter(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Supplied model is null", e.getMessage());
    }
  }

}
