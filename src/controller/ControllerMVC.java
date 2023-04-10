package controller;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import model.IModel;
import view.IView;
import view.OwnListener;

/**
 * Controller for the GUI that extends the original controller.
 */
public class ControllerMVC extends Controller implements IController {

  protected final Map<String, RunnableAction> actionMap;
  protected final IView view;
  protected final Stack<RunnableAction> actions;

  /**
   * Constructor for controller for the GUI.
   *
   * @param model model used
   * @param view  GUI view
   * @throws IllegalArgumentException if view or model is null
   */
  public ControllerMVC(IModel model, IView view) throws IllegalArgumentException {
    super(model, new StringReader(""));
    if (view == null) {
      throw new IllegalArgumentException("something is NULL");
    }
    this.view = view;
    this.actions = new Stack<>();
    this.actionMap = new HashMap<>();
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
    try {
      super.runProgram();
      this.configureActionMap();
      this.view.addListener(new OwnListener(this.actions, this.actionMap, this.view, this.model));
      this.view.addButtons(this.configureActionsWithParam());
      this.view.initialize();
    } catch (IllegalStateException e) {
      this.view.alert("Something went wrong in image processor. Restart!");
    }
  }

  private void configureActionMap() {
    for (String commandName : super.commandMap.keySet()) {
      this.actionMap.put(commandName, new RunnableAction(super.commandMap.get(commandName),
              super.model));
    }
  }

  private Map<String, Integer> configureActionsWithParam() {
    Map<String, Integer> actionsWithParamMap = new HashMap<>();

    for (String commandName : super.commandMap.keySet()) {
      Class<? extends ICommand> commandClass =
              super.commandMap.get(commandName).apply(new Scanner
                      (new StringReader("0 0"))).getClass();
      Constructor[] constructors = commandClass.getConstructors();
      int paramsCount = constructors[0].getParameterCount();
      actionsWithParamMap.put(commandName, paramsCount);
    }

    return actionsWithParamMap;
  }
}
