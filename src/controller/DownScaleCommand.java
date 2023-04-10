package controller;

import java.util.ArrayList;

import model.IModel;
import model.functions.Downsize;
import model.functions.flip.HorizontalFlip;
import model.functions.flip.VerticalFlip;

/**
 * Class DownScaleCommand represent a command that uses
 * {@link Downsize#Downsize(int, int, ArrayList)}.
 * It extends {@link controller.AbstractCommand}.
 * It executes command over {@link model.IModel model}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 * This is designed in controller pattern.
 */
public class DownScaleCommand extends AbstractCommand {
  private final int width;
  private final int height;

  /**
   * Constructor of DownScaleCommand, which takes in two parameter {@link Integer width}
   * and {@link Integer height}. And they can not be non-numeric.
   *
   * @param width represent the width of aimed size.
   * @param height represent the height of aimed size.
   * @throws IllegalArgumentException when {@link Integer width} or {@link Integer height}
   *                                  given is not numeric.
   * @throws QuitException            when any parameter taken is smaller than 0
   */
  public DownScaleCommand(String width, String height) {
    try {
      this.width = Integer.parseInt(width);
      this.height = Integer.parseInt(height);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("width or height is not number.Not valid for brighten!");
    }
    if (this.width < 0 || this.height < 0) {
      throw new QuitException();
    }
  }

//  @Override
//  public void run(IModel model, String old, String dest) throws QuitException {
//    if (QuitException.isQuitException(old) || QuitException.isQuitException(dest)) {
//      throw new QuitException();
//    }
//    model.apply(new Downsize(old, dest, this.width, this.height));
//  }
//
//  @Override
//  public void run(IModel model, String old, String mask, String dest) throws QuitException {
//    throw new IllegalArgumentException("Not supposed to work");
//  }

  /**
   * Execution method of DownScaleCommand.
   *
   * @param model is the instance of {@link model.IModel} to accept commands
   * @param params represents all string needed for run command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException{
    model.apply(new Downsize(this.width,this.height,params));
  }
}
