package controller.greyScaleCommands;

import java.util.ArrayList;

import controller.AbstractCommand;
import controller.QuitException;
import model.IModel;
import model.functions.greyScale.GreyScaleBlue;
import model.functions.greyScale.GreyScaleGreen;


/**
 * Class GreyScaleGreenCommand represent a command that uses
 * {@link GreyScaleGreen#GreyScaleGreen(ArrayList)}.
 * It extends {@link controller.AbstractCommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public class GreyScaleGreenCommand extends AbstractCommand {

//  /**
//   * Execution method of GreyScaleGreenCommand.
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
//    model.apply(new GreyScaleGreen(old, dest));
//  }
//
//  /**
//   * Execution method of GreyScaleGreenCommand.
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
//    model.apply(new GreyScaleGreen(old, mask, dest));
//  }

  /**
   * Execution method of GreyScaleGreenCommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException{
    model.apply(new GreyScaleGreen(params));
  }
}
