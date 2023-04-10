package view;

import controller.ControllerMVC;
import model.IModel;

/**
 * ControllerMVCSubClass is designed for test only.
 */
public class ControllerMVCSubClass extends ControllerMVC {
  private IOwnListener listener;

  /**
   * Constructor for controller for the GUI.
   *
   * @param model model used
   * @param view  GUI view
   * @throws IllegalArgumentException if view or model is null
   */
  public ControllerMVCSubClass(IModel model, IView view)
          throws IllegalArgumentException {
    super(model, view);
  }

  /**
   * test only runProgram method expose the listener.
   */
  @Override
  public void runProgram() {
    super.runProgram();
    super.view.addListener(this.listener);
  }

  public IOwnListener assignListener() {
    this.listener = new OwnListener(super.actions, super.actionMap, super.view, super.model);
    return this.listener;
  }
}
