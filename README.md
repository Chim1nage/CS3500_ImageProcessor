# CS3500 - Image Processor
#### Contributors: Daniel Szyc and Evan Keister
___

**Description:** This project is an image processing application, supporting text-based and 
interactive GUI-based user interfaces. The application was developed to follow a 
Model-View-Controller (MVC) framework, a core principle of object-oriented design. It was 
written in Java using IntelliJ IDEA. As new features were incrementally added to the program, 
we practiced
[SOLID principles](https://www.digitalocean.com/community/conceptual_articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design)
to minimize modification of previously written code. 

Currently, the supported file formats are JPG, PNG, BMP, and PPM.

---
**Text Commands**
* *Note: the program supports running a series of text commands as ``.txt`` script file;
refer to the following section*
* Commands are case-sensitive and the parameters that they take are order-sensitive
* Enter a ‘q’ or ‘Q’ at any point when you would like to quit the program 
  * **Retrieval and output**
    * ``load [image-path] [image-name]``
    * ``save [image-path] [image-name]``
      * ``[image-path]`` is the file path of the target image and ``[image-name]``
      is the name which the image is referred to in the program 
  * **Orientation**
    * ``horizontal-flip [image-name] [dest-image-name]``
    * ``vertical-flip [image-name] [dest-image-name]``
      * ``[dest-image-name]`` is the name of the resulting flipped image,
      which is stored in the program
  * **Brightness**
    * ``brighten [increment] [image-name] [dest-image-name]``
      * ``[increment]`` is the amount to brighten (positive integer) or darken (negative integer)
  * **Greyscale manipulation**
    * ``red-component [image-name] [dest-image-name]`` 
    * ``green-component [image-name] [dest-image-name]``
    * ``blue-component [image-name] [dest-image-name]``
      * *Note: a greyscale manipulation of any RGB value x involves setting the 
      value of each RRB channel to x's value*
    * ``value-component [image-name] [dest-image-name]``
      * Takes the maximum value of each color component of a pixel
    * ``luma-component [image-name] [dest-image-name]``
      * Averages the three color components of a pixel
    * ``intensity-component [image-name] [dest-image-name]``
      * Applies the weighted sum 0.2126r + 0.7152g + 0.0722b
  * **Filter operations** [(using kernels)](https://towardsdatascience.com/types-of-convolution-kernels-simplified-f040cb307c37)
    * ``blur [image-name] [dest-image-name]``
    * ``sharpen [image-name] [dest-image-name]``
  * **Color transformations**  [(using color matrices)](https://www.c-sharpcorner.com/article/color-transformations-and-the-color-matrix/#:~:text=In%20color%20transformation%2C%20we%20apply,a%205%20x%205%20matrix.)
    * ``greyscale [image-name] [dest-image-name]``
      * *Note: greyscale produces the same result as luma-component, but is instead
      implemented with a linear color transformation*
    * ``sepia [image-name] [dest-image-name]``


**Running the Project via ``ImageProcessor.jar``**

* GUI: simply double-click on the JAR file
* Text commands: paste the following command into your terminal to run the program
as individual text commands 

  * ``java -jar res/ImageProcessor.jar -text``
* Script file: paste the following command into your terminal to run a series of text commands at once
  * ``java -jar res/ImageProcessor.jar -file script.txt``








---
**Design:**

* Main: Runs the program and reacts to:
  * GUI interactions
  * Text arguments via the terminal
  * Text arguments as a ``.txt`` file
* *Model*
  * IProcessingModel: Represents all operations an image processing model
  supports 
    * GreyscaleType: Represents every possible type of greyscale representation
    as an enumeration 
  * SimpleProcessingModel: Implements a model used to retrieve, manipulate, 
  and save images. All images are stored as a string denoted by the last 
  parameter of every command for later reference 
  * IBetterProcessingModel: Extends the operations an IProcessingModel 
  supports, and adds the relevant color transformation and filtering methods 
  * BetterProcessingModel: Extends functionality of SimpleProcessingModel 
  and implements the relevant methods: blur, sharpen, grayscale, and sepia 
  * IFinalProcessingModel: Extends the operations a IBetterProcessingModel 
  supports, and adds (value,intensity) table generation, downscaling, and 
  partial image manipulation for select operations 
  * FinalProcessingModel: Extends functionality of BetterProcessingModel 
  and implements the relevant methods: generateFrequencies, PIM methods,
  and downscale 
  * Adapter 
    * Adapter(.java): Implements the adapter 
    * IViewModel: Defines what methods an adapter should support (i.e. 
    only those that cannot change the state of the model)
  * Util 
    * Pixel: Implements operations on image that can be delegated to 
    singular pixels 
    * GeneralImage: Represents an image, and stores its attributes for 
    the purpose of simply being able to easily access its fields. Serves 
    as a general data structure for all image formats, regardless of what 
    new types of image manipulations are added in the future 
* *View*
  * IView: represents the functionality of an image processor view 
  * SimpleView: implements a view to allow transmission of messages to the 
  user via an Appendable 
  * Components:
    * Image that is currently being worked on (scrollable)
  * BlankView: functions as a placeholder view for a delegate controller in 
  GUIController 
  * JFrameView: GUI view implementation, which uses Java Swing to display 
  the image processor graphically.
* *Controller*:
  * ControllerImpl: Represents the implementation of an image processor
    controller, which takes in data from the user, delegates commands to the
    model, and transmits messages to the user via the view
  * IController: Represents the methods an image processor controller should
    support
  * GUIController: Represents the implementation of a GUI-based image
    processor controller, which takes in data from the user, delegates
    commands to the model, and transmits data to the user via the GUI
  * IFeaturesController: Represents the action listener functionality of the
    GUI, describes what functions are called by user input
  * Commands ([Command Design Pattern](https://refactoring.guru/design-patterns/command))
    - IPhotoCommand: Defines how commands should delegate to the model
    - AbstractCommand: represents common functionality between all commands,
      such as checking for null arguments, verifying that the number of command
      arguments is correct or rendering to the view
    - BrightenCommand: reads the user input, verify it has an
      increment parameter, and delegates to the model’s brighten method
    - FlipCommand: reads the user input and delegates to the model’s flip method
    - GreyscaleCommand: reads the user input, determines the
      greyscale type, and delegates to the model’s greyscale method This was changed
      to support the case when PIM is attempted (supports the maskImageName parameter)
    - BlurAndSharpenCommand: reads the user input, determines the correct filter type,
      and delegates to one of the model’s filter commands
    - ColorTransformationCommand: reads the user input, determines the correct color
    - transformation, and delegates to one of the model’s color transformation commands
    - DownscaleCommand: reads the user input and delegates to the mode’s downscale method

    
