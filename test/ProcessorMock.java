import java.io.IOException;
import java.util.Map;

import model.IFinalProcessingModel;
import model.util.GeneralImage;

/**
 * Confirm inputs that are sent to the model from inputs via the controller.
 */
public final class ProcessorMock implements IFinalProcessingModel {
  private final Appendable log;

  /**
   * Initialize this mock to confirm user inputs sent to the model.
   *
   * @param log used to keep a log of inputs sent to the controller.
   */
  public ProcessorMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void sharpen(String imageName, String destImageName) throws IllegalArgumentException {
    try {
      this.log.append("Sharpen. Image Name: " + imageName + ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to sharpen image.");
    }
  }

  @Override
  public void sharpen(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Mask Image Sharpen. Src Image Name: " + imageName + ". Mask Image Name: " +
              maskImageName + "Dest Image Name: " + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to sharpen image.");
    }
  }

  @Override
  public void blur(String imageName, String destImageName) throws IllegalArgumentException {
    try {
      this.log.append("Blur. Image Name: " + imageName + ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to blur image.");
    }
  }

  @Override
  public void blur(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Mask Image Blur. Src Image Name: " + imageName + ". Mask Image Name: "
              + maskImageName + ". Dest Image Name: " + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to blur image.");
    }
  }

  @Override
  public void sepia(String imageName, String destImageName) throws IllegalArgumentException {
    try {
      this.log.append("Sepia. Image Name: " + imageName + ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to apply sepia to image.");
    }
  }

  @Override
  public void sepia(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Mask Image Sepia. Image Name: " + imageName + "Mask Image Name: "
              + maskImageName + ". Dest Image Name: " + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to sepia image.");
    }
  }

  @Override
  public void greyscale(String imageName, String destImageName) throws IllegalArgumentException {
    try {
      this.log.append("Greyscale. Image Name: " + imageName + ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to greyscale image.");
    }
  }

  @Override
  public void greyscale(String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Maks Image General Greyscale. Src Image Name: " + imageName +
              ". Mask Image Name: " + maskImageName
              + ". Dest Image Name: " + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to greyscale image.");
    }
  }

  @Override
  public void flip(boolean horizontal, String imageName, String destImageName)
          throws IllegalArgumentException {
    try {
      if (horizontal) {
        this.log.append("Horizontal-Flip. Image Name: " + imageName + ". Destination Image Name: "
                + destImageName + ".\n");
      } else {
        this.log.append("Vertical-Flip. Image Name: " + imageName + ". Destination Image Name: "
                + destImageName + ".\n");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to flip image.");
    }
  }

  @Override
  public void brighten(int increment, String imageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Brighten. Image Name: " + imageName + ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to brighten image.");
    }
  }

  @Override
  public void brighten(int increment, String imageName, String maskImageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append("Mask Image Brighten. Increment: " + increment + ". Src Image Name: "
              + imageName + "." +
              " Mask Image Name: " + maskImageName + ". Dest Image Name: " + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to brighten image.");
    }
  }

  @Override
  public void componentGreyScale(GreyscaleType type, String imageName, String destImageName)
          throws IllegalArgumentException {
    try {
      this.log.append(type + "-component greyscale. Image Name: " + imageName +
              ". Destination Image Name: "
              + destImageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Confirm inputs to " + type + "-component greyscale.");
    }
  }

  @Override
  public void componentGreyScale(GreyscaleType type, String imageName, String maskImageName,
                                 String destImageName) throws IllegalArgumentException {
    try {
      this.log.append("Mask Image Component GeryScale. Type: " + type + ". Src Image Name: "
              + imageName + ". Mask" + " Image Name: " + maskImageName + ". Dest Image Name: "
              + destImageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to component greyscale image.");
    }
  }

  @Override
  public GeneralImage getImage(String imageName) {
    try {
      this.log.append("Get Image. Image Name: " + imageName + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to get image.");
    }
    return null;
  }

  @Override
  public void addImage(String imageName, GeneralImage image) {
    try {
      this.log.append("Add Image. Image Name: " + imageName + ". Image-Width: " + image.getWidth()
              + ". Image-Height: " + image.getHeight() + ". Image-MaxValue: "
              + image.getMaxValue() + ".\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot confirm inputs to add image.");
    }
  }

  @Override
  public Map<String, GeneralImage> getLoadedImages() {
    return null;
  }

  @Override
  public Map<Integer, int[]> generateFrequencies(String imageName) throws IllegalArgumentException {
    try {
      this.log.append("Generate Frequencies. Image Name: " + imageName + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to generate frequencies" +
              "of image.");
    }
    return null;
  }

  @Override
  public void downScale(String imageName, String destImageName, double widthScaleFactor,
                        double heightScaleFactor) throws IllegalArgumentException {
    try {
      this.log.append("Mask Image DownScale. Src Image Name: " + imageName + ". Dest Image Name: "
              + destImageName + ". Width Scale Factor: " + widthScaleFactor +
              ". Height Scale Factor: " + heightScaleFactor + ".");
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to confirm inputs to downscale image.");
    }
  }

}
