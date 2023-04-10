package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import controller.filterCommand.BlurCommand;
import controller.filterCommand.SharpenCommand;
import controller.flipCommand.HorizontalFlipCommand;
import controller.flipCommand.VerticalFlipCommand;
import controller.greyScaleCommands.GreyScaleBlueCommand;
import controller.greyScaleCommands.GreyScaleCommand;
import controller.greyScaleCommands.GreyScaleGreenCommand;
import controller.greyScaleCommands.GreyScaleIntensityCommand;
import controller.greyScaleCommands.GreyScaleLumaCommand;
import controller.greyScaleCommands.GreyScaleRedCommand;
import controller.greyScaleCommands.GreyScaleValueCommand;
import model.IModel;

/**
 * Class Controller represent a controller to control what is given.
 * It is an instance of {@link IController}.
 * It accepts {@link model.IModel} and {@link Readable}
 * to run the program, which is designed in controller pattern.
 */
public class Controller implements IController {
  protected final IModel model;
  protected final Map<String, Function<Scanner, ICommand>> commandMap;
  private final Readable input;
  private final Stack<ICommand> commands;

  /**
   * Constructor of Controller. It takes in {@link model.IModel model} and {@link Readable input}.
   * {@link model.IModel model} and {@link Readable input} can not be null.
   *
   * @param model is instances of {@link model.IModel} given to be controlled.
   * @param input is instance of {@link Readable} given to instruct the running of program.
   * @throws IllegalArgumentException when {@link model.IModel model} or {@link Readable input}
   *                                  given is null.
   */
  public Controller(IModel model, Readable input) throws IllegalArgumentException {
    if (model == null || input == null) {
      throw new IllegalArgumentException("something is NULL");
    }
    this.model = model;
    this.input = input;
    this.commands = new Stack<>();
    this.commandMap = new HashMap<>();
  }

  // initialization for known commands. It is designed as helper method to facilitate possible
  // delegation usage.
  void commandsInitialize() {
    commandMap.put("brighten", (Scanner s) -> new BrightenCommand(s.next()));
    commandMap.put("vertical-flip", (Scanner s) -> new VerticalFlipCommand());
    commandMap.put("horizontal-flip", (Scanner s) -> new HorizontalFlipCommand());
    commandMap.put("save", (Scanner s) -> new Save());
    commandMap.put("load", (Scanner s) -> new Load());
    commandMap.put("blue-greyscale", (Scanner s) -> new GreyScaleBlueCommand());
    commandMap.put("red-greyscale", (Scanner s) -> new GreyScaleRedCommand());
    commandMap.put("green-greyscale", (Scanner s) -> new GreyScaleGreenCommand());
    commandMap.put("luma-greyscale", (Scanner s) -> new GreyScaleLumaCommand());
    commandMap.put("intensity-greyscale", (Scanner s) -> new GreyScaleIntensityCommand());
    commandMap.put("value-greyscale", (Scanner s) -> new GreyScaleValueCommand());
    commandMap.put("blur", (Scanner s) -> new BlurCommand());
    commandMap.put("sharpen", (Scanner s) -> new SharpenCommand());
    commandMap.put("greyscale", (Scanner s) -> new GreyScaleCommand());
    commandMap.put("sepia-tone", (Scanner s) -> new SepiaToneCommand());
    commandMap.put("down-scale", (Scanner s) -> new DownScaleCommand(s.next(), s.next()));
  }

  /**
   * runProgram is the method that run the program based on instance of IModel.This method
   * use the {@link Readable input} to decide which command to use. It will continue running
   * util receive "q"/"quit"(ignore case) command or run out of command. It handles any
   * IllegalArgumentException from what it is controlling.
   *
   * @throws IllegalStateException was thrown to handle the situation that
   *                               IllegalArgumentException was thrown by what it is controlling.
   */
  @Override
  public void runProgram() throws IllegalStateException {
    this.commandsInitialize();

    Scanner scanner = new Scanner(this.input);
    String inputHolder = "";

    while (scanner.hasNext() && !QuitException.isQuitException(inputHolder)) {

      ICommand c;
      String in = "";
      if (inputHolder.isEmpty()) {
        in = scanner.next();
      } else {
        in = inputHolder;
        inputHolder = "";
      }

      if (QuitException.isQuitException(in)) {
        return;
      }

      Function<Scanner, ICommand> cmd =
              commandMap.getOrDefault(in.toLowerCase(), null);

      if (cmd == null) {
        throw new IllegalStateException("no such command! try a new one!");
      } else {
        try {
          c = cmd.apply(scanner);
          commands.add(c);

          String param1 = scanner.next();
          if (QuitException.isQuitException(param1)) {
            return;
          }

          String param2 = scanner.next();
          if (QuitException.isQuitException(param2)) {
            return;
          }

          try {
            ArrayList<String> params = new ArrayList<>();
            params.add(param1);
            params.add(param2);
            params.add(null);
            c.run(this.model, params);
          } catch (IllegalArgumentException argumentException){
            ArrayList<String> params = new ArrayList<>();
            params.add(param1);
            params.add(param2);
            c.run(this.model, params);
          } catch (NullPointerException nullPointerException){
            String param3 = scanner.next();
            if (commandMap.containsKey(param3) || QuitException.isQuitException(param3)) {
              inputHolder = param3;
              ArrayList<String> params = new ArrayList<>();
              params.add(param1);
              params.add(param2);
              c.run(this.model, params);
            } else {
              ArrayList<String> params = new ArrayList<>();
              params.add(param1);
              params.add(param2);
              params.add(param3);
              c.run(this.model, params);
            }
          }

        } catch (IllegalArgumentException e) {
          throw new IllegalStateException("something went wrong in running program!");
        } catch (NullPointerException | QuitException e2) {
          return;
        }
      }
    }

  }
}
