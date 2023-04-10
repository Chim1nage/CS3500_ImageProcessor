import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;
import java.util.Scanner;

import controller.Controller;
import controller.ControllerMVC;
import controller.IController;
import model.IModel;
import model.ImageModel;
import view.GraphicalView;
import view.IView;

/**
 * Main class to run the program.
 */
public final class Main {
  /**
   * main method to run the program.
   */
  public static void main(String[] args) {
    IModel model = new ImageModel();
    IView view;
    Readable input = new StringReader("");
    IController controller = new Controller(model, input);

    if (args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        if (Objects.equals(args[i], "-file")) {
          try {
            Scanner sc = new Scanner(new FileInputStream(args[i + 1]));
            StringBuilder string = new StringBuilder();
            while (sc.hasNext()) {
              string.append(sc.next()).append(" ");
            }
            input = new StringReader(string.toString());
          } catch (FileNotFoundException e) {
            throw new IllegalStateException("no such script could be run");
          }
          controller = new Controller(model, input);
        } else if (Objects.equals(args[i], "-text")) {
          input = new InputStreamReader(System.in);
          controller = new Controller(model, input);
        }
      }
    } else {
      view = new GraphicalView();
      controller = new ControllerMVC(model, view);
    }

    controller.runProgram();
  }
}
