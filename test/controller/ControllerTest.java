package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import model.IModel;
import model.ImageModel;
import utils.FakeAppendable;

import static org.junit.Assert.assertEquals;

/**
 * Test for Controller.
 */
public class ControllerTest {

  private IModel model;
  private Appendable output;

  @Before
  public void initialize() {
    this.output = new StringBuilder();
    this.model = new ImageModel();
  }

  @Test
  public void testFromControllerToModel() {
    Appendable output = new StringBuilder();
    Readable input = new StringReader("load res/2x2color.ppm 2x2color q");
    IModel model = new ImageModel();
    IController controller = new ControllerSubclass(model, input, output);

    controller.runProgram();

    assertEquals("load res/2x2color.ppm 2x2color\n", output.toString());


    Appendable output2 = new StringBuilder();
    Readable input2 = new StringReader("load res/2x2color.ppm 2x2color "
            + "save res/2x2color.ppm 2x2color q");
    IModel model2 = new ImageModel();
    IController controller2 = new ControllerSubclass(model2, input2, output2);

    controller2.runProgram();

    assertEquals("load res/2x2color.ppm 2x2color\n"
            + "save res/2x2color.ppm 2x2color\n", output2.toString());

    Appendable output3 = new StringBuilder();
    Readable input3 = new StringReader("red-greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model3 = new ImageModel();
    IController controller3 = new ControllerSubclass(model3, input3, output3);

    controller3.runProgram();

    assertEquals("red-greyscale res/2x2color.ppm res/2x2color.ppm\n", output3.toString());

    Appendable output4 = new StringBuilder();
    Readable input4 = new StringReader("green-greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model4 = new ImageModel();
    IController controller4 = new ControllerSubclass(model4, input4, output4);

    controller4.runProgram();

    assertEquals("green-greyscale res/2x2color.ppm res/2x2color.ppm\n", output4.toString());

    Appendable output5 = new StringBuilder();
    Readable input5 = new StringReader("blue-greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model5 = new ImageModel();
    IController controller5 = new ControllerSubclass(model5, input5, output5);

    controller5.runProgram();

    assertEquals("blue-greyscale res/2x2color.ppm res/2x2color.ppm\n", output5.toString());

    Appendable output6 = new StringBuilder();
    Readable input6 = new StringReader("luma-greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model6 = new ImageModel();
    IController controller6 = new ControllerSubclass(model6, input6, output6);

    controller6.runProgram();

    assertEquals("luma-greyscale res/2x2color.ppm res/2x2color.ppm\n", output6.toString());

    Appendable output7 = new StringBuilder();
    Readable input7 = new StringReader("intensity-greyscale res/2x2color.ppm "
            + "res/2x2color.ppm q");
    IModel model7 = new ImageModel();
    IController controller7 = new ControllerSubclass(model7, input7, output7);

    controller7.runProgram();

    assertEquals("intensity-greyscale res/2x2color.ppm res/2x2color.ppm\n", output7.toString());

    Appendable output8 = new StringBuilder();
    Readable input8 = new StringReader("value-greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model8 = new ImageModel();
    IController controller8 = new ControllerSubclass(model8, input8, output8);

    controller8.runProgram();

    assertEquals("value-greyscale res/2x2color.ppm res/2x2color.ppm\n", output8.toString());

    Appendable output9 = new StringBuilder();
    Readable input9 = new StringReader("vertical-flip res/2x2color.ppm res/2x2color.ppm q");
    IModel model9 = new ImageModel();
    IController controller9 = new ControllerSubclass(model9, input9, output9);

    controller9.runProgram();

    assertEquals("vertical-flip res/2x2color.ppm res/2x2color.ppm\n", output9.toString());

    Appendable output10 = new StringBuilder();
    Readable input10 = new StringReader("horizontal-flip res/2x2color.ppm res/2x2color.ppm q");
    IModel model10 = new ImageModel();
    IController controller10 = new ControllerSubclass(model10, input10, output10);

    controller10.runProgram();

    assertEquals("horizontal-flip res/2x2color.ppm res/2x2color.ppm\n", output10.toString());

    Appendable output11 = new StringBuilder();
    Readable input11 = new StringReader("brighten -50 res/2x2color.ppm res/2x2color.ppm q");
    IModel model11 = new ImageModel();
    IController controller11 = new ControllerSubclass(model11, input11, output11);

    controller11.runProgram();

    assertEquals("brighten -50 res/2x2color.ppm res/2x2color.ppm\n", output11.toString());

    Appendable output12 = new StringBuilder();
    Readable input12 = new StringReader("brighten +100 res/2x2color.ppm res/2x2color.ppm q");
    IModel model12 = new ImageModel();
    IController controller12 = new ControllerSubclass(model12, input12, output12);

    controller12.runProgram();

    assertEquals("brighten +100 res/2x2color.ppm res/2x2color.ppm\n", output12.toString());

    Appendable output13 = new StringBuilder();
    Readable input13 = new StringReader("brighten 0 res/2x2color.ppm res/2x2color.ppm q");
    IModel model13 = new ImageModel();
    IController controller13 = new ControllerSubclass(model13, input13, output13);

    controller13.runProgram();

    assertEquals("brighten 0 res/2x2color.ppm res/2x2color.ppm\n", output13.toString());

    Appendable output14 = new StringBuilder();
    Readable input14 = new StringReader("blur res/2x2color.ppm res/2x2color.ppm q");
    IModel model14 = new ImageModel();
    IController controller14 = new ControllerSubclass(model14, input14, output14);

    controller14.runProgram();

    assertEquals("blur res/2x2color.ppm res/2x2color.ppm\n", output14.toString());

    Appendable output15 = new StringBuilder();
    Readable input15 = new StringReader("sepia-tone res/2x2color.ppm res/2x2color.ppm q");
    IModel model15 = new ImageModel();
    IController controller15 = new ControllerSubclass(model15, input15, output15);

    controller15.runProgram();

    assertEquals("sepia-tone res/2x2color.ppm res/2x2color.ppm\n", output15.toString());

    Appendable output16 = new StringBuilder();
    Readable input16 = new StringReader("sharpen res/2x2color.ppm res/2x2color.ppm q");
    IModel model16 = new ImageModel();
    IController controller16 = new ControllerSubclass(model16, input16, output16);

    controller16.runProgram();

    assertEquals("sharpen res/2x2color.ppm res/2x2color.ppm\n", output16.toString());

    Appendable output17 = new StringBuilder();
    Readable input17 = new StringReader("greyscale res/2x2color.ppm res/2x2color.ppm q");
    IModel model17 = new ImageModel();
    IController controller17 = new ControllerSubclass(model17, input17, output17);

    controller17.runProgram();

    assertEquals("greyscale res/2x2color.ppm res/2x2color.ppm\n", output17.toString());
  }

  @Test
  public void testMultipleCommands() {
    Readable input = new StringReader("load res/2x2color.ppm sample "
            + "vertical-flip sample sampleVerticalFlip "
            + "horizontal-flip sample sampleHorizontalFlip q");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("load res/2x2color.ppm sample\n" +
            "vertical-flip sample sampleVerticalFlip\n" +
            "horizontal-flip sample sampleHorizontalFlip\n", this.output.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testHandleException() {
    Appendable output = new FakeAppendable();
    Readable input = new StringReader("load res/2x2color.ppm sample q");
    IController controller = new ControllerSubclass(this.model, input, output);
    controller.runProgram();
  }

  @Test
  public void testQuitAtBeginning() {
    Readable input = new StringReader("q brighten 100 res/2x2color.ppm res/2x2color.ppm q");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());
  }

  @Test
  public void testQuitAtEnd() {
    Readable input = new StringReader("load res/2x2color.ppm sample "
            + "brighten 100 sample sampleBrighten q");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("load res/2x2color.ppm sample\n" +
            "brighten 100 sample sampleBrighten\n", this.output.toString());
  }

  @Test
  public void testQuitAfterSeveralSteps() {
    Readable input = new StringReader("load res/2x2color.ppm sample "
            + "brighten 100 sample sampleBrighten "
            + "vertical-flip sample sampleVerticalFlip q");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("load res/2x2color.ppm sample\n"
            + "brighten 100 sample sampleBrighten\n"
            + "vertical-flip sample sampleVerticalFlip\n", this.output.toString());
  }

  @Test
  public void testQuitAtMiddleOfTwoValidCommands() {
    Readable input = new StringReader("load res/2x2color.ppm sample "
            + "brighten 100 sample sampleBrighten "
            + "q vertical-flip sample sampleVerticalFlip");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("load res/2x2color.ppm sample\n" +
            "brighten 100 sample sampleBrighten\n", this.output.toString());
  }

  @Test
  public void testQuitAtOnce() {
    Readable input = new StringReader("q brighten 100 res/2x2color.ppm res/2x2color.ppm "
            + "q vertical-flip res/2x2color.ppm res/2x2color.ppm q ");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());
  }


  @Test
  public void testQuitAtMiddleOfCommand() {
    Readable input = new StringReader("brighten q 100 res/2x2color.ppm res/2x2color.ppm");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());

    Readable input2 = new StringReader("brighten 100 q res/2x2color.ppm res/2x2color.ppm");
    IController controller2 = new ControllerSubclass(this.model, input2, this.output);
    controller2.runProgram();

    assertEquals("", this.output.toString());

    Readable input3 = new StringReader("brighten 100 res/2x2color.ppm q res/2x2color.ppm");
    IController controller3 = new ControllerSubclass(this.model, input3, this.output);
    controller3.runProgram();

    assertEquals("", this.output.toString());
  }

  @Test
  public void testQuitWithBigQ() {
    Readable input = new StringReader("Q brighten 100 res/2x2color.ppm res/2x2color.ppm");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());

    Readable input2 = new StringReader("load res/2x2color.ppm sample "
            + "brighten 100 sample sampleBrighten Q");
    IController controller2 = new ControllerSubclass(this.model, input2, this.output);
    controller2.runProgram();

    assertEquals("load res/2x2color.ppm sample\n" +
            "brighten 100 sample sampleBrighten\n", this.output.toString());
  }

  @Test
  public void testEmptyInput() {
    Readable input = new StringReader("");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());

  }

  @Test
  public void testEmptyButMultipleLinesInput() {
    Readable input = new StringReader("\n\n");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("", this.output.toString());

  }

  @Test
  public void testMultipleLinesInput() {
    Readable input = new StringReader("load res/2x2color.ppm sample "
            + "brighten 100 sample sampleBrighten\n"
            + "vertical-flip sample sampleVerticalFlip\n"
            + "horizontal-flip sample sampleHorizontalFlip\nq");
    IController controller = new ControllerSubclass(this.model, input, this.output);
    controller.runProgram();

    assertEquals("load res/2x2color.ppm sample\n" +
            "brighten 100 sample sampleBrighten\n" +
            "vertical-flip sample sampleVerticalFlip\n" +
            "horizontal-flip sample sampleHorizontalFlip\n", this.output.toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense1() {
    Readable input = new StringReader("load dhsadhas res/2x2color.ppm sample");
    IController controller = new Controller(this.model, input);
    controller.runProgram();

  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense2() {
    Readable input2 = new StringReader("load res/2x2color.ppm heu21he sample");
    IController controller2 = new Controller(this.model, input2);
    controller2.runProgram();
  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense3() {
    Readable input3 = new StringReader("load res/2x2color.ppm sample dhasuidas");
    IController controller3 = new Controller(this.model, input3);
    controller3.runProgram();
  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense4() {
    Readable input4 = new StringReader("apple res/2x2color.ppm sample");
    IController controller4 = new ControllerSubclass(this.model, input4, this.output);
    controller4.runProgram();

  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense5() {
    Readable input5 = new StringReader("load 12e1e21e2.ppm sample dsad");
    IController controller5 = new Controller(this.model, input5);
    controller5.runProgram();
  }

  @Test(expected = IllegalStateException.class)
  public void testThrowExceptionWhenMeetNonsense6() {
    Readable input6 = new StringReader("123  32134 5213");
    IController controller6 = new ControllerSubclass(this.model, input6, this.output);
    controller6.runProgram();
  }
}