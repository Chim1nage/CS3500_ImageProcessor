package view;

import java.util.Map;

import javax.swing.DefaultListModel;

import controller.IFeaturesController;
import model.util.GeneralImage;

/**
 * GUI View interface. Used to add features provided by a supplied controller so that the
 * components of the GUI becomes functional in the program.
 */
public interface IGUIView extends IView {

  /**
   * Initializes action listener functionality of the GUI.
   *
   * @param features of the controller
   */
  public void addFeatures(IFeaturesController features);

  /**
   * Adds an image to this view so that it can display it through the GUI.
   *
   * @param destName    the name of the image
   * @param frequencies the (value,frequency) table used to create a histrogram
   */
  public void addImage(String destName, Map<Integer, int[]> frequencies, GeneralImage toSave);

  /**
   * Updates the view to display the current image.
   *
   * @param imageName   the image to display
   * @param frequencies the (value,frequency) table used to display the histogram
   */
  public void updateImage(String imageName, Map<Integer, int[]> frequencies, GeneralImage toSave);

  /**
   * Creates deep copy of list of images loaded in the program.
   *
   * @return deep copy of list of images loaded in the program
   */
  public DefaultListModel<String> getDataForImageList();

}

