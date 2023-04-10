package controller;

import java.util.ArrayList;

import model.IModel;

/**
 * Interface ICommand represent a command.
 * It accepts instance of {@link model.IModel model},{@link String old} and {@link String dest}
 * to execute command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public interface ICommand {

  /**
   * Execution method of ICommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  void run(IModel model, ArrayList<String> params) throws QuitException;
}
