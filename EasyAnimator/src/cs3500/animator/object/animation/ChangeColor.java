package cs3500.animator.object.animation;

import cs3500.animator.object.Color;
import cs3500.animator.object.IColor;
import cs3500.animator.object.shape.IShape;

/**
 * Represents a color change animation to alter an object's color to a endColor color.
 */
public class ChangeColor extends AbstractAnimation {
  public static final String ERROR_TARGET_NULL =
          "Target cannot be null.";

  private IColor startColor;
  private IColor endColor;

  /**
   * Constructs an scale animation with a given endColor color.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @param startColor the color of the shape at the start of the animation
   * @param endColor    the color to change the shape to
   * @throws IllegalArgumentException if the endColor is null
   */
  public ChangeColor(int startTime, int endTime, IShape shape, IColor startColor, IColor endColor)
          throws IllegalArgumentException {
    super(startTime, endTime, shape);

    if (endColor == null) {
      throw new IllegalArgumentException(ERROR_TARGET_NULL);
    }

    this.startColor = startColor;
    this.endColor = endColor;
  }

  @Override
  public void animate(IShape s) {
    s.changeColor(this.endColor);
  }

  @Override
  public void animate(int ticksElapsed) {
    double newR = this.startColor.getRed() * this.getStartCoef(ticksElapsed)
            + this.endColor.getRed() * this.getEndCoef(ticksElapsed);
    double newG = this.startColor.getGreen() * this.getStartCoef(ticksElapsed)
            + this.endColor.getGreen() * this.getEndCoef(ticksElapsed);
    double newB = this.startColor.getBlue() * this.getStartCoef(ticksElapsed)
            + this.endColor.getBlue() * this.getEndCoef(ticksElapsed);
    shape.changeColor(new Color(newR, newG, newB));
  }

  @Override
  public boolean sameType(IAnimation other) {
    return other instanceof ChangeColor;
  }

  @Override
  public String getType() {
    return "change color";
  }

  @Override
  public String getAction(IShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("changes color from ")
            .append(s.getColor().toString())
            .append(" to ")
            .append(endColor.toString());

    return builder.toString();
  }

  @Override
  public IColor getStartColor() {
    return this.startColor;
  }

  @Override
  public IColor getEndColor() {
    return this.endColor;
  }
}
