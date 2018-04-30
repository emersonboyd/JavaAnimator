package cs3500.animator.object.animation;

import cs3500.animator.object.Posn;
import cs3500.animator.object.shape.IShape;

/**
 * Represents a translation animation to move an object to a endPosition location.
 */
public class Move extends AbstractAnimation {
  public static final String ERROR_DESTINATION_NULL =
          "The endPosition cannot be null.";

  private Posn startPosition;
  private Posn endPosition;

  /**
   * Constructs an move animation with a given move factor.
   *
   * @param startTime   the time to show the object
   * @param endTime     the time to hide the object
   * @param shape       the shape to apply the animation to
   * @param startPosition the location of the object at the start of this animation
   * @param endPosition the endPosition to move the shape to
   * @throws IllegalArgumentException if the endPosition is null
   */
  public Move(int startTime, int endTime, IShape shape, Posn startPosition,
              Posn endPosition) throws IllegalArgumentException {
    super(startTime, endTime, shape);

    if (endPosition == null) {
      throw new IllegalArgumentException(ERROR_DESTINATION_NULL);
    }

    this.endPosition = endPosition;
    this.startPosition = startPosition;
  }

  @Override
  public void animate(IShape s) {
    s.move(this.endPosition);
  }

  @Override
  public void animate(int ticksElapsed) {
    double newX = this.startPosition.getX() * this.getStartCoef(ticksElapsed)
            + this.endPosition.getX() * this.getEndCoef(ticksElapsed);
    double newY = this.startPosition.getY() * this.getStartCoef(ticksElapsed)
            + this.endPosition.getY() * this.getEndCoef(ticksElapsed);
    shape.move(new Posn(newX, newY));
  }

  @Override
  public boolean sameType(IAnimation other) {
    return other instanceof Move;
  }

  @Override
  public String getType() {
    return "move";
  }

  @Override
  public String getAction(IShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("moves from ")
            .append(s.getLocation().toString())
            .append(" to ")
            .append(endPosition.toString());

    return builder.toString();
  }

  @Override
  public Posn getStartPosition() {
    return this.startPosition;
  }

  @Override
  public Posn getEndPosition() {
    return this.endPosition;
  }
}
