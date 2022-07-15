import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ControllerImpl;
import controller.GUIController;
import controller.IFeaturesController;
import controller.IController;
import model.FinalProcessingModel;
import model.IFinalProcessingModel;
import view.IGUIView;
import view.IView;
import view.JFrameView;
import view.SimpleView;

/**
 * Accepts command line arguments or input from "System.in". Command line arguments are
 * accepted are in the format of ".txt" files where you enter "-file nameOfScript.txt".
 * If you want to use the text based version of the program, pass into the command line
 * argument "-text". If none are supplied, the user will then be prompoted to ue the GUI version
 * of the program.
 */
public final class Main {

  /**
   * Used to run the program and use the command line arguments from the file or input from the
   * user.
   *
   * @param args used to run the program. Needs to be supplied as a ".txt" file. If none supplied,
   *             input is taken directly from user keyboard typing input.
   */
  public static void main(String[] args) {
    StringBuilder builder = new StringBuilder();
    IFinalProcessingModel model = new FinalProcessingModel();
    IView simpleView;
    IGUIView guiView;
    IFeaturesController guiController;
    IController controller;

    StringBuilder contents = new StringBuilder();

    for (String s : args) {
      contents.append(s);
    }

    // accepts argument from user - GUI
    if (contents.toString().equals("")) {
      guiView = new JFrameView();
      guiController = new GUIController(model);
      guiController.setView(guiView);
    }
    // accepts argument from user - Console
    else if (args.length == 1 && args[0].equals("-text")) {
      simpleView = new SimpleView(model);
      controller = new ControllerImpl(model, simpleView, new InputStreamReader(System.in));
      controller.startProcessor();
    }
    // runs through script of commands - Console
    else if (args.length == 2 && args[0].equals("-file") && args[1].endsWith(".txt")) {
      simpleView = new SimpleView(model);

      BufferedReader bufferedReader;
      try {
        bufferedReader = new BufferedReader(new FileReader(args[1]));
      } catch (FileNotFoundException e) {
        simpleView.renderMessage("Supplied script could not be read.");
        return;
      }

      try {
        String line = bufferedReader.readLine();

        while (line != null) {
          builder.append(line);
          builder.append(System.lineSeparator());
          line = bufferedReader.readLine();
        }
        bufferedReader.close();
      } catch (IOException e) {
        simpleView.renderMessage("Cannot read supplied file");
      }

      controller = new ControllerImpl(model, simpleView, new StringReader(builder.toString()));
      try {
        controller.startProcessor();
      } catch (IllegalStateException e) {
        simpleView.renderMessage("Image processor quit.");
        return;
      }

    } else {
      simpleView = new SimpleView(model);
      simpleView.renderMessage("Invalid command line arguments.");
      return;
    }
  }

}

