package controller.commands;

import model.IFinalProcessingModel;
import view.IView;

/**
 * Represents the greyscale command for representing an image as some greyscale.
 *
 * <p>Invariant: inputs and view are not null.
 */
public final class GreyscaleCommand extends ParentCommand {
  private final String[] inputs;

  /**
   * Initializes this greyscale command with some user parameters and the view to transmit messages
   * to.
   *
   * @param inputs the user input String array
   * @param view   the view to transmit message to
   * @throws IllegalArgumentException if either parameter is null
   */
  public GreyscaleCommand(String[] inputs, IView view) throws IllegalArgumentException {
    super(inputs, view);
    this.inputs = inputs;
  }

  /**
   * Executes grey scale supplied on the supplied model (specified type).
   *
   * @param model the represents an image processor, to be executed upon.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  @Override
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException {
    super.execute(model);
    IFinalProcessingModel.GreyscaleType type = null;

    switch (this.inputs[0]) {
      case "red-component":
        type = IFinalProcessingModel.GreyscaleType.Red;
        break;
      case "green-component":
        type = IFinalProcessingModel.GreyscaleType.Green;
        break;
      case "blue-component":
        type = IFinalProcessingModel.GreyscaleType.Blue;
        break;
      case "value-component":
        type = IFinalProcessingModel.GreyscaleType.Value;
        break;
      case "luma-component":
        type = IFinalProcessingModel.GreyscaleType.Luma;
        break;
      case "intensity-component":
        type = IFinalProcessingModel.GreyscaleType.Intensity;
        break;
      default:
        break;
    }

    try {
      if (inputs.length == 3) {
        model.componentGreyScale(type, this.inputs[1], this.inputs[2]);
        tryTransmit(inputs[0] + " visualization applied.");
      } else if (inputs.length == 4) {
        model.componentGreyScale(type, this.inputs[1], this.inputs[2], this.inputs[3]);
        tryTransmit("Partial " + inputs[0] + " visualization applied.");
      }

    } catch (IllegalArgumentException e) {
      tryTransmit(e.getMessage());
    }
  }

}

