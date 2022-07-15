package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Downscale command used by controller.
 */
public class DownScaleCommand extends ParentCommand {
  private double widthScaleFactor;
  private double heightScaleFactor;

  /**
   * Initializes this command with the split command String and the view to transmit messages to.
   *
   * @param inputs the split command string from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or a parameter is null
   */
  public DownScaleCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);
    if (inputs.length == 5) {
      try {
        this.widthScaleFactor = Double.valueOf(inputs[3]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Width scale factor invalid.");
      }
      try {
        this.heightScaleFactor = Double.valueOf(inputs[4]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Height scale factor invalid.");
      }
    } else {
      throw new IllegalArgumentException("Invalid parameters");
    }
  }

  /**
   * Executes the appropriate filtering method on the model.
   *
   * @param model represents an image processor, to be executed upon
   * @throws IllegalArgumentException if the model is null
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    super.execute(model);
    try {
      model.downScale(inputs[1], inputs[2], this.widthScaleFactor, this.heightScaleFactor);
      tryTransmit("Down scale applied to image. Width Scale Factor: " + widthScaleFactor + "." +
              " Height Scale Factor: " + heightScaleFactor + ".");
    } catch (IllegalArgumentException e) {
      tryTransmit("Unable to downscale image.");
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
    if (inputs != null && inputs.length != 5) {
      throw new IllegalArgumentException("Downscale Command expects 5 parameters, got "
              + inputs.length);
    }
  }

}
