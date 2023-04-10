package controller;

/**
 * Interface IController represent a controller to control what is given.
 * It accepts instance of {@link model.IModel} and {@link Readable} to run the program,
 * which is designed in controller pattern.
 */
public interface IController {

  /**
   * runProgram is the method that run the program based on instance of IModel.This method
   * use the {@link Readable input} to decide which command to use. It will continue running
   * util receive "q"/"quit"(ignore case) command or run out of command. It handles any
   * IllegalArgumentException from what it is controlling.
   *
   * @throws IllegalStateException was thrown to handle the situation that
   *                               IllegalArgumentException was thrown by what it is controlling.
   */
  void runProgram() throws IllegalStateException;
}
