package cs3500.hw05.animation;

import java.util.Objects;

import cs3500.hw05.AbstractCanvasObject;
import cs3500.hw05.shape.AbstractShape;

/**
 * Represents an abstract animation that can be placed in a canvas.
 */
public abstract class AbstractAnimation extends AbstractCanvasObject {
  public static final String ERROR_NULL_SHAPE =
          "The shape cannot be null.";
  public static final String ERROR_START_TOO_EARLY =
          "The animation cannot start before the shape appears";
  public static final String ERROR_START_TOO_LATE =
          "The animation cannot start after the shape has disappeared";
  public static final String ERROR_NULL_ANIMATION =
          "The animation cannot be null.";

  AbstractShape shape;

  /**
   * Constructs an animation that can be applied on the given shape.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @throws IllegalArgumentException if the shape is null
   */
  public AbstractAnimation(int startTime, int endTime, AbstractShape shape) {
    super(startTime, endTime);

    if (shape == null) {
      throw new IllegalArgumentException(ERROR_NULL_SHAPE);
    }

    if (this.getStartTime() < shape.getStartTime()) {
      throw new IllegalArgumentException(ERROR_START_TOO_EARLY);
    }

    if (this.getStartTime() > shape.getEndTime()) {
      throw new IllegalArgumentException(ERROR_START_TOO_LATE);
    }

    this.shape = shape;
  }

  /**
   * Checks whether this animation makes for an incompatible move with the other animation, meaning
   * that both animations perform the same type of alteration on the same shape at overlapping
   * times.
   *
   * @param other the animation to check this one against
   *                  @throws IllegalArgumentException if the given animation is null
   */
  public boolean conflictsWithAnimation(AbstractAnimation other) {
    if (other == null) {
      throw new IllegalArgumentException(ERROR_NULL_ANIMATION);
    }

    // check if the shapes that the animations are operating on are different
    if (this.shape != other.shape) {
      return false;
    }

    // check if the two animations are being performed at different times
    if (!this.timeOverlaps(other)) {
      return false;
    }

    // check if the two animations are different types of animations
    if (!this.sameType(other)) {
      return false;
    }

    return true;
  }

  /**
   * Checks whether the times of the two animations overlap one another at all, exclusively.
   *
   * @param other the animation to check this one against
   * @return true if the execution periods overlap at all, false otherwise
   */
  public boolean timeOverlaps(AbstractAnimation other) {
    return this.getStartTime() < other.getEndTime() && this.getEndTime() > other.getStartTime();
  }

  /**
   * Checks whether the given anmiation type is the same type as this animation type.
   *
   * @param other the animatino to check this one against
   * @return true if the animation types are the same, false otherwise
   */
  public abstract boolean sameType(AbstractAnimation other);
}
