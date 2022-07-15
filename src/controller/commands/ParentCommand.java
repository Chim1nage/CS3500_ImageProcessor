package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents the common functionality between commands.
 *
 * <p>Invariant: inputs and view are not null
 */
public class ParentCommand implements IPhotoCommand {
  protected IView view;
  protected String[] inputs;

  /**
   * Initializes this command with the split command String and the view to transmit messages to.
   *
   * @param view the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or a parameter is null
   */
  protected ParentCommand(IView view) throws IllegalArgumentException {
    this.view = view;
  }

  /**
   * Initializes this command with the split command String and the view to transmit messages to.
   *
   * @param inputs the split command string from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or a parameter is null
   */
  protected ParentCommand(String[] inputs, IView view) throws IllegalArgumentException {
    checkNull(inputs, view);
    this.checkSize(inputs);
    this.inputs = inputs;
    this.view = view;
  }

  /**
   * Executes the corresponding command on the supplied model.
   *
   * @param model the represents an image processor, to be executed upon.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Supplied model to execute on is null.");
    }
  }

  /**
   * Attempt transmitting a message to the view.
   *
   * @param message the desired message
   * @throws IllegalArgumentException if the message rendering is not successful
   */
  protected void tryTransmit(String message) throws IllegalArgumentException {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Unable to transmit message.");
    }
  }

  /**
   * Verifies that neither command parameter is null.
   *
   * @param inputs the inputs parameter to check for null
   * @param view   the view parameter to check for null
   * @throws IllegalArgumentException if either parameter is null
   */
  private void checkNull(String[] inputs, IView view) throws IllegalArgumentException {
    if (inputs == null || view == null) {
      throw new IllegalArgumentException("Invalid command arguments");
    }
  }

  /**
   * Verifies that the command received the correct number of parameters.
   *
   * @param inputs the user input
   * @throws IllegalArgumentException if the number of parameters is not 3
   */
  protected void checkSize(String[] inputs) throws IllegalArgumentException {
    if (inputs.length > 4) {
      throw new IllegalArgumentException("Expected 3 or 4 parameters for this command, got "
              + inputs.length);
    }
  }

}

