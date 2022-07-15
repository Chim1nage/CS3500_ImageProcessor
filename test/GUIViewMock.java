import java.io.IOException;
import java.util.Map;

import javax.swing.DefaultListModel;

import controller.IFeaturesController;
import model.util.GeneralImage;
import view.IGUIView;

/**
 * Mock used to confirm inputs sent to the GUI view.
 */
public final class GUIViewMock implements IGUIView {
  private Appendable log;

  /**
   * Initialize this GUI View confirm inputs mock.
   *
   * @param log of inputs sent the view from the controller.
   */
  public GUIViewMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void addFeatures(IFeaturesController features) {
    try {
      this.log.append("Features added.\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs that features were added" +
              " to GUI.");
    }
  }

  @Override
  public void addImage(String destName, Map<Integer, int[]> frequencies, GeneralImage toSave) {
    try {
      this.log.append("Image added. Dest Name: " + destName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm that image was added to GUI.");
    }
  }

  @Override
  public void updateImage(String imageName, Map<Integer, int[]> frequencies, GeneralImage toSave) {
    try {
      this.log.append("Update Image. Image Name: " + imageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to the view to update" +
              " the current image displayed.");
    }
  }

  @Override
  public DefaultListModel<String> getDataForImageList() {
    try {
      this.log.append("Data for image list of images was retrieved.\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to get data for image list from the GUI.");
    }
    return null;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.log.append("Render Message: " + message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to confirm that message sent to the view" +
              " was rendered.");
    }
  }

}
