package controller;

import java.util.Scanner;

import model.IModel;

/**
 * Class Controller represent a controller that is subclass of {@link Controller}, which provides
 * modified {@link Controller#commandsInitialize()} to replace original commands. With modified
 * {@link Controller#commandsInitialize()} and {@link Appendable output}, this controller is able
 * to log the user input and transmit the log to designated source.
 * It is an instance of {@link IController}.
 * It extends {@link Controller}.
 * It accepts {@link model.IModel}, {@link Readable} and {@link Appendable}.
 */
public class ControllerSubclass extends Controller {
  private final Appendable output;

  /**
   * Constructor of ControllerSubclass. It takes in {@link IModel model}, {@link Readable input}
   * and {@link Appendable output}.
   * {@link IModel model}, {@link Readable input} and {@link Appendable output} can not be null.
   *
   * @param model  is instances of {@link IModel} given to be controlled.
   * @param input  is instance of {@link Readable} given to instruct the running of program.
   * @param output is instance of {@link Appendable} given store the log.
   * @throws IllegalArgumentException when any of {@link IModel model}, {@link Readable input}
   *                                  or {@link Appendable output} given is null.
   */
  public ControllerSubclass(IModel model, Readable input, Appendable output)
          throws IllegalArgumentException {
    super(model, input);

    if (output == null) {
      throw new IllegalArgumentException();
    }

    this.output = output;
  }

  @Override
  protected void commandsInitialize() {
    super.commandsInitialize();
    super.commandMap.put("brighten", (Scanner s) ->
            new NamedForwardingCommand("brighten", s.next(), this.output));
    super.commandMap.put("vertical-flip", (Scanner s) ->
            new NamedForwardingCommand("vertical-flip", this.output));
    super.commandMap.put("horizontal-flip", (Scanner s) ->
            new NamedForwardingCommand("horizontal-flip", this.output));
    super.commandMap.put("save", (Scanner s) ->
            new NamedForwardingCommand("save", this.output));
    super.commandMap.put("load", (Scanner s) ->
            new NamedForwardingCommand("load", this.output));
    super.commandMap.put("blue-greyscale", (Scanner s) ->
            new NamedForwardingCommand("blue-greyscale", this.output));
    super.commandMap.put("red-greyscale", (Scanner s) ->
            new NamedForwardingCommand("red-greyscale", this.output));
    super.commandMap.put("green-greyscale", (Scanner s) ->
            new NamedForwardingCommand("green-greyscale", this.output));
    super.commandMap.put("luma-greyscale", (Scanner s) ->
            new NamedForwardingCommand("luma-greyscale", this.output));
    super.commandMap.put("intensity-greyscale", (Scanner s) ->
            new NamedForwardingCommand("intensity-greyscale", this.output));
    super.commandMap.put("value-greyscale", (Scanner s) ->
            new NamedForwardingCommand("value-greyscale", this.output));
    super.commandMap.put("blur", (Scanner s) ->
            new NamedForwardingCommand("blur", this.output));
    super.commandMap.put("sharpen", (Scanner s) ->
            new NamedForwardingCommand("sharpen", this.output));
    super.commandMap.put("greyscale", (Scanner s) ->
            new NamedForwardingCommand("greyscale", this.output));
    super.commandMap.put("sepia-tone", (Scanner s) ->
            new NamedForwardingCommand("sepia-tone", this.output));
  }
}
