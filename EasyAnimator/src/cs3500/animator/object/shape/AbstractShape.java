package cs3500.animator.object.shape;

import cs3500.animator.object.AbstractCanvasObject;
import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;

/**
 * Represents an abstract shape that can be placed in a canvas.
 */
public abstract class AbstractShape extends AbstractCanvasObject implements IShape {
  public static final String ERROR_NAME_NULL =
          "The name cannot be null.";
  public static final String ERROR_LOCATION_NULL =
          "The location cannot be null.";
  public static final String ERROR_COLOR_NULL =
          "The color cannot be null.";
  public static final String ERROR_NAME_HAS_SPACE =
          "The name cannot have a space in it.";

  private String name;
  private Posn originalLocation;
  private Posn location;
  private IColor originalColor;
  private IColor color;
  private double originalTheta;
  private double theta;


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
  public AbstractShape(int startTime, int endTime, String name, Posn location, IColor color)
          throws IllegalArgumentException {
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
    if (name.contains(" ")) {
      throw new IllegalArgumentException(ERROR_NAME_HAS_SPACE);
    }

    this.originalLocation = location;
    this.originalColor = color;
    this.name = name;
    this.location = location;
    this.color = color;
    this.originalTheta = 0;
    this.theta = 0;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Posn getLocation() {
    return this.location;
  }

  @Override
  public IColor getColor() {
    return this.color;
  }

  @Override
  public double getTheta() {
    return this.theta;
  }

  @Override
  public abstract String getType();

  @Override
  public abstract String getAttributes();

  @Override
  public abstract String getSizeDescriptionWithScale(double scaleX, double scaleY);

  @Override
  public abstract IShape clone();

  @Override
  public String getSizeDescription() {
    return getSizeDescriptionWithScale(1.0, 1.0);
  }

  @Override
  public void move(Posn newLocation) {
    this.location = newLocation;
  }

  @Override
  public abstract void scale(double scaleX, double scaleY);

  @Override
  public abstract void updateSize(double width, double height);

  @Override
  public void changeColor(IColor newColor) {
    this.color = newColor;
  }

  @Override
  public void setRotation(double newTheta) {
    this.theta = newTheta;
  }

  @Override
  public boolean equals(Object other) {
    // two shapes should only be considered the same if they are the same in memory
    return this == other;
  }

  @Override
  public int hashCode() {
    return this.getName().hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("Name: ").append(this.name);
    builder.append("\n");
    builder.append("Type: ").append(this.getType());
    builder.append("\n");
    builder.append(this.getAttributes());
    builder.append("\n");
    builder.append("Appears at t=").append(this.getStartTime());
    builder.append("\n");
    builder.append("Disappears at t=").append(this.getEndTime());

    return builder.toString();
  }

  @Override
  public void reset() {
    this.location = originalLocation;
    this.color = originalColor;
    this.theta = originalTheta;
  }

  @Override
  public abstract double getSizeX();

  @Override
  public abstract double getSizeY();

  @Override
  public Posn getOriginalLocation() {
    return this.originalLocation;
  }

  @Override
  public IColor getOriginalColor() {
    return this.originalColor;
  }

  @Override
  public double getOriginalTheta() {
    return this.originalTheta;
  }

  @Override
  public String getRotationDescription() {
    return getRotationDescriptionWithTheta(this.theta);
  }

  @Override
  public abstract String getRotationDescriptionWithTheta(double theta);
}
