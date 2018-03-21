package cs3500.animator.view;

import java.awt.Graphics;

import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.util.NumUtil;

/**
 * This class represents a panel to draw a list of shapes. It can also can show a list of
 * animations.
 */
public class ShapePanel extends JPanel {

  private List<AbstractShape> listOfShapes;

  /**
   * Constructs a panel for the animation of shapes.
   * @param listOfShapes the list of shapes to be drawn
   */
  ShapePanel(List<AbstractShape> listOfShapes) {
    this.listOfShapes = listOfShapes;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (AbstractShape shape : listOfShapes) {
      int x = (int) shape.getLocation().getX();
      int y = (int) shape.getLocation().getY();
      g.setColor(shape.getColor().transformToAwt());

      if (shape instanceof Rectangle) {
        Rectangle rect = (Rectangle) shape;
        g.draw3DRect(x, y, NumUtil.round(rect.getWidth()), NumUtil.round(rect.getHeight()), true);
        g.fill3DRect(x, y, NumUtil.round(rect.getWidth()), NumUtil.round(rect.getHeight()), true);
      }
      else  if (shape instanceof Oval) {
        Oval oval = (Oval) shape;

        int roundedRadiusX = NumUtil.round(oval.getRadiusX());
        int roundedRadiusY = NumUtil.round(oval.getRadiusY());
        int roundedWidth = NumUtil.round(oval.getRadiusX() * 2);
        int roundedHeight = NumUtil.round(oval.getRadiusY() * 2);
        x = x - roundedRadiusX;
        y = y - roundedRadiusY;

        g.drawOval(x, y, roundedWidth, roundedHeight);
        g.fillOval(x, y, roundedWidth, roundedHeight);
      }
    }
  }
}
