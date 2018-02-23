package cs3500.hw05.shape;

import cs3500.hw05.AbstractCanvasObject;
import cs3500.hw05.Color;
import cs3500.hw05.Posn;

/**
 * Represents an abstract shape that can be placed in a canvas.
 */
public abstract class AbstractShape extends AbstractCanvasObject {
  public static final String ERROR_NAME_NULL =
          "The name cannot be null.";
  public static final String ERROR_LOCATION_NULL =
          "The location cannot be null.";
  public static final String ERROR_COLOR_NULL =
          "The color cannot be null.";

  private String name;
  private Posn location;
  private Color color;

  /**
   * Constructs a shape with the given name and position.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param name      the object's name
   * @param location  the object's location on a canvas
   * @param color     the object's color
   * @throws IllegalArgumentException if the name, location or color is null
   */
  public AbstractShape(int startTime, int endTime, String name, Posn location, Color color) {
    super(startTime, endTime);

    if (name == null) {
      throw new IllegalArgumentException(ERROR_NAME_NULL);
    }
    if (location == null) {
      throw new IllegalArgumentException(ERROR_LOCATION_NULL);
    }
    if (color == null) {
      throw new IllegalArgumentException(ERROR_COLOR_NULL);
    }

    this.name = name;
    this.location = location;
    this.color = color;
  }

  /**
   * A getter for the name.
   *
   * @return the name String
   */
  public String getName() {
    return this.name;
  }

  /**
   * A getter for the location.
   *
   * @return the location Posn
   */
  public Posn getLocation() {
    return this.location;
  }

  /**
   * A getter for the color.
   *
   * @return the color object
   */
  public Color getColor() {
    return this.color;
  }
}
