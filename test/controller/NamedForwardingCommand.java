package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.IModel;

/**
 * Class NamedForwardingCommand represent a command that designed to output
 * names and parameter received from controller.
 * It is test-only.
 * It extends {@link AbstractCommand}.
 * It accepts instance of {@link String}, {@link String},
 * and {@link Appendable} to initialize class * and produce log to be out.
 * It accepts{@link IModel}, {@link String}, {@link String} to run
 * {@link NamedForwardingCommand#run(IModel, ArrayList)}.
 * If it meets a command of quit, then throw {@link QuitException} to
 * inform {@link controller.IController}.
 */
public class NamedForwardingCommand extends AbstractCommand {
  private final String value;
  private final String name;

  private final Appendable out;

  /**
   * Constructor of NamedForwardingCommand, which takes two parameters {@link String name}
   * and {@link Appendable output}.
   *
   * @param name   represent the name of commands that is being called.
   * @param output represent designated source for storing log.
   * @throws IllegalArgumentException when {@link String value} given is not numeric.
   * @throws QuitException            when any parameter taken is command of quit
   */
  public NamedForwardingCommand(String name, Appendable output)
          throws IllegalArgumentException, QuitException {
    if (QuitException.isQuitException(name)) {
      throw new QuitException();
    }

    this.name = name;
    this.value = "";
    this.out = output;
  }

  /**
   * Constructor of NamedForwardingCommand, which takes in three parameters {@link String name},
   * {@link String value} and {@link Appendable output}.
   * {@link String value} can not be non-numeric.
   *
   * @param name   represent the name of commands that is being called.
   * @param value  represent the value given to specifying operation if needed.
   * @param output represent designated source for storing log.
   * @throws IllegalArgumentException when {@link String value} given is not numeric.
   * @throws QuitException            when any parameter taken is command of quit
   */
  public NamedForwardingCommand(String name, String value, Appendable output)
          throws IllegalArgumentException, QuitException {

    if (QuitException.isQuitException(value) || QuitException.isQuitException(name)) {
      throw new QuitException();
    }

    try {
      Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Value is not number.Not valid!");
    }

    this.value = value;
    this.name = name;
    this.out = output;
  }
//
//  /**
//   * Execution method of NamedForwardingCommand.
//   *
//   * @param model is the instance of {@link IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filename executed on.
//   * @param dest  is the instance of {@link String} representing filename of output.
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String dest) throws QuitException {
//    if (QuitException.isQuitException(old) || QuitException.isQuitException(dest)) {
//      throw new QuitException();
//    }
//    if (this.value.equals("")) {
//      try {
//        this.out.append(this.name).append(" ").append(old).append(" ").append(dest).append("\n");
//      } catch (IOException e) {
//        throw new IllegalArgumentException();
//      }
//    } else {
//      try {
//        this.out.append(this.name).append(" ").append(this.value).append(" ").
//                append(old).append(" ").append(dest).append("\n");
//      } catch (IOException e) {
//        throw new IllegalArgumentException();
//      }
//    }
//  }
//
//  /**
//   * Execution method of NamedForwardingCommand.
//   *
//   * @param model is the instance of {@link IModel} to accept commands
//   * @param old   is the instance of {@link String} representing filename executed on.
//   * @param mask   is the instance of {@link String} representing mask image name.
//   * @param dest  is the instance of {@link String} representing filename of output.
//   * @throws QuitException when any parameter taken is command of quit
//   */
//  @Override
//  public void run(IModel model, String old, String mask, String dest) throws QuitException {
//    if (QuitException.isQuitException(old) || QuitException.isQuitException(dest)
//            || QuitException.isQuitException(mask)) {
//      throw new QuitException();
//    }
//    if (this.value.equals("")) {
//      try {
//        this.out.append(this.name).append(" ").append(old).append(" ").append(dest).append("\n");
//      } catch (IOException e) {
//        throw new IllegalArgumentException();
//      }
//    } else {
//      try {
//        this.out.append(this.name).append(" ").append(this.value).append(" ").
//                append(old).append(" ").append(dest).append("\n");
//      } catch (IOException e) {
//        throw new IllegalArgumentException();
//      }
//    }
//  }

  /**
   * Execution method of NamedForwardingCommand.
   *
   * @param model is the instance of {@link IModel} to accept commands
   * @param params represent params needed for running command
   * @throws QuitException when any parameter taken is command of quit
   */
  @Override
  public void run(IModel model, ArrayList<String> params) throws QuitException {
//    super.run(model,params);
    if (this.value.equals("")) {
      try {
        this.out.append(this.name).append(" ").append(params.get(0)).append(" ").append(params.get(1)).append("\n");
      } catch (IOException e) {
        throw new IllegalArgumentException();
      }
    } else {
      try {
        this.out.append(this.name).append(" ").append(this.value).append(" ").
                append(params.get(0)).append(" ").append(params.get(1)).append("\n");
      } catch (IOException e) {
        throw new IllegalArgumentException();
      }
    }
  }

}
