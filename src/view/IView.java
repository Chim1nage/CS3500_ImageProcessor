package view;

/**
 * Used to view output produced by the program.
 */
public interface IView {

  /**
   * Tries to append message to the specified data output destination.
   *
   * @param message to be transmitted.
   * @throws IllegalStateException when transmission of message was unsuccessful.
   */
  public void renderMessage(String message) throws IllegalStateException;

}

