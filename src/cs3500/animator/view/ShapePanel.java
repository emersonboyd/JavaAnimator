package cs3500.animator.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.*;

import cs3500.animator.object.shape.IShape;
import cs3500.animator.util.NumUtil;

/**
 * This class represents a panel to draw a list of shapes. It can also can show a list of
 * animations.
 */
public class ShapePanel extends JPanel {

  private List<IShape> listOfShapes;

  /**
   * Constructs a panel for the animation of shapes.
   * @param listOfShapes the list of shapes to be drawn
   */
  ShapePanel(List<IShape> listOfShapes) {
    this.listOfShapes = listOfShapes;
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    super.paintComponent(g);

    for (IShape shape : listOfShapes) {
      int minX = (int) shape.getLocation().getX();
      int minY = (int) shape.getLocation().getY();
      g2D.setColor(shape.getColor().transformToAwt());

      if (shape.getType().equalsIgnoreCase("rectangle")) {
        int centerX = minX + NumUtil.round(shape.getSizeX() / 2);
        int centerY = minY + NumUtil.round(shape.getSizeY() / 2);
        AffineTransform rot = AffineTransform.getRotateInstance(Math.toRadians(shape.getTheta()),
                centerX, centerY);
        g2D.setTransform(rot);

        g2D.draw3DRect(minX, minY, NumUtil.round(shape.getSizeX()), NumUtil.round(shape.getSizeY()), true);
        g2D.fill3DRect(minX, minY, NumUtil.round(shape.getSizeX()), NumUtil.round(shape.getSizeY()), true);
      }
      else  if (shape.getType().equalsIgnoreCase("oval")) {
        int roundedRadiusX = NumUtil.round(shape.getSizeX());
        int roundedRadiusY = NumUtil.round(shape.getSizeY());
        int roundedWidth = NumUtil.round(shape.getSizeX() * 2);
        int roundedHeight = NumUtil.round(shape.getSizeY() * 2);
        minX = minX - roundedRadiusX;
        minY = minY - roundedRadiusY;

        int centerX = (int) shape.getLocation().getX();
        int centerY = (int) shape.getLocation().getY();
        AffineTransform rot = AffineTransform.getRotateInstance(Math.toRadians(shape.getTheta()),
                centerX, centerY);
        g2D.setTransform(rot);

        g2D.drawOval(minX, minY, roundedWidth, roundedHeight);
        g2D.fillOval(minX, minY, roundedWidth, roundedHeight);
      }
    }
  }
}
