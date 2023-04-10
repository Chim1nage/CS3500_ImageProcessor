package controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import model.IModel;

/**
 * class RunnableAction implements {@link Runnable}.
 * It provides functionality of construct runnable section controller by
 * {@link IController#runProgram()} and {@link view.IOwnListener}.
 */
public class RunnableAction implements Runnable {

  private final Function<Scanner, ICommand> function;
  private final IModel model;
  private Scanner scanner;
  private boolean configure;

  /**
   * Constructor of RunnableAction.
   * @param f constructor function for {@link ICommand}.
   * @param model represent model to be controlled.
   */
  public RunnableAction(Function<Scanner, ICommand> f, IModel model) {
    this.function = f;
    this.model = model;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used
   * to create a thread, starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * The general contract of the method <code>run</code> is that it may
   * take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    ICommand c = this.function.apply(this.scanner);
    if (this.configure){
      ArrayList<String> params = new ArrayList<>();
      params.add(scanner.next());
      params.add(scanner.next());
      params.add(scanner.next());
      c.run(this.model, params);
    } else {
      ArrayList<String> params = new ArrayList<>();
      params.add(scanner.next());
      params.add(scanner.next());
      c.run(this.model, params);
    }
  }

  /**
   * setScanner method set the scanner object for runnable section.
   * @param s represent {@link Scanner}.
   */
  public void setScanner(Scanner s) {
    this.scanner = s;
  }

  /**
   * setRunConfigure method set the configure option for runnable.
   * @param configure determine how to configure.
   */
  public void setRunConfigure(boolean configure){
    this.configure = configure;
  }
}
