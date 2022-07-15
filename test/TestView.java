import org.junit.Test;

import model.BetterProcessingModel;
import model.IBetterProcessingModel;
import model.IProcessingModel;
import model.SimpleProcessingModel;
import view.IView;
import view.SimpleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Used to test the method of the IView (SimpleView). Tests output.
 */
public final class TestView {

  @Test
  public void testView() {
    IProcessingModel model = new SimpleProcessingModel();
    IView view;
    Appendable builder = new StringBuilder();
    Appendable corrupt = new CorruptAppendable();

    try {
      view = new SimpleView(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Supplied model is null to default System.out view constructor.",
              e.getMessage());
    }

    try {
      view = new SimpleView(model, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View cannot be initialized with null parameters", e.getMessage());
    }
    try {
      view = new SimpleView(null, new StringBuilder());
    } catch (IllegalArgumentException e) {
      assertEquals("View cannot be initialized with null parameters", e.getMessage());
    }
    try {
      view = new SimpleView(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("View cannot be initialized with null parameters", e.getMessage());
    }

    view = new SimpleView(model, builder);

    try {
      view.renderMessage("Test render message");
      assertEquals("Test render message", builder.toString());
    } catch (IllegalStateException e) {
      fail(e.getMessage());
    }

    view = new SimpleView(model, corrupt);
    try {
      view.renderMessage("Test render message");
    } catch (IllegalStateException e) {
      assertEquals("Unable to render message.", e.getMessage());
    }

  }

  @Test
  public void testRenderMessage() {
    IBetterProcessingModel model = new BetterProcessingModel();
    IView view;
    Appendable builder = new StringBuilder();

    view = new SimpleView(model, builder);

    view.renderMessage("Test render message - better processing model.");

    assertEquals("Test render message - better processing model.", builder.toString());

    IProcessingModel modelSimple = new SimpleProcessingModel();
    IView view2;
    Appendable builder2 = new StringBuilder();

    view2 = new SimpleView(modelSimple, builder2);

    view2.renderMessage("Test render message - simple processing model.");

    assertEquals("Test render message - simple processing model.", builder2.toString());
  }

}
