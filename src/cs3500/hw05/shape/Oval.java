package cs3500.hw05.shape;

import cs3500.hw05.Color;
import cs3500.hw05.Posn;

/**
 * Represents a standard oval shape with a basic x radius and y radius.
 */
public class Oval extends AbstractShape {
  public static final String ERROR_RADIUS_BOUNDS =
          "The radius must be greater than 0.";

  public double radiusX;
  public double radiusY;

  /**
   * Constructs an oval with the given x radius and y radius.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param name the object's name
   * @param location the object's location on a canvas
   * @param color the object's color
   * @param radiusX the x radius of the oval
   * @param radiusY the y radius of the oval
   * @throws IllegalArgumentException if the x radius or y radius is 0 or less
   */
  public Oval(int startTime, int endTime, String name, Posn location, Color color,
              double radiusX, double radiusY) {
    super(startTime, endTime, name, location, color);

    if (radiusX <= 0 || radiusY <= 0) {
      throw new IllegalArgumentException(ERROR_RADIUS_BOUNDS);
    }

    this.radiusX = radiusX;
    this.radiusY = radiusY;
  }

  /**
   * A getter for the x radius.
   *
   * @return the x radius value
   */
  public double getRadiusX() {
    return this.radiusX;
  }

  /**
   * A getter for the y radius.
   *
   * @return the y radius value
   */
  public double getRadiusY() {
    return this.radiusY;
  }
}
