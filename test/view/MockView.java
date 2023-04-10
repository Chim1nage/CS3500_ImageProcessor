package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JFrame;

/**
 * class MockView is designed for test transmit message.
 * It implements {@link IView}.
 */
public class MockView extends JFrame implements IView {

  private final Appendable output;

  /**
   * Constructor of MockView.
   *
   * @param output is the designated source of output.
   */
  public MockView(Appendable output) {
    this.output = Objects.requireNonNull(output);
  }

  /**
   * transmit message of view has been initialized.
   */
  @Override
  public void initialize() {
    try {
      this.output.append("view has been initialized successfully\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }

  /**
   * transmit message of listener has been added to view.
   *
   * @param listener the listener that will be added
   */
  @Override
  public void addListener(IOwnListener listener) {
    try {
      this.output.append("a new listener was added\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }

  /**
   * transmit what button has been initialized.
   *
   * @param buttonWithParamMap the Hashmap containing information about the buttons
   */
  @Override
  public void addButtons(Map<String, Integer> buttonWithParamMap) {
    try {
      for (String buttonName : buttonWithParamMap.keySet()) {
        this.output.append(buttonName).append(" with ")
                .append(Integer.toString(buttonWithParamMap.get(buttonName)))
                .append(" parameters\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }

  /**
   * transmit message of what button was triggered.
   *
   * @param buttonName the name of functional button that need input
   * @return input that is provided.
   */
  @Override
  public String setInputDisplayVisible(String buttonName) {
    try {
      this.output.append(buttonName).append(" button was triggered\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
    return "";
  }

  /**
   * transmit message of load button was triggered.
   *
   * @return the location of file to be loaded.
   */
  @Override
  public String setLoadDisplayVisible() {
    try {
      this.output.append("load button was triggered\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
    return "";
  }

  /**
   * transmit message of save button was triggered.
   *
   * @return the location of file to be saved.
   */
  @Override
  public String setSaveDisplayVisible() {
    try {
      this.output.append("save button was triggered\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
    return "";
  }

  /**
   * transmit message of alert received.
   *
   * @param s is the alert message.
   */
  @Override
  public void alert(String s) {
    try {
      this.output.append("alert message is ").append(s).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }

  /**
   * transmit message of refresh triggered.
   */
  @Override
  public void refresh(BufferedImage image, Map<String, Map<Integer, Integer>> histogramMap) {
    try {
      this.output.append("refresh was triggered\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }

  /**
   * Refreshes the preview area.
   *
   * @param image
   */
  @Override
  public void refreshPreview(BufferedImage image) {

  }

  /**
   * Clear the preview area.
   */
  @Override
  public void clearPreview() {

  }

  /**
   * set preview location.
   *
   * @param x
   * @param y
   */
  @Override
  public void setPreviewLocation(int x, int y) {

  }

  /**
   * transmit message of automatically close triggered.
   */
  @Override
  public void close() {
    try {
      this.output.append("automatically close was triggered\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException met in MockView");
    }
  }
}
