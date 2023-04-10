package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.JPanel;

/**
 * the Image panel that demonstrates the image the user is working on.
 */
public class Image extends JPanel {
  private BufferedImage image;

  /**
   * Constructor for the image panel.
   */
  public Image() {
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) {
      g.drawImage(this.image, 0, 0, null);
    }
  }

  /**
   * Refreshes the image panel after moves.
   *
   * @param image the new image
   */
  public void reset(BufferedImage image) {
    this.image = Objects.requireNonNull(image);
    this.setPreferredSize(new Dimension(image.getWidth(), image.getWidth()));
    this.revalidate();
    this.repaint();
  }

  /**
   * Clear the image panel.
   */
  public void clear() {
    this.image = null;
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    this.revalidate();
    this.repaint();
  }
}
