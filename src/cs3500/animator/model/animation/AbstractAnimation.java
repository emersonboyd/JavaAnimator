package cs3500.animator.model.animation;

import cs3500.animator.model.AbstractCanvasObject;
import cs3500.animator.model.shape.AbstractShape;

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

  protected AbstractShape shape;
  protected boolean animationStarted;


  /**
   * Constructs an animation that can be applied on the given shape.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @throws IllegalArgumentException if the shape is null
   */
  public AbstractAnimation(int startTime, int endTime, AbstractShape shape) throws
          IllegalArgumentException {
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
    this.animationStarted = false;
  }

  /**
   * Checks whether this animation makes for an incompatible move with the other animation, meaning
   * that both animations perform the same type of alteration on the same shape at overlapping
   * times.
   *
   * @param other the animation to check this one against
   * @throws IllegalArgumentException if the given animation is null
   */
  public boolean conflictsWithAnimation(AbstractAnimation other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException(ERROR_NULL_ANIMATION);
    }

    // check if the shapes that the animations are operating on are different
    if (!this.shape.equals(other.shape)) {
      return false;
    }

    // check if the two animations are being performed at different times
    if (!this.timeOverlaps(other)) {
      return false;
    }

    // if the two animations are the same type of animation, then they conflict
    return this.sameType(other);
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
   * Performs the full animation on the given shape by altering the shape the necessary amount.
   *
   * @param s the shape to animate
   */
  public abstract void animate(AbstractShape s);

  /**
   * Performs a fraction of an animation based on ticks elapsed.
   *
   * @param ticksElapsed total ticks elapsed
   */
  public abstract void animate(int ticksElapsed);

  /**
   * Checks whether the given anmiation type is the same type as this animation type.
   *
   * @param other the animatino to check this one against
   * @return true if the animation types are the same, false otherwise
   */
  public abstract boolean sameType(AbstractAnimation other);

  /**
   * A getter for the shape.
   *
   * @return this object's shape
   */
  public AbstractShape getShape() {
    return this.shape;
  }

  /**
   * Produces an action descriptor of what this animation does.
   *
   * @param s the shape that the action is being performed on
   * @return the action string
   */
  public abstract String getAction(AbstractShape s);

  /**
   * Produces a string describing this animation's actions.
   *
   * @param s the shape that the action is being performed on
   * @return the animation string description
   */
  public String toString(AbstractShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("Shape ")
            .append(s.getName())
            .append(" ")
            .append(this.getAction(s))
            .append(" from t=")
            .append(this.getStartTime())
            .append(" to t=")
            .append(this.getEndTime());

    return builder.toString();
  }

  /**
   * Calculates the starting coefficient for any temporal animation.
   *
   * @param ticksElapsed total ticks elapsed
   * @return the starting coefficient
   */
  protected double getStartCoef(int ticksElapsed) {
    return ((double) (this.getEndTime() - ticksElapsed)
            / (this.getEndTime() - this.getStartTime()));
  }

  /**
   * Calculates the ending coefficient for any temporal animation.
   *
   * @param ticksElapsed total ticks elapsed
   * @return the ending coefficient
   */
  protected double getEndCoef(int ticksElapsed) {
    return ((double) (ticksElapsed - this.getStartTime())
            / (this.getEndTime() - this.getStartTime()));
  }


}
