package controller;

import view.IGUIView;

/**
 * Supports action listener functionality of the GUI. Used to represent controllers
 * used to implement GUI Views, where the controller gives commands to the view, passing
 * information stored in the model.
 */
public interface IFeaturesController {
  /**
   * Loads image to the GUI. Signaled by click on detection on the "Open" button.
   * Uses JOption Pane pop-ups to get information from the user.
   */
  public void loadImage();

  /**
   * Image that is selected in the list is detected, and is displayed on the screen.
   *
   * @param imageName name of the image selected to be displayed.
   */
  public void selectAndDisplayImage(String imageName);

  /**
   * Performs filter on an image. Takes in the name of the command and name of image to
   * operate upon.
   *
   * @param commandName name of the command
   * @param imageName   name of the image to filter
   */
  public void filter(String commandName, String imageName);

  /**
   * Save image loaded in the GUI. Takes in input from image selected in the list, and executes
   * when the Save button is pressed.
   *
   * @param selectedImage image selected to be displayed
   */
  public void saveImage(String selectedImage);

  /**
   * Enacts the view to pop-up when initializing the program.
   *
   * @param view supplied to be displayed as GUI
   */
  public void setView(IGUIView view);

}

