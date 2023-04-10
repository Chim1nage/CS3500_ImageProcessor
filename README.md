# CS3500Team

# Contributors: Hanghong Lin, Zhijie Jiang

___

**Description:** This project is an imaging processing application that supports text based
scripting. It allows users to maniupulate images including saving, loading, brightening, flipping,
etc. The design of the project follows the Model-View-Controller(MVC) Design pattern, while also
using command design pattern and strategy pattern. The program was developed via IntelliJ IDEA.
Currently, the supported file formats are .ppm, .jpg, .jpeg, .png, etc.

---
**Text Commands**

* Commands are not case-sensitive and the parameters that they take are order-sensitive
* Enter a ‘q’, ‘Q’, 'quit' or "QUIT" at any point when you would like to quit the program.
* [mask-image-name] is an image that is black and white and has the same size as the original image. Every command that contains a mask-image-name will only apply the operation on the original image where the mask image is black.

    * **Retrieval and output**
        * ``load [file-path] [image-name]``
        * ``save [file-path] [image-name]``
            * ``[file-path]`` is the file path of the image
            * ``[image-name]`` is the name which the image is referred to in the program

    * **Orientation**
        * ``horizontal-flip [image-name] [dest-image-name]``
        * ``vertical-flip [image-name] [dest-image-name]``
            * ``[dest-image-name]`` is the name stored by the program of the image after flipping

    * **Brightness**
        * ``brighten [increment] [image-name] [dest-image-name]``
            * ``[increment]`` is the amount to brighten (positive integer) or darken (negative
              integer)
        * ``brighten [increment] [image-name] [mask-image-name] [dest-image-name]``

    * **Greyscale manipulation**
        * ``red-greyscale [image-name] [dest-image-name]``
        * ``red-greyscale [image-name] [mask-image-name] [dest-image-name]``
        * ``green-greyscale [image-name] [dest-image-name]``
        * ``green-greyscale [image-name] [mask-image-name] [dest-image-name]``
        * ``blue-greyscale [image-name] [dest-image-name]``
        * ``blue-greyscale [image-name] [mask-image-name] [dest-image-name]``
        * ``value-greyscale [image-name] [dest-image-name]``
            * changes each pixel to the the pixel's greatest color component
        * ``value-greyscale [image-name] [mask-image-name] [dest-image-name]``
        * ``intensity-greyscale [image-name] [dest-image-name]``
            * alters the color component of each pixel to the average of the three color component
        * ``intensity-greyscale [image-name] [mask-image-name] [dest-image-name]``
        * ``luma-greyscale [image-name] [dest-image-name]``
            * changes the color component of each pixel to 0.2126r + 0.7152g + 0.0722b
        * ``luma-greyscale [image-name] [mask-image-name] [dest-image-name]``
        
    * **Down-scale image**
        * ``down-scale width height [image-name] [dest-image-name]
            * downscales an image to the given int width and height

* **Filtering operations**
    * ``blur [image-name] [dest-image-name]``
        * blurs an image
    * ``blur [image-name] [mask-image-name] [dest-image-name]``
    * ``sharpen [image-name] [dest-image-name]``
        * sharpens an image
    * ``sharpen [image-name] [mask-image-name] [dest-image-name]``

* **Color transformation operations**
    * ``greyscale [image-name] [dest-image-name]``
        * has the same function as luma-greyscale
    * ``greyscale [image-name] [mask-image-name] [dest-image-name]``
    * ``sepia-tone [image-name] [dest-image-name]``
        * makes the image sepia-toned
    * ``sepia-tone [image-name] [mask-image-name] [dest-image-name]``

**Running the Project**

* Text commands: paste the following command into your terminal to run the program
  as individual text commands
    * To load, horizontal flip and, save an image
    * ``java -jar Program.jar -text load res/2x2color.ppm 2x2color horizontal-flip 2x2color 2x2colorFlipped save res/new2x2color.ppm 2x2colorFlipped``
* Script file: paste the following command into your terminal to run a series of 
  text commands at once
    * ``java -jar CS3500Team.jar -file CommandScript.txt``
* GUI: To use the program in graphical user interface use:
    * ``java -jar Program.jar``


---
**Design:**

* *model*
    * IModel: Represents all image processing model
    * ImageModel: Implements a model used to load, alter, and save images. All images
      are saved as an int[][]
    * ModelFunctions: The interface that represents all functions that are able to be applied on the
      IModel
    * Brighten: Function object that takes in a delta, old name, and new name. Brightens all
      pixels according to the delta and save under the new name.
    * HorizontalFlip: Function object that horizontally flips the old image and saves under a new
      name.
    * VerticalFlip: Function object that vertically flips the old image and saves under a new name.
    * GreyScaleAbstract: Abstract class for all grey scale function objects including GreyScaleBlue,
      GreyScaleRed,
      GreyScaleGreen, GreyScaleIntensity, GreyScaleValue, and GreyScaleLuma.
    * Blur: Function object that blurs the old image and saves under a new name.
    * Sharpening: Function object that sharpens the old image and saves under a new name.
    * GreyScale: Function object that greyScales the old image and saves under a new name.
    * SepiaTone: Function object that turns the old image into sepia tone and saves under a new
      name.
    * Filter: Function object that returns the value of a channel of a pixel after multiplying a
      given matrix.
    * ColorTransformation: Funciton obeject that change all channel of all pixels after multiplying
      each pixel with a given matrix, and save under a new name.
    * Downsize: Function objects that can resize a given image to a specific width and heigh.
    * CreateWindow: A function objects returning a 200x200 image of part of a larger image.

