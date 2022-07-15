package view;

import java.io.IOException;

import model.IProcessingModel;
import model.adapter.Adapter;
import model.adapter.IViewModel;

/**
 * A simple view implementation which supports rendering messages to the user.
 *
 * <p>Invariant: the output and model are not null
 */
public final class SimpleView implements IView {
  private final Appendable output;
  private final IViewModel model;

  /**
   * Initializes this view with the model to support displaying images and uses System.out
   * to display information to the user. Adapter is used to prevent exposure of functionality
   * that the view should not have control of.
   *
   * @param model the model to support displaying images.
   * @throws IllegalArgumentException when the supplied model is null.
   */
  public SimpleView(IProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Supplied model is null to default "
              + "System.out view constructor.");
    }
    this.model = new Adapter(model);
    this.output = System.out;
  }

  /**
   * Initializes this view with the model to support displaying images and the Appendable output
   * object to display information to the user. Adapter is used to prevent exposure of functionality
   * * that the view should not have control of.
   *
   * @param model  the model to support displaying images
   * @param output the Appendable object to render messages to the user
   * @throws IllegalArgumentException if either parameter is null
   */
  public SimpleView(IProcessingModel model, Appendable output) throws IllegalArgumentException {
    if (model == null || output == null) {
      throw new IllegalArgumentException("View cannot be initialized with null parameters");
    }
    this.model = new Adapter(model);
    this.output = output;
  }

  /**
   * Tries to append message to the specified data output destination.
   *
   * @param message to be transmitted.
   * @throws IllegalStateException when transmission of message was unsuccessful.
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to render message.");
    }
  }

}

