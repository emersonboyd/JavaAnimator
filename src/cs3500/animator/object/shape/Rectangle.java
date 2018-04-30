package cs3500.animator.object.shape;

import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;
import cs3500.animator.util.NumUtil;

/**
 * Represents a standard rectangle shape with a basic width and height.
 */
public class Rectangle extends AbstractShape {
  public static final String ERROR_WIDTH_BOUNDS =
          "Width must be greater than 0.";
  public static final String ERROR_HEIGHT_BOUNDS =
          "Height must be greater than 0.";

  private double originalWidth;
  private double originalHeight;
  private double width;
  private double height;

  /**
   * Constructs a rectangle with the given width and height.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param name      the object's name
   * @param location  the object's location on a canvas
   * @param color     the object's color
   * @param width     the width of the rectangle
   * @param height    the height of the rectangle
   * @throws IllegalArgumentException if the width or height is 0 or less
   */
  public Rectangle(int startTime, int endTime, String name, Posn location, IColor color,
                   double width, double height) throws IllegalArgumentException {
    super(startTime, endTime, name, location, color);

    if (width <= 0) {
      throw new IllegalArgumentException(ERROR_WIDTH_BOUNDS);
    }
    if (height <= 0) {
      throw new IllegalArgumentException(ERROR_HEIGHT_BOUNDS);
    }

    this.originalWidth = width;
    this.originalHeight = height;
    this.width = width;
    this.height = height;
  }

  @Override
  public String getType() {
    return "rectangle";
  }

  @Override
  public Rectangle clone() {
    return new Rectangle(this.getStartTime(), this.getEndTime(), this.getName(), this.getLocation(),
            this.getColor(), this.width, this.height);
  }

  @Override
  public void scale(double scaleX, double scaleY) {
    this.width = this.width * scaleX;
    this.height = this.height * scaleY;
  }

  @Override
  public void updateSize(double width, double height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public String getAttributes() {
    StringBuilder builder = new StringBuilder();

    builder.append("Min-corner: ")
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

    builder.append("Width: ")
            .append(this.width * scaleX)
            .append(", Height: ")
            .append(this.height * scaleY);

    return builder.toString();
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

  @Override
  public void reset() {
    super.reset();

    this.width = originalWidth;
    this.height = originalHeight;
  }

  @Override
  public double getSizeX() {
    return this.getWidth();
  }

  @Override
  public double getSizeY() {
    return this.getHeight();
  }

  @Override
  public double getOriginalX() {
    return this.originalWidth;
  }

  @Override
  public double getOriginalY() {
    return this.originalHeight;
  }

  @Override
  public String getRotationDescriptionWithTheta(double theta) {
    StringBuilder builder = new StringBuilder();

    builder.append(theta)
            .append("° at (")
            .append(this.getLocation().getX() + NumUtil.round(this.width / 2))
            .append(",")
            .append(this.getLocation().getY() + NumUtil.round(this.height / 2))
            .append(")");
    return builder.toString();
  }

}
