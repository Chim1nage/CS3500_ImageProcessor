package controller;

import java.util.ArrayList;

import model.IModel;

/**
 * Abstract class AbstractCommand represent a command.
 * It implements {@link controller.ICommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public abstract class AbstractCommand implements ICommand {
//
//  /**
//   * Abstract execution method of AbstractCommand.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filepath/filename
//   * @param dest  is the instance of {@link String} representing filepath/filename
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  public abstract void run(IModel model, String old, String dest) throws QuitException;
//
//  /**
//   * Execution method of AbstractCommand.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filepath/filename
//   * @param mask   is the instance of {@link String} representing filepath/filename of mask image
//   * @param dest  is the instance of {@link String} representing filepath/filename
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  public abstract void run(IModel model, String old, String mask, String dest) throws QuitException;

  /**
   * Execution method of ICommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  public abstract void run(IModel model, ArrayList<String> params) throws QuitException;
}
