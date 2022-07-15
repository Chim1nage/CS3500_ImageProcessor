package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents color transformations that can be applied to images via a kernel.
 *
 * <p>Invariant: inputs and view are not null.
 */
public class ColorTransformationCommand extends ParentCommand {
  private final String[] inputs;

  /**
   * Initializes this command with the split command String and the view to transmit messages to.
   *
   * @param inputs the split command string from the user
   * @param view   the view to transmit messages to
   * @throws IllegalArgumentException if the input does not match the expected number of parameters
   *                                  or a parameter is null
   */
  public ColorTransformationCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);
    this.inputs = inputs;
  }

  /**
   * Executes the appropriate color transformation method on the model.
   *
   * @param model represents an image processor, to be executed upon
   * @throws IllegalArgumentException if the model is null
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    super.execute(model);
    try {
      if (inputs[0].equals("sepia") && inputs.length == 3) {
        model.sepia(inputs[1], inputs[2]);
        tryTransmit("Sepia applied.");
      } else if (inputs[0].equals("sepia") && inputs.length == 4) {
        model.sepia(inputs[1], inputs[2], inputs[3]);
        tryTransmit("Partial Sepia applied.");
      } else if (inputs[0].equals("greyscale") && inputs.length == 3) {
        model.greyscale(inputs[1], inputs[2]);
        tryTransmit("Greyscale applied.");
      } else if (inputs[0].equals("greyscale") && inputs.length == 4) {
        model.greyscale(inputs[1], inputs[2], inputs [3]);
        tryTransmit("Partial Greyscale applied.");
      }
    } catch (IllegalArgumentException e) {
      tryTransmit(e.getMessage());
    }
  }
}