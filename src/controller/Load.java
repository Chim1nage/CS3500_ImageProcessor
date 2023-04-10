package controller;

import java.util.ArrayList;

import model.IModel;
import model.functions.flip.VerticalFlip;

/**
 * Class Load represent a command that uses
 * {@link model.functions.Load#Load(ArrayList)}.
 * It extends {@link controller.AbstractCommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public class Load extends AbstractCommand {

//  /**
//   * Execution method of Load.
//   *
//   * @param model    is the instance of {@link model.IModel} to accept commands
//   * @param filepath is the instance of {@link String} representing filepath looked for.
//   * @param name     is the instance of {@link String} representing filename of input.
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String filepath, String name) throws QuitException {
//    if (QuitException.isQuitException(filepath) || QuitException.isQuitException(name)) {
//      throw new QuitException();
//    }
//    model.apply(new model.functions.Load(filepath, name));
//  }
//
//  /**
//   * Execution method of Load.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filepath/filename
//   * @param mask  is the instance of {@link String} representing filepath/filename of mask image
//   * @param dest  is the instance of {@link String} representing filepath/filename
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String mask, String dest) throws QuitException {
//    throw new IllegalArgumentException("Not supposed to work");
//  }

  /**
   * Execution method of Load.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException{
    model.apply(new model.functions.Load(params));
  }
}
