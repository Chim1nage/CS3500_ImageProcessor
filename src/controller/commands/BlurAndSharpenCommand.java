package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents the blur and sharpen commands, used to apply filters on images with kernels.
 *
 * <p>Invariant: inputs and view are not null.
 */
public class BlurAndSharpenCommand extends ParentCommand {
  private final String[] inputs;

  /**
   * Initializes this command with the split command String and the view to transmit messages to.
   *
   * @param inputs the split command string from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or a parameter is null
   */
  public BlurAndSharpenCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);
    this.inputs = inputs;
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
      if (inputs[0].equals("blur") && inputs.length == 3) {
        model.blur(inputs[1], inputs[2]);
        tryTransmit("Image Blurred.");
      } else if (inputs[0].equals("blur") && inputs.length == 4) {
        model.blur(inputs[1], inputs[2], inputs[3]);
        tryTransmit("Partial Blur applied.");
      } else if (inputs[0].equals("sharpen") && inputs.length == 3) {
        model.sharpen(inputs[1], inputs[2]);
        tryTransmit("Image Sharpened.");
      } else if (inputs[0].equals("sharpen") && inputs.length == 4) {
        model.sharpen(inputs[1], inputs[2], inputs [3]);
        tryTransmit("Partial Sharpen applied.");
      }
    } catch (IllegalArgumentException e) {
      tryTransmit(e.getMessage());
    }
  }
}