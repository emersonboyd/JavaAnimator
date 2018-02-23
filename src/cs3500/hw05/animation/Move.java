package cs3500.hw05.animation;

import cs3500.hw05.Posn;
import cs3500.hw05.shape.AbstractShape;

/**
 * Represents a translation animation to move an object to a destination location.
 */
public class Move extends AbstractAnimation {
  public static final String ERROR_DESTINATION_NULL =
          "The destination cannot be null.";

  private Posn destination;

  /**
   * Constructs an scale animation with a given scale factor.
   *
   * @param startTime   the time to show the object
   * @param endTime     the time to hide the object
   * @param shape       the shape to apply the animation to
   * @param destination the factor to scale the shape to
   * @throws IllegalArgumentException if the destination is null
   */
  public Move(int startTime, int endTime, AbstractShape shape, Posn destination) {
    super(startTime, endTime, shape);

    if (destination == null) {
      throw new IllegalArgumentException(ERROR_DESTINATION_NULL);
    }

    this.destination = destination;
  }

  /**
   * A getter for the destination.
   *
   * @return the destination posn
   */
  public Posn getDestination() {
    return this.destination;
  }

  @Override
  public boolean sameType(AbstractAnimation other) {
    return other instanceof Move;
  }
}
