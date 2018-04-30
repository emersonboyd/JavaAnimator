package cs3500.animator.object.animation;

import cs3500.animator.object.shape.IShape;
import cs3500.animator.object.shape.Oval;
import cs3500.animator.object.shape.Rectangle;

/**
 * Represents a scaling animation to resize an object based on a scaling factor.
 */
public class Scale extends AbstractAnimation {
  public static final String ERROR_SCALE_FACTOR_BOUNDS =
          "The scale factor must be greater than 0.";

  private double startWidth;
  private double endWidth;
  private double startHeight;
  private double endHeight;

  /**
   * Constructs an scale animation with a given scale factor.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @param startWidth the width of the shape at the start of the animation
   * @param startHeight the height of the shape at the start of the animation
   * @param endWidth    the end width of the shape
   * @param endHeight    the end width of the shape
   * @throws IllegalArgumentException if the either scaleX or scaleY is null
   */
  public Scale(int startTime, int endTime, IShape shape, double startWidth, double startHeight,
               double endWidth, double endHeight) throws IllegalArgumentException {
    super(startTime, endTime, shape);

    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
  }
  @Override
  public void animate(IShape s) {
    s.scale(this.getScaleX(), this.getScaleY());
  }

  @Override
  public void animate(int ticksElapsed) {
    double newWidth = this.startWidth * this.getStartCoef(ticksElapsed)
            + this.endWidth * this.getEndCoef(ticksElapsed);
    double newHeight = this.startHeight * this.getStartCoef(ticksElapsed)
            + this.endHeight * this.getEndCoef(ticksElapsed);
    shape.updateSize(newWidth, newHeight);
  }

  @Override
  public boolean sameType(IAnimation other) {
    return other instanceof Scale;
  }

  @Override
  public String getType() {
    return "scale";
  }

  @Override
  public String getAction(IShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("scales from ")
            .append(s.getSizeDescription())
            .append(" to ")
            .append(s.getSizeDescriptionWithScale(this.getScaleX(), this.getScaleY()));

    return builder.toString();
  }

  @Override
  public double getStartWidth() {
    return this.startWidth;
  }

  @Override
  public double getEndWidth() {
    return this.endWidth;
  }

  @Override
  public double getStartHeight() {
    return this.startHeight;
  }

  @Override
  public double getEndHeight() {
    return this.endHeight;
  }

  /**
   * Get x scale factor
   * @return x scale factor
   */
  private double getScaleX() {
    return endWidth / startWidth;
  }

  /**
   * Get y scale factor
   * @return y scale factor
   */
  private double getScaleY() {
    return endHeight / startHeight;
  }
}
