package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents the flip command for flipping an image horizontally or vertically.
 *
 * <p>Invariant: inputs and view are not null.
 */
public final class FlipCommand extends ParentCommand {

  /**
   * Initializes this command with the provided parameters and the view to transmit messages to.
   *
   * @param inputs the parameter array from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or if a parameter is null
   */
  public FlipCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);
  }

  /**
   * Executes the flip command on the supplied model.
   *
   * @param model the represents an image processor, to be executed upon.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    super.execute(model);
    String s = this.inputs[0];
    boolean horizontal;

    horizontal = s.charAt(0) == 'h';
    try {
      model.flip(horizontal, this.inputs[1], this.inputs[2]);
      tryTransmit(this.inputs[0] + " applied.");
    } catch (IllegalArgumentException e) {
      tryTransmit(e.getMessage());
    }

  }

}

