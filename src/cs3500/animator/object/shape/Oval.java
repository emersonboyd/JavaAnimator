package cs3500.animator.object.shape;

import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;

/**
 * Represents a standard oval shape with a basic x radius and y radius.
 */
public class Oval extends AbstractShape {
  public static final String ERROR_RADIUS_BOUNDS =
          "The radius must be greater than 0.";

  private double originalRadiusX;
  private double originalRadiusY;
  private double radiusX;
  private double radiusY;

  /**
   * Constructs an oval with the given x radius and y radius.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param name      the object's name
   * @param location  the object's location on a canvas
   * @param color     the object's color
   * @param radiusX   the x radius of the oval
   * @param radiusY   the y radius of the oval
   * @throws IllegalArgumentException if the x radius or y radius is 0 or less
   */
  public Oval(int startTime, int endTime, String name, Posn location, IColor color,
              double radiusX, double radiusY) throws IllegalArgumentException {
    super(startTime, endTime, name, location, color);

    if (radiusX <= 0 || radiusY <= 0) {
      throw new IllegalArgumentException(ERROR_RADIUS_BOUNDS);
    }

    this.originalRadiusX = radiusX;
    this.originalRadiusY = radiusY;
    this.radiusX = radiusX;
    this.radiusY = radiusY;
  }

  @Override
  public String getType() {
    return "oval";
  }

  @Override
  public String getAttributes() {
    StringBuilder builder = new StringBuilder();

    builder.append("Center: ")
            .append(this.getLocation().toString())
            .append(", ")
            .append(this.getSizeDescription())
            .append(", Color: ")
            .append(this.getColor().toString());

    return builder.toString();
  }

  @Override
  public String getSizeDescriptionWithScale(double scaleX, double scaleY) {
    StringBuilder builder = new StringBuilder();

    builder.append("X radius: ")
            .append(this.radiusX * scaleX)
            .append(", Y radius: ")
            .append(this.radiusY * scaleY);

    return builder.toString();
  }

  @Override
  public Oval clone() {
    return new Oval(this.getStartTime(), this.getEndTime(), this.getName(), this.getLocation(),
            this.getColor(), this.radiusX, this.radiusY);
  }

  @Override
  public void scale(double scaleX, double scaleY) {
    this.radiusX = this.radiusX * scaleX;
    this.radiusY = this.radiusY * scaleY;
  }

  @Override
  public void updateSize(double width, double height) {
    this.radiusX = width;
    this.radiusY = height;
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

  @Override
  public void reset() {
    super.reset();
    this.radiusX = originalRadiusX;
    this.radiusY = originalRadiusY;
  }

  @Override
  public double getSizeX() {
    return this.getRadiusX();
  }

  @Override
  public double getSizeY() {
    return this.getRadiusY();
  }

  @Override
  public double getOriginalX() {
    return this.originalRadiusX;
  }

  @Override
  public double getOriginalY() {
    return this.originalRadiusY;
  }

  @Override
  public String getRotationDescriptionWithTheta(double theta) {
    StringBuilder builder = new StringBuilder();

    builder.append(theta)
            .append("° at (")
            .append(this.getLocation().getX())
            .append(",")
            .append(this.getLocation().getY())
            .append(")");
    return builder.toString();
  }

}
