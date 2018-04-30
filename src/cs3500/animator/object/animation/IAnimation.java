package cs3500.animator.object.animation;

import cs3500.animator.object.ICanvasObject;
import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;
import cs3500.animator.object.shape.IShape;

/**
 * This interface contains all of the methods that an animation should have when being constructed.
 */
public interface IAnimation extends ICanvasObject {

  /**
   * Checks whether this animation makes for an incompatible move with the other animation, meaning
   * that both animations perform the same type of alteration on the same shape at overlapping
   * times.
   *
   * @param other the animation to check this one against
   * @throws IllegalArgumentException if the given animation is null
   */
  public boolean conflictsWithAnimation(IAnimation other) throws IllegalArgumentException;

  /**
   * Checks whether the times of the two animations overlap one another at all, exclusively.
   *
   * @param other the animation to check this one against
   * @return true if the execution periods overlap at all, false otherwise
   */
  public boolean timeOverlaps(IAnimation other);

  /**
   * Performs the full animation on the given shape by altering the shape the necessary amount.
   *
   * @param s the shape to animate
   */
  public abstract void animate(IShape s);

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
  public abstract boolean sameType(IAnimation other);

  /**
   * A getter for the shape.
   *
   * @return this object's shape
   */
  public IShape getShape();

  /**
   * Produces a string describing the type of the object, usually the object's class name.
   *
   * @return the type description string
   */
  String getType();

  /**
   * Produces an action descriptor of what this animation does.
   *
   * @param s the shape that the action is being performed on
   * @return the action string
   */
  String getAction(IShape s);

  /**
   * Produces a string describing this animation's actions.
   *
   * @param s the shape that the action is being performed on
   * @return the animation string description
   */
  String toString(IShape s);

  /**
   * Calculates the starting coefficient for any temporal animation.
   *
   * @param ticksElapsed total ticks elapsed
   * @return the starting coefficient
   */
  double getStartCoef(int ticksElapsed);

  /**
   * Calculates the ending coefficient for any temporal animation.
   *
   * @param ticksElapsed total ticks elapsed
   * @return the ending coefficient
   */
  double getEndCoef(int ticksElapsed);

  /**
   * Produces the position of the shape at the start of the animation.
   * 
   * @return the shape's position at the start of the animation
   */
  Posn getStartPosition();

  /**
   * Produces the position of the shape at the end of the animation.
   *
   * @return the shape's position at the end of the animation
   */
  Posn getEndPosition();

  /**
   * Produces the color of the shape at the start of the animation.
   *
   * @return the shape's color at the start of the animation
   */
  IColor getStartColor();

  /**
   * Produces the color of the shape at the end of the animation.
   *
   * @return the shape's color at the end of the animation
   */
  IColor getEndColor();
  
  /**
   * Produces the width of the shape at the start of the animation.
   *
   * @return the shape's width at the start of the animation
   */
  double getStartWidth();

  /**
   * Produces the width of the shape at the end of the animation.
   *
   * @return the shape's width at the end of the animation
   */
  double getEndWidth();

  /**
   * Produces the height of the shape at the start of the animation.
   *
   * @return the shape's height at the start of the animation
   */
  double getStartHeight();

  /**
   * Produces the height of the shape at the end of the animation.
   *
   * @return the shape's height at the end of the animation
   */
  double getEndHeight();

  /**
   * Produces the theta of the shape at the start of the animation.
   *
   * @return the shape's theta at the start of the animation
   */
  double getStartTheta();

  /**
   * Produces the theta of the shape at the end of the animation.
   *
   * @return the shape's theta at the end of the animation
   */
  double getEndTheta();
}
