package view;

/**
 * A Blank View which is has no function other than as a placeholder in a controller delegate
 * that is used for the GUI.
 */
public class BlankView implements IView {

  /**
   * Render message which serves no purpose.
   *
   * @param message to be transmitted
   * @throws IllegalStateException never
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    return;
  }

}
