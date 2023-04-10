package controller;

import java.util.ArrayList;

import model.IModel;
import model.functions.Brighten;

/**
 * Class BrightenCommand represent a command that uses
 * {@link model.functions.Brighten#Brighten(int, ArrayList)}
 * It extends {@link controller.AbstractCommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public class BrightenCommand extends AbstractCommand {
  private final int value;

  /**
   * Constructor of BrightenCommand, which takes in one parameter {@link String value}.
   * {@link String value} can not be non-numeric.
   *
   * @param value represent the value given to {@link IModel model}
   * @throws IllegalArgumentException when {@link String value} given is not numeric.
   * @throws QuitException            when any parameter taken is command of quit
   */
  public BrightenCommand(String value) throws IllegalArgumentException, QuitException {

    if (QuitException.isQuitException(value)) {
      throw new QuitException();
    }

    int number;
    try {
      number = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Value is not number.Not valid for brighten!");
    }
    this.value = number;
  }

//  /**
//   * Execution method of BrightenCommand.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filename executed on.
//   * @param dest  is the instance of {@link String} representing filename of output.
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String dest) throws QuitException {
//    if (QuitException.isQuitException(old) || QuitException.isQuitException(dest)) {
//      throw new QuitException();
//    }
//    model.apply(new Brighten(this.value, old, dest));
//  }
//
//  /**
//   * Execution method of BrightenCommand.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filepath/filename
//   * @param mask   is the instance of {@link String} representing filepath/filename of mask image
//   * @param dest  is the instance of {@link String} representing filepath/filename
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String mask, String dest) throws QuitException{
//    if (QuitException.isQuitException(old) || QuitException.isQuitException(dest)
//            || QuitException.isQuitException(mask)) {
//      throw new QuitException();
//    }
//
//    model.apply(new Brighten(this.value, old, mask, dest));
//  }

  /**
   * Execution method of BrightenCommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException{
    model.apply(new Brighten(this.value, params));
  }
}
