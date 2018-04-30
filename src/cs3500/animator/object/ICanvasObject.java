package cs3500.animator.object;

/**
 * This interface contains all of the methods that a canvas object should have. A canvas object
 * is either an animation or a shape.
 */
public interface ICanvasObject extends Comparable<ICanvasObject> {
  /**
   * A getter for the start time.
   *
   * @return the start time value
   */
  int getStartTime();

  /**
   * A getter for the end time.
   *
   * @return the end time value
   */
  int getEndTime();

  @Override
  int compareTo(ICanvasObject other);
}
