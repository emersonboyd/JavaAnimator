package cs3500.hw05.shape;

import cs3500.hw05.Color;
import cs3500.hw05.Posn;

/**
 * Represents a standard rectangle shape with a basic width and height.
 */
public class Rectangle extends AbstractShape {
  public static final String ERROR_WIDTH_BOUNDS =
          "Width must be greater than 0.";
  public static final String ERROR_HEIGHT_BOUNDS =
          "Height must be greater than 0.";

  private double width;
  private double height;

  /**
   * Constructs a rectangle with the given width and height.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param name the object's name
   * @param location the object's location on a canvas
   * @param color the object's color
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   * @throws IllegalArgumentException if the width or height is 0 or less
   */
  public Rectangle(int startTime, int endTime, String name, Posn location, Color color,
                   double width, double height) {
    super(startTime, endTime, name, location, color);

    if (width <= 0) {
      throw new IllegalArgumentException(ERROR_WIDTH_BOUNDS);
    }
    if (height <= 0) {
      throw new IllegalArgumentException(ERROR_HEIGHT_BOUNDS);
    }

    this.width = width;
    this.height = height;
  }

  /**
   * A getter for the width.
   *
   * @return the width value
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * A getter for the height.
   *
   * @return the height value
   */
  public double getHeight() {
    return this.height;
  }
}
