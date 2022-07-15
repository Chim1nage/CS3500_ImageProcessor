package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents the brighten command for modifying the brightness of an image.
 *
 * <p>Invariant: inputs and view are not null.
 */
public final class BrightenCommand extends ParentCommand {
  private final int increment;

  /**
   * Initializes this command with the provided parameters and the view to transmit messages to.
   *
   * @param inputs the parameter array from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or if the brightness increment was not found or if a
   *                                  parameter is null
   */
  public BrightenCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);

    if (inputs.length == 4 || inputs.length == 5) {
      try {
        this.increment = Integer.parseInt(inputs[1]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Increment amount could not be found");
      }
    } else {
      throw new IllegalArgumentException("Invalid parameters");
    }

  }

  /**
   * Executes brighten command on the model.
   *
   * @param model the represents an image processor, to be executed upon.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    super.execute(model);
    try {
      if (inputs.length == 4) {
        model.brighten(this.increment, this.inputs[2], this.inputs[3]);
      } else if (inputs.length == 5) {
        model.brighten(this.increment, this.inputs[2], this.inputs[3], inputs[4]);
      }

      if (this.increment > 0) {
        tryTransmit("Image was brightened by factor of: " + this.increment + ".");
      }
      if (this.increment < 0) {
        tryTransmit("Image was darkened by factor of: " + this.increment + ".");
      }
      if (this.increment == 0) {
        tryTransmit("Increment did not affect image.");
      }
    } catch (IllegalArgumentException e) {
      tryTransmit(e.getMessage());
    }
  }


  /**
   * Verifies that the command received the correct number of parameters.
   *
   * @param inputs the user input.
   * @throws IllegalArgumentException if the number of parameters is not 4.
   */
  @Override
  protected void checkSize(String[] inputs) throws IllegalArgumentException {
    if (inputs != null && inputs.length != 4 && inputs.length != 5) {
      throw new IllegalArgumentException("Brighten expects 4 or 5 parameters, got "
              + inputs.length);
    }
  }

}

