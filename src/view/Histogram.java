package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;

/**
 * A panel that extends JPanel which represents a line chart Histogram given a HashMap and
 * the color in String.
 */
public class Histogram extends JPanel {
  private HashMap<Integer, Integer> map;
  private String color;
  int max;

  /**
   * Constructor for the Histogram JPanel.
   */
  public Histogram() {
    this.map = new HashMap<>();
    this.color = "";
    for (int i = 0; i < 256; i++) {
      this.map.put(i, 0);
    }
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
  }

  /**
   * Paints the customized graphic.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int gap = 20;
    int factor = 1;

    switch (color.toLowerCase()) {
      case "red":
        g.setColor(Color.red);
        break;
      case "green":
        g.setColor(Color.green);
        break;
      case "blue":
        g.setColor(Color.blue);
        break;
      case "intensity":
        g.setColor(Color.black);
        break;
      default:
        g.setColor(Color.WHITE);
    }

    for (int i = 1; i < 256; i++) {
      int before = this.map.get(i - 1);
      int after = this.map.get(i);
      g.drawLine(gap + factor * (i - 1), this.getHeight() - gap - before * factor,
              gap + factor * i, this.getHeight() - gap - after * factor);
    }

    //create x and y-axis
    g.setColor(Color.BLACK);
    g.drawLine(gap, this.getHeight() - gap, gap + 256 * factor, this.getHeight() - gap);
    if (max == 0) {
      g.drawLine(gap, this.getHeight() - gap, gap, this.getHeight() - gap - 256);
      for (int i = 10; i < 256; i = i + 10) {
        g.drawLine(gap, this.getHeight() - gap - i * factor,
                gap - factor * 3, this.getHeight() - gap - i * factor);
      }
    } else {
      g.drawLine(gap, this.getHeight() - gap,
              gap, this.getHeight() - gap - this.max * factor);
      for (int i = 10; i < max; i = i + 10) {
        g.drawLine(gap, this.getHeight() - gap - i * factor,
                gap - factor * 3, this.getHeight() - gap - i * factor);
      }

    }
    for (int i = 10; i < 256; i = i + 10) {
      g.drawLine(gap + i * factor, this.getHeight() - gap,
              gap + i * factor, this.getHeight() - gap - factor * factor);
    }
  }

  /**
   * Refreshes the histogram panel after the user moves.
   *
   * @param map   the new map of frequencies
   * @param color the color of the line
   */
  public void reset(Map<Integer, Integer> map, String color) {
    this.map = new HashMap<>(Objects.requireNonNull(map));
    this.color = Objects.requireNonNull(color);
    this.setBackground(Color.WHITE);

    for (int i = 0; i < 256; i++) {
      max = Math.max(max, this.map.get(i));
    }

    // Need to adjust according to the window size!!
    this.setPreferredSize(new Dimension(300, this.max + 40));
    this.revalidate();
    this.repaint();
  }
}
