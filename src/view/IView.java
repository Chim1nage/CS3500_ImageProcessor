package view;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * View Interface that has all methods to display a model.
 */
public interface IView {

  /**
   * Initialize the view screen after configuration.
   */
  void initialize();

  /**
   * adds the given listener to an objects.
   *
   * @param listener the listener that will be added
   */
  void addListener(IOwnListener listener);

  /**
   * adds functional button list as a hashmap to View.
   *
   * @param buttonWithParamMap button list as hashMap.
   */
  void addButtons(Map<String, Integer> buttonWithParamMap);

  /**
   * set input dialogue visible.
   * @param buttonName the name of button trigger this event.
   * @return input as String.
   */
  String setInputDisplayVisible(String buttonName);

  /**
   * set load dialogue visible.
   * @return load location as String.
   */
  String setLoadDisplayVisible();

  /**
   * set save dialogue visible.
   * @return save location as String.
   */
  String setSaveDisplayVisible();

  /**
   * set alert dialogue visible.
   */
  void alert(String s);

  /**
   * Refreshes the program.
   */
  void refresh(BufferedImage image, Map<String, Map<Integer, Integer>> histogramMap);

  /**
   * Refreshes the preview area.
   */
  void refreshPreview(BufferedImage image);

  /**
   * Clear the preview area.
   */
  void clearPreview();

  /**
   * set preview location.
   */
  void setPreviewLocation(int x, int y);

  /**
   * Close the program.
   */
  void close();
}
