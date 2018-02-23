package cs3500.hw05.animation;

import cs3500.hw05.Color;
import cs3500.hw05.Posn;
import cs3500.hw05.shape.AbstractShape;

/**
 * Represents a color change animation to alter an object's color to a target color.
 */
public class ChangeColor extends AbstractAnimation {
  public static final String ERROR_TARGET_NULL =
          "Target cannot be null.";

  Color target;

  /**
   * Constructs an scale animation with a given scale factor.
   *
   * @param startTime   the time to show the object
   * @param endTime     the time to hide the object
   * @param shape       the shape to apply the animation to
   * @param target the color to change the shape to
   * @throws IllegalArgumentException if the target is null
   */
  public ChangeColor(int startTime, int endTime, AbstractShape shape, Color target) {
    super(startTime, endTime, shape);

    if (target == null) {
      throw new IllegalArgumentException(ERROR_TARGET_NULL);
    }

    this.target = target;
  }

  /**
   * A getter for the target.
   *
   * @return the target color
   */
  public Color getTarget() {
    return this.target;
  }

  @Override
  public boolean sameType(AbstractAnimation other) {
    return other instanceof ChangeColor;
  }
}
