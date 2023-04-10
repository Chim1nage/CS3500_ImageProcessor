package view;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

import controller.RunnableAction;
import model.IModel;
import model.functions.CreateWindow;
import model.histogram.BlueHistogram;
import model.histogram.GreenHistogram;
import model.histogram.IntensityHistogram;
import model.histogram.RedHistogram;
import utils.ConvertToBufferImage;

/**
 * class IOwnListener represent a customized ActionListener.
 * It implements {@link IOwnListener}.
 */
public class OwnListener implements IOwnListener {

  private final Stack<RunnableAction> actions;
  private final Map<String, RunnableAction> actionMap;
  private final IView view;

  private final IModel model;
  private String filePathLoad;
  private String filePathSave;

  private boolean previewSelected;

  private int previewX;
  private int previewY;

  /**
   * Constructor of OwnListener.
   * @param actions represent tracing stack of actions.
   * @param actionMap represent possible actions.
   * @param view represent the view corresponding to this listener.
   */
  public OwnListener(Stack<RunnableAction> actions, Map<String, RunnableAction> actionMap,
                     IView view, IModel model) {
    this.actions = actions;
    this.actionMap = actionMap;
    this.view = Objects.requireNonNull(view);
    this.model = Objects.requireNonNull(model);
    this.filePathSave = "";
    this.filePathLoad = "";
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (command.equals("load")) {
      String temp = this.view.setLoadDisplayVisible();
      if(!temp.isEmpty()){
        this.filePathLoad = temp;
      }
    }

    String returnInput = this.view.setInputDisplayVisible(e.getActionCommand());

    if (this.filePathLoad.isEmpty()) {
      this.view.alert("No photo has been rendered, please load one first.");
    } else {

      RunnableAction actionPerformed = this.actionMap.getOrDefault(command, null);

      if (actionPerformed == null) {
        this.view.alert("This Functionality not supported!");
      } else {
        try {
          String input = "";
          if (e.getActionCommand().equals("save")) {
            this.filePathSave = this.view.setSaveDisplayVisible();
            input = this.filePathSave + " " + this.filePathLoad;
          } else if (e.getActionCommand().equals("load")) {
            input = this.filePathLoad + " " + this.filePathLoad;
          } else {
            if (this.previewSelected) {
              input = returnInput + " " + this.filePathLoad + " "
                      + this.filePathLoad.concat("-mask") + " "
                      + this.filePathLoad.concat("-preview");
            } else {
              input = returnInput + " " + this.filePathLoad + " " + this.filePathLoad;
            }
          }

          actionPerformed.setScanner(new Scanner(new StringReader(input)));

          if (this.previewSelected && !e.getActionCommand().equals("load")
                  && !e.getActionCommand().equals("save")){
            actionPerformed.setRunConfigure(true);
            actionPerformed.run();
            this.view.refreshPreview(ConvertToBufferImage.intListToBufferImage(
                    this.model.getFromMap(filePathLoad.concat("-preview"))));
          } else if (!e.getActionCommand().equals("save") || !filePathSave.isEmpty()) {
            actionPerformed.setRunConfigure(false);
            actionPerformed.run();
            this.actions.add(actionPerformed);
            Map<String, Map<Integer, Integer>> histogramMap = new HashMap<>();
            histogramMap.put("red",this.model.apply(new RedHistogram(filePathLoad)));
            histogramMap.put("green",this.model.apply(new GreenHistogram(filePathLoad)));
            histogramMap.put("blue",this.model.apply(new BlueHistogram(filePathLoad)));
            histogramMap.put("intensity",this.model.apply(new IntensityHistogram(filePathLoad)));
            this.view.clearPreview();
            this.view.refresh(ConvertToBufferImage.intListToBufferImage(
                    this.model.getFromMap(filePathLoad)), histogramMap);
          }

        } catch (IllegalArgumentException exception) {
          this.view.alert("error: missing or invalid input for operation");
        }
      }
    }
  }

  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if(this.previewSelected && !this.filePathLoad.isEmpty()){
      int x = e.getX() ;
      int y = e.getY() ;

      int [][] image = this.model.getFromMap(filePathLoad);
      int width = image[0][0];
      int height = image[0][1];
      if(x+200 > width){
        if(width <= 200){
          x = 0;
        } else {
          x = width - 201;
        }
      }
      if(y+200 > height){
        if(height <= 200){
          y = 0;
        } else {
          y = height - 201;
        }
      }

      this.view.setPreviewLocation(x, y);

      this.model.apply(new CreateWindow(this.filePathLoad, this.filePathLoad.concat("-mask"),
              this.filePathLoad.concat("-preview"), x, y ));
      this.view.refreshPreview(ConvertToBufferImage.intListToBufferImage(
              this.model.getFromMap(filePathLoad.concat("-preview"))));
    }
}

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Invoked when the mouse enters a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
  }

  /**
   * Invoked when an item has been selected or deselected by the user.
   * The code written for this method performs the operations
   * that need to occur when an item is selected (or deselected).
   *
   * @param e the event to be processed
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
   if( e.getStateChange() == ItemEvent.SELECTED ) {
     if (this.filePathLoad.isEmpty()) {
       this.view.alert("No photo has been rendered, please load one first.");
       this.previewSelected = false;
     }else{
       this.previewSelected = true;
     }
   }else{
     this.previewSelected = false;
     this.view.clearPreview();
   }
  }
}
