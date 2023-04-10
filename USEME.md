# CS3500Team

# Contributors: Hanghong Lin, Zhijie Jiang

---

* **All Script Commands**
    * Commands are not case-sensitive and the parameters that they take are order-sensitive
    * trying to manipulate an image that has not been load to the program yet will result in the
      program throwing an IllegalArgumentException.
    * Quit:                 Enter a ‘q’, ‘Q’, 'quit' or "QUIT" at any point when you would like to
      quit the program.
    * Open JAR file:        java -jar file-path
    * Load file:            load file-path image-name
    * Save file:            save file-path image-name
    * Brighten:             brighten delta old-image-name new-image-name
    * Vertical flip:        vertical-flip old-image-name new-image-name
    * Horizontal flip:      horizontal-flip old-image-name new-image-name
    * Red greyscale:        red-greyscale old-image-name new-image-name
    * Green greyscale:      green-greyscale old-image-name new-image-name
    * Blue greyscale:       blue-greyscale old-image-name new-image-name
    * Value greyscale:      value-greyscale old-image-name new-image-name
    * Intensity greyscale:  intensity-greyscale old-image-name new-image-name
    * Luma greyscale:       luma-greyscale old-image-name new-image-name
    * Blur:                 blur old-image-name new-image-name
    * Sharpen:              sharpen old-image-name new-image-name
    * Greyscale:            greyscale old-image-name new-image-name
    * Sepia tone:           sepia-tone old-image-name new-image-name
    * Down scale:           down-scale width height old-image-name new-image-name
    * Use of mask:          for operations that support masking, include the mask name between the old-image-name and new-image-name
        * ``The mask must be the same size as the old image, and be black and white
        * ``brighten, all greyscale, blur, sharpen, and sepiatone supports use of mask.
    
* **Using Script file**
    * ``java -jar CS3500Team.jar -file [Command-file]``
        * [Command-file] is the txt file that contains the commands need to be executed.

* **Examples using Script Commands**
    * ``java -jar Program.jar -text ...``: open the program and enter script command mode.
    * Images should be loaded beforehand if needed to manipulate.
    * Load an image, horizontal flip it, and save the new image
        * ``load 2x2color.ppm 2x2color horizontal-flip 2x2color 2x2colorFlipped save new2x2color.ppm 2x2colorFlipped``
    * Save an image into a new format
        * ``load 2x2color.ppm 2x2color save new2x2color.png 2x2color``
    * Blur an image twice and save it
        * ``load 2x2color.ppm 2x2color blur 2x2color 2x2colorBlur blur 2x2colorBlur 2x2colorBlur+ save new2x2color.ppm 2x2colorBlur+``
    * Use a Script file
        * ``java -jar CS3500Team.jar -file CommandScript.txt``

* **Using graphical user interface**
    * ``java -jar CS3500Team.jar``: open the program and enter the graphical user interface.
    * Quit by exiting the window.
    * Images should be loaded before mannipulating.
