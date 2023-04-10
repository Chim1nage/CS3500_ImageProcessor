package controller.flipCommand;

import java.util.ArrayList;

import controller.AbstractCommand;
import controller.QuitException;
import model.IModel;
import model.functions.filter.Sharpening;
import model.functions.flip.HorizontalFlip;

/**
 * Class HorizontalFlipCommand represent a command that uses
 * {@link HorizontalFlip#HorizontalFlip(ArrayList)}.
 * It extends {@link controller.AbstractCommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public class HorizontalFlipCommand extends AbstractCommand {

//  /**
//   * Execution method of HorizontalFlipCommand.
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
//    model.apply(new HorizontalFlip(old, dest));
//  }
//
//  /**
//   * Execution method of HorizontalFlipCommand.
//   *
//   * @param model is the instance of {@link model.IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filepath/filename
//   * @param mask   is the instance of {@link String} representing filepath/filename of mask image
//   * @param dest  is the instance of {@link String} representing filepath/filename
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String mask, String dest) throws QuitException{
//    throw new IllegalArgumentException("Not supposed to work");
//  }

  /**
   * Execution method of HorizontalFlipCommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException{
    model.apply(new HorizontalFlip(params));
  }
}
