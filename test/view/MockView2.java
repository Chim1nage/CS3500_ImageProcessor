package view;

/**
 * designed to revise load method so that everything would be tested thoroughly.
 */
public class MockView2 extends MockView {
  /**
   * Constructor of MockView2.
   *
   * @param output is the designated source of output.
   */
  public MockView2(Appendable output) {
    super(output);
  }

  /**
   * transmit message of what button was triggered.
   *
   * @param buttonName the name of functional button that need input
   * @return input that is provided.
   */
  @Override
  public String setInputDisplayVisible(String buttonName) {
    super.setInputDisplayVisible(buttonName);
    return "res/class/class.png class";
  }

  /**
   * transmit message of load button was triggered.
   *
   * @return the location of file to be loaded.
   */
  @Override
  public String setLoadDisplayVisible() {
    super.setLoadDisplayVisible();
    return "res/class/class.png class";
  }

  /**
   * transmit message of save button was triggered.
   *
   * @return the location of file to be saved.
   */
  @Override
  public String setSaveDisplayVisible() {
    super.setSaveDisplayVisible();
    return "res/class/class.png class";
  }
}
