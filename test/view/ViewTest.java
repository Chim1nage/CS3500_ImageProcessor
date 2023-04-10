package view;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import model.IModel;
import model.ImageModel;

import static org.junit.Assert.assertEquals;

/**
 * test for view.
 */
public class ViewTest {
  private IModel model;
  private Appendable output;

  private ControllerMVCSubClass controller;

  private IOwnListener listener;

  @Before
  public void initialize() {
    Appendable output = new StringBuilder();
    this.output = output;
    IView view = new MockView(output);
    IModel model = new ImageModel();
    this.model = model;
    this.controller = new ControllerMVCSubClass(model, view);
    this.listener = controller.assignListener();
  }

  @Test
  public void initializeGUITest() {
    controller.runProgram();
    assertEquals("a new listener was added\n" +
            "red-greyscale with 0 parameters\n" +
            "brighten with 1 parameters\n" +
            "sharpen with 0 parameters\n" +
            "green-greyscale with 0 parameters\n" +
            "value-greyscale with 0 parameters\n" +
            "save with 0 parameters\n" +
            "blur with 0 parameters\n" +
            "greyscale with 0 parameters\n" +
            "horizontal-flip with 0 parameters\n" +
            "intensity-greyscale with 0 parameters\n" +
            "sepia-tone with 0 parameters\n" +
            "luma-greyscale with 0 parameters\n" +
            "load with 0 parameters\n" +
            "vertical-flip with 0 parameters\n" +
            "blue-greyscale with 0 parameters\n" +
            "down-scale with 2 parameters\n" +
            "view has been initialized successfully\n" +
            "a new listener was added\n", this.output.toString());
  }

  @Test
  public void CommandTriggerTest() {
    this.controller.runProgram();
    this.listener.actionPerformed(new ActionEvent("", 0, "red-greyscale"));
    String message = "a new listener was added\n" +
            "red-greyscale with 0 parameters\n" +
            "brighten with 1 parameters\n" +
            "sharpen with 0 parameters\n" +
            "green-greyscale with 0 parameters\n" +
            "value-greyscale with 0 parameters\n" +
            "save with 0 parameters\n" +
            "blur with 0 parameters\n" +
            "greyscale with 0 parameters\n" +
            "horizontal-flip with 0 parameters\n" +
            "intensity-greyscale with 0 parameters\n" +
            "sepia-tone with 0 parameters\n" +
            "luma-greyscale with 0 parameters\n" +
            "load with 0 parameters\n" +
            "vertical-flip with 0 parameters\n" +
            "blue-greyscale with 0 parameters\n" +
            "down-scale with 2 parameters\n" +
            "view has been initialized successfully\n"+
            "a new listener was added\n";

    message += "red-greyscale button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 1, "green-greyscale"));

    message += "green-greyscale button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 2, "load"));
    message += "load button was triggered\n" +
            "load button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 3, "save"));
    message += "save button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 3, "save"));
    message += "save button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 3, "greyscale"));
    message += "greyscale button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());

    this.listener.actionPerformed(new ActionEvent("", 3, "xxx"));
    message += "xxx button was triggered\n" +
            "alert message is No photo has been rendered, please load one first.\n";
    assertEquals(message, this.output.toString());
  }

  @Test
  public void CommandTriggerTest2() {
    IView view = new MockView2(output);
    this.controller = new ControllerMVCSubClass(model, view);
    this.listener = this.controller.assignListener();
    this.controller.runProgram();
    String message = "a new listener was added\n" +
            "red-greyscale with 0 parameters\n" +
            "brighten with 1 parameters\n" +
            "sharpen with 0 parameters\n" +
            "green-greyscale with 0 parameters\n" +
            "value-greyscale with 0 parameters\n" +
            "save with 0 parameters\n" +
            "blur with 0 parameters\n" +
            "greyscale with 0 parameters\n" +
            "horizontal-flip with 0 parameters\n" +
            "intensity-greyscale with 0 parameters\n" +
            "sepia-tone with 0 parameters\n" +
            "luma-greyscale with 0 parameters\n" +
            "load with 0 parameters\n" +
            "vertical-flip with 0 parameters\n" +
            "blue-greyscale with 0 parameters\n" +
            "a new listener was added\n";
  }
}
