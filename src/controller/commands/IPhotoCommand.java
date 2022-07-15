package controller.commands;

import model.IFinalProcessingModel;

/**
 * Used to represent the different commands that can be performed on a photo.
 */
public interface IPhotoCommand {

  /**
   * Executes the command function on the supplied model.
   *
   * @param model represents the model to be executed upon.
   * @throws IllegalArgumentException when the supplied model to the view is null.
   */
  public void execute(IFinalProcessingModel model) throws IllegalArgumentException;
}

