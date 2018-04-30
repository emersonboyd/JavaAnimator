package cs3500.animator.object.animation;

import cs3500.animator.object.AbstractCanvasObject;
import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;
import cs3500.animator.object.shape.IShape;

/**
 * Represents an abstract animation that can be placed in a canvas.
 */
public abstract class AbstractAnimation extends AbstractCanvasObject implements IAnimation {
  public static final String ERROR_NULL_SHAPE =
          "The shape cannot be null.";
  public static final String ERROR_START_TOO_EARLY =
          "The animation cannot start before the shape appears";
  public static final String ERROR_START_TOO_LATE =
          "The animation cannot start after the shape has disappeared";
  public static final String ERROR_NULL_ANIMATION =
          "The animation cannot be null.";

  protected IShape shape;


  /**
   * Constructs an animation that can be applied on the given shape.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @throws IllegalArgumentException if the shape is null
   */
  public AbstractAnimation(int startTime, int endTime, IShape shape) throws
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
  }

  @Override
  public boolean conflictsWithAnimation(IAnimation other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException(ERROR_NULL_ANIMATION);
    }

    // check if the shapes that the animations are operating on are different
    if (!this.shape.equals(other.getShape())) {
      return false;
    }

    // check if the two animations are being performed at different times
    if (!this.timeOverlaps(other)) {
      return false;
    }

    // if the two animations are the same type of animation, then they conflict
    return this.sameType(other);
  }

  @Override
  public boolean timeOverlaps(IAnimation other) {
    return this.getStartTime() < other.getEndTime() && this.getEndTime() > other.getStartTime();
  }

  @Override
  public abstract void animate(IShape s);

  @Override
  public abstract void animate(int ticksElapsed);

  @Override
  public abstract boolean sameType(IAnimation other);

  @Override
  public IShape getShape() {
    return this.shape;
  }

  @Override
  public abstract String getAction(IShape s);

  @Override
  public String toString(IShape s) {
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

  @Override
  public double getStartCoef(int ticksElapsed) {
    return ((double) (this.getEndTime() - ticksElapsed)
            / (this.getEndTime() - this.getStartTime()));
  }

  @Override
  public double getEndCoef(int ticksElapsed) {
    return ((double) (ticksElapsed - this.getStartTime())
            / (this.getEndTime() - this.getStartTime()));
  }

  @Override
  public Posn getStartPosition() {
    throw new UnsupportedOperationException("No start position.");
  }

  @Override
  public Posn getEndPosition() {
    throw new UnsupportedOperationException("No end position.");
  }

  @Override
  public IColor getStartColor() {
    throw new UnsupportedOperationException("No start color.");
  }

  @Override
  public IColor getEndColor() {
    throw new UnsupportedOperationException("No end color.");
  }

  @Override
  public double getStartWidth() {
    throw new UnsupportedOperationException("No start width.");
  }

  @Override
  public double getEndWidth() {
    throw new UnsupportedOperationException("No end width.");
  }

  @Override
  public double getStartHeight() {
    throw new UnsupportedOperationException("No start height.");
  }

  @Override
  public double getEndHeight() {
    throw new UnsupportedOperationException("No end height.");
  }

  @Override
  public double getStartTheta() {
    throw new UnsupportedOperationException("No start theta.");
  }

  @Override
  public double getEndTheta() {
    throw new UnsupportedOperationException("No end theta.");
  }
}