* *utils*
    * ImageUtil: loads a file to the program given a filepath, and
      saves an image given a name that the program recognizes and a filepath
    * 

* *controller*
    * Controller: Represents the controller of IModel(image processor) that
      takes in data from the user, utilizes the commands and outputs messages to the user.
    * IController: Represents the methods an image processor controller supports
    * ICommand: Represents a command with all methods that all commands should support
    * Commands:
        - AbstractCommand: contains the common and abstract functions in all commands
        - BrightenCommand: reads the user input, verify it has an increment parameter, and delegates
          to the model's apply method and uses the Brighten function class
        - HorizontalFlipCommand: delegates to the model's apply method and uses the HorizontalFlip
          function class
        - VerticalFlipCommand: delegates to the model's apply method and uses the VerticalFlip
          function class
        - GreyscaleBlueCommand: delegates to the model's apply method and uses the GreyScaleBlue
          function class
        - GreyscaleRedCommand: delegates to the model's apply method and uses the GreyScaleRed
          function class
        - GreyscaleGreenCommand: delegates to the model's apply method and uses the GreyScaleGreen
          function class
        - GreyscaleLumaCommand: delegates to the model's apply method and uses the GreyScaleLuma
          function class
        - GreyscaleIntensityCommand: delegates to the model's apply method and uses the
          GreyScaleIntensity function class
        - GreyscaleValueCommand: delegates to the model's apply method and uses the GreyScaleValue
          function class
        - BlurCommand: delegates to the model's apply method and uses the Blur function class
        - GreyScaleCommand: delegates to the model's apply method and uses the GreyScale function
          class
        - SharpenCommand: delegates to the model's apply method and uses the Sharpening function
          class
        - SepiaToneCommand: delegates to the model's apply method and uses the SepiaTone function
          class
        - Load: delegates to the model’s load method
        - Save: delegates to the model’s save method
        - DownScaleCommand: delegates to the model's apply method and uses the Downsize function
          class
    * QuitException: designed to verify command receive command of quit
      and throw exception to inform controller.

* **Image Citation**
    * /res/2x2color.ppm is an image made by myself using photoshop, it is free to use.
    * /res/class/class.png is an image from the class
      assignment. https://northeastern.instructure.com/courses/119265/assignments/1457644
    * /res/other/whatsapp.png is an image from online.
      https://www.flaticon.com/free-icon/whatsapp_124034 attribute to Freepik.
    * Other images in /res are all created by manipulating the images or by the program itself.
    
* **Design Changes from previous model**
    * HW6 changes: Controller was unchanged from HW5. The model interface and class was slightly changed in order to support the a new funciton. To statisfy HW6's requirements, for controller, we added a new controllerMVC that extends the previous controller and implements IController. For model, we added a strategy function objects that is used to return a hashMap of frequencies. The function objects will  use the new implemented apply function in model. We added an IView interface and its implementation Graphical view class. The class is responsible for creating a GUI view of the program and contains all methods. Histrogram and Image class was created and extended JPanel as our own customized panel. We created IOwnListener which extends ActionListener because, if further function needed to be added, it would be more convinient. Main method was also changed to support new functions.
    * Extra Credit changes: For level 1 implementing the Downscaling funtion, a function object in the model module named Downsize was created to modify an int[][] image to its downsized version. A DownScaleCommand was also created in the controller functions which used the function objects above. The command was added into the hashmap of the Controller, and a button for downsize was added in the GUI view that would take in the width and height of the new image. For level 2 implementing partial image manipulation, the run method of ICommand interface was edited to take parameters as an ArrayList to accept various number of parameter. Each function that supported masking was edited to support masking. Constructors of all funciton objects were edited to distinguish with or with mask operation and will throw exception from those not supporting masking. The controller also changed in order to support taking in multiple strings rather than 2 strings in HW6. For level 3 implementing preview mode, A checkbox was added into GUI to support preview mode. As check box was added, OwnListener was edited to listen the change of selected item from check box. Also, a new pane of "preview" was added below the area of orginal photo. Further, OwnListener was edited as a mouseListener to listen the click over original photo, thus being able to recogize which area has been selected as preview. The logic of action performed in OwnListener has been edited to fit the situation with or without preview mode. With response to that, IView add new refresh and clear method specific to preview area when action performed.
