package cs3500.animator.object.shape;

import cs3500.animator.object.IColor;
import cs3500.animator.object.ICanvasObject;
import cs3500.animator.object.Posn;

/**
 * This interface contains all of the methods that a shape should have when being constructed
 * for an animation.
 */

public interface IShape extends ICanvasObject {

  /**
   * Gets the x dimension of this shape.
   *
   * @return x dimension of this shape
   */
  double getSizeX();

  /**
   * Gets the y dimension of this shape.
   *
   * @return the y dimension of this shape
   */
  double getSizeY();

  /**
   * A getter for the name.
   *
   * @return the name String
   */
  String getName();

  /**
   * A getter for the location.
   *
   * @return the location Posn
   */
  Posn getLocation();

  /**
   * A getter for the color.
   *
   * @return the color object
   */
  IColor getColor();

  /**
   * Gets the shapes theta.
   *
   * @return the shapes theta
   */
  double getTheta();

  /**
   * Produces a string describing the type of the object, usually the object's class name.
   *
   * @return the type description string
   */
  String getType();

  /**
   * Produces a string describing the attributes associated with the shape.
   *
   * @return the attribute description string
   */
  String getAttributes();

  /**
   * Produces a string describing the attributes associated with this shape's size if the size is
   * multiplied by a scale factor in the x and y directions.
   *
   * @param scaleX the amount to scale the shape in the x direction
   * @param scaleY the amount to scale the shape in the y direction
   * @return the size attribute description string with the scaling factor taken into place
   */
  String getSizeDescriptionWithScale(double scaleX, double scaleY);

  /**
   * Produces an identical copy of this shape in a different memory location.
   *
   * @return the cloned shape
   */
  IShape clone();

  /**
   * A convenience method describing the attributes associated with this shape's size and without
   * a scale factor.
   *
   * @return the size attribute description string
   */
  String getSizeDescription();

  /**
   * Modifies this shape to be located at the new given location.
   *
   * @param newLocation the location to move this shape to
   */
  void move(Posn newLocation);

  /**
   * Modifies this shape to scale itself by the given x and y scaling factors.
   *
   * @param scaleX the factor to scale in the x direction
   * @param scaleY the factor to scale in the y direction
   */
  abstract void scale(double scaleX, double scaleY);

  /**
   * Modifies this shape's width and height to the new width and height.
   *
   * @param width  width of the shape
   * @param height height of the shape
   */
  abstract void updateSize(double width, double height);

  /**
   * Modifies this shape to be colored the new given color.
   *
   * @param newColor the color to change this shape to
   */
  void changeColor(IColor newColor);

  /**
   * Modifies this shape's rotation to the new rotation.
   *
   * @param theta the theta to set to
   */
  void setRotation(double theta);

  /**
   * Sets the properties of this shape to its original properties at construction.
   */
  void reset();

  /**
   * A getter for the original location.
   *
   * @return the original location Posn
   */
  Posn getOriginalLocation();

  /**
   * A getter for the original color.
   *
   * @return the original color object
   */
  IColor getOriginalColor();

  /**
   * Gets the original x dimension of this shape.
   *
   * @return original x dimension of this shape
   */
  double getOriginalX();

  /**
   * Gets the original y dimension of this shape.
   *
   * @return original y dimension of this shape
   */
  double getOriginalY();

  /**
   * Gets the original theta of this shape.
   *
   * @return original theta of this shape
   */
  double getOriginalTheta();

  /**
   * A convenience method describing the attributes associated with this shape's rotation without
   * a theta.
   *
   * @return a string describing the shapes rotation
   */
  String getRotationDescription();

  /**
   * Gets the rotation description.
   *
   * @param theta the amount rotated
   * @return description of the rotation
   */
  String getRotationDescriptionWithTheta(double theta);
}
